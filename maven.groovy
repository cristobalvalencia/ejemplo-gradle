/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def exec(){
  
    echo 'Building..'
    sh "mvn clean install"
    echo 'SonarQube..'
    withSonarQubeEnv('Sonar'){
		  sh 'mvn clean package sonar:sonar'
    }
}

return this;