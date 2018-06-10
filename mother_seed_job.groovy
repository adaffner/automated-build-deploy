import jenkins.automation.utils.ScmUtils
import jenkins.automation.utils.EnvironmentUtils

def registrationConfigFile = readFileFromWorkspace('config/registered-projects-config.groovy')
def environmentVarsConfigFile = readFileFromWorkspace('config/global-env-vars-config.groovy')
def defaultEmail = "foo@example.com"

def host = "${JAC_HOST}"
def env = EnvironmentUtils.getInstance("${JAC_ENVIRONMENT}").getEnv()
def registrationConfig = new ConfigSlurper(host.toLowerCase()).parse(registrationConfigFile)
def environmentVarsConfig = new ConfigSlurper(host.toLowerCase()).parse(environmentVarsConfigFile)

def globalVariables =environmentVarsConfig."${env}".globals

def path = "${JENKINS_HOME}/globals.properties"
File globalConfigs = new File(path)
if (globalVariables) {
    globalConfigs.write globalVariables
}

def sharedReposToAutomate = registrationConfig."all".reposToAutomate
def reposToAutomate = registrationConfig."${env}".reposToAutomate
reposToAutomate = reposToAutomate.plus(sharedReposToAutomate);

reposToAutomate.each { project ->
    def reposToInclude = [
            project,
            [name: "test", url: 'https://github.com/adaffner/test.git', sub_directory: 'test']
    ]


    if (project.viewName) {

        def viewRegex = project.viewRegex ? project.viewRegex : "${project.projectName}-.*"

        listView(project.viewName) {
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
            filterBuildQueue()
            filterExecutors()
            jobs {
                regex(/(?i)(${viewRegex})/)
            }
        }
    }

    job(project.projectName + '-seed-job1') {

        if (project.disabled) {
            disabled()
        }
        logRotator {
            daysToKeep(90)
        }

        multiscm {
            ScmUtils.project_repos(delegate, reposToInclude, false)
        }

        triggers {
            scm 'H/5 * * * *'
        }

        configure { node ->
            node / publishers << 'hudson.plugins.logparser.LogParserPublisher' {
                unstableOnWarning(true)
                failBuildOnError(false)
                useProjectRule(true)
                projectRulePath('automation/log-parser-rules.txt')
            }
        }

        steps {

            dsl {
                external "jobs/**/*.groovy"
                additionalClasspath "automation/src/main/groovy" + "\r\n" + "src/main/groovy"
                removeAction("DELETE")
                removeViewAction("DELETE")
            }

            shell('echo ${JENKINS_HOME}')
        }

        recipients = project.emails ?: defaultEmail
        publishers {
            extendedEmail {
                recipientList(recipients)
                triggers {
                    failure {
                        sendTo {
                            recipientList()
                        }
                    }
                    fixed {
                        sendTo {
                            recipientList()
                        }
                    }
                }
            }
        }
    }
}
