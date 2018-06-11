import hudson.plugins.git.*
import jenkins.model.Jenkins
import java.util.logging.Logger

sleep(10000)

def logger = Logger.getLogger("")

def scm = new GitSCM("https://github.com/adaffner/automated-build-deploy.git")
scm.branches = [new BranchSpec("*/master")]

def flowDefinition = new org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition(scm, "Jenkinsfile.build")

def instance = Jenkins.instance
def job = new org.jenkinsci.plugins.workflow.job.WorkflowJob(instance, "automated-build-deploy")
job.definition = flowDefinition
job.addTrigger(gogsTrigger)

logger.info("$job job added")
instance.reload()
