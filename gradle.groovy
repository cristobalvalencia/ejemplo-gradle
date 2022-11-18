def call(){
  
  stage(){
    sh 'gradle build'
    echo 'SonarQube..'
    withSonarQubeEnv('Sonar'){
		sh 'gradle sonarqube'
    }
    echo 'QualityGate..'
    timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
    }

  }

}

return this;