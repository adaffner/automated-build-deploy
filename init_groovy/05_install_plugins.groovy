import jenkins.model.*
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
a=Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0];
b=(a.installations as List);
b.add(new hudson.tasks.Maven.MavenInstallation("MAVEN3", "/home/jenkins/apache-maven/apache-maven-3.2.3", []));
a.installations=b
a.save()
import java.util.logging.Logger

// list of plugins to be installed
def plugins = [ "git", "workflow" ]
def logger = Logger.getLogger("")
def installed = false
def initialized = false

logger.info("Installing plugins: " + plugins)

def instance = Jenkins.getInstance()
def pm = instance.getPluginManager()
def uc = instance.getUpdateCenter()

plugins.each {
  logger.info("Checking " + it)
  if (!pm.getPlugin(it)) {
    logger.info("Looking UpdateCenter for " + it)
    if (!initialized) {
      uc.updateAllSites()
      initialized = true
    }
    def plugin = uc.getPlugin(it)
    if (plugin) {
      logger.info("Installing " + it)
        def installFuture = plugin.deploy()
      while(!installFuture.isDone()) {
        logger.info("Waiting for plugin install: " + it)
        sleep(5000)
      }
      installed = true
    }
  }
}

if (installed) {
  logger.info("Plugins installed, initializing a restart!")
  instance.save()
  instance.restart()
}
