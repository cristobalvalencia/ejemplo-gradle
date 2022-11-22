def mvn
def grdl
def stg

pipeline {
    agent any
    tools{
		gradle 'gradle'
		maven 'maven'
	}
	parameters{
		choice(name: 'Build_Tool', choices:['maven', 'gradle'], description: '')
	}
	stages {
        stage('Building..') {
            steps{
                script{
                    stg == 'Building'
                    if(params.Build_Tool == 'maven'){
                        mvn = load 'maven.groovy'
                        mvn.exec()
                    }
                    if(params.Build_Tool == 'gradle'){
                        grdl = load 'gradle.groovy'
	                    grdl.exec()
                    }
                }
                echo 'QualityGate..'
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
            
        }
        stage('uploadNexus') { 
            steps {
                script{
                    stg = 'uploadNexus'
                }
                echo 'Uploading Nexus'
				nexusPublisher nexusInstanceId: 'nsx01', nexusRepositoryId: 'EjercicioUnificar', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: '/var/jenkins_home/workspace/ejemplo-gradle_maven-gradle/build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
            
            }
            
        }
        stage ('Download Artifact'){
            steps
                {
                    script{
                    stg == 'Download Artifact'
                    }
                    sh 'curl -X GET -u admin:admin http://nexus:8081/repository/EjercicioUnificar/com.devopsusachs2020.DevOpsUsach2020.0.0.1.jar -O'
                }
                
        }
           
    }
    post{
            failure{
                slackSend channel: 'C044C4RDF26', message: "${custom_msg()}", teamDomain: 'diplomadodevo-izc9001', tokenCredentialId: 'slack'
            }
            success{
                slackSend channel: 'C044C4RDF26', message: '[Cristobal Valencia] [Slack_notification] [$env.] Ejecución correcta', teamDomain: 'diplomadodevo-izc9001', tokenCredentialId: 'slack'
            }
        }  

}

def custom_msg()
{
  def AUTHOR = env.CHANGE_AUTHOR
  def JOB_NAME = env.JOB_NAME
  def BUILD_ID= env.BUILD_ID
  def MSG= "[${AUTHOR}] STAGE: ${stg} FAILED: Job [${JOB_NAME}] Logs path: localhost:8080/job/${JOB_NAME}/${BUILD_ID}/consoleText"
  return MSG
}

