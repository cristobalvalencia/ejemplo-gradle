/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(stage){
  
  stage(){
    echo 'Compile..'
    sh "./mvnw clean compile -e"
    echo 'Testing..'
    sh "./mvnw clean test -e"
    echo 'Package..'
    sh "./mvnw clean package -e"
    echo 'SonarQube..'
    withSonarQubeEnv('Sonar'){
		sh 'mvn clean package sonar:sonar'
    }
    echo 'QualityGate..'
    timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
    }

  }

}

return this;