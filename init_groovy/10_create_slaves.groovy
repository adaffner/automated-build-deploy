import hudson.model.Node.Mode
import hudson.slaves.*
import jenkins.model.Jenkins
import java.util.logging.Logger

def logger = Logger.getLogger("")

//create build slave
DumbSlave buildslave = new DumbSlave("build",
	"Build Slave",
	"/home/jenkins",
	"1",
	Mode.NORMAL,
	"build",
	new JNLPLauncher(),
	RetentionStrategy.INSTANCE)
Jenkins.instance.addNode(buildslave)

//create run slave
DumbSlave runslave = new DumbSlave("run",
	"Run Slave",
	"/home/jenkins",
	"1",
	Mode.NORMAL,
	"run",
	new JNLPLauncher(),
	RetentionStrategy.INSTANCE)
Jenkins.instance.addNode(runslave)

Jenkins.instance.nodes.each {
    logger.info("$it slave configured")
}
