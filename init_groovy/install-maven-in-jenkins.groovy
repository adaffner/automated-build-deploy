// install maven 
// file: maven3.groovy

import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
a=Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0];
b=(a.installations as List);
b.add(new hudson.tasks.Maven.MavenInstallation("MAVEN3", "/home/jenkins/apache-maven/apache-maven-3.2.3", []));
a.installations=b
a.save()
