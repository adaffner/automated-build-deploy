import hudson.plugins.git.*
import jenkins.model.Jenkins
import java.util.logging.Logger

sleep(30000)

def logger = Logger.getLogger("")

def scm = new GitSCM("https://github.com/adaffner/automated-build-deploy.git")
scm.branches = [new BranchSpec("*/master")]

def flowDefinition = new org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition(scm, "Jenkinsfile.run")
def buildTrigger = new jenkins.triggers.ReverseBuildTrigger("automated-build-deploy")

def instance = Jenkins.instance
def job = new org.jenkinsci.plugins.workflow.job.WorkflowJob(instance, "helloworld_run")
job.definition = flowDefinition
job.addTrigger(buildTrigger)

logger.info("$job job added")
instance.reload()
