pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  stages {
    stage ('build hello world') {
      steps ('maven build') {
        def mvn_version = 'M3'
        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] )
      }
      steps ('build java app') {
          sh 'mvn -B -DskipTests clean package'
      }
    }
  }
}
