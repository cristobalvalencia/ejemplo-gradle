pipeline {
    agent any
	parameters{
		choice(name: 'Build_Tool', choices:['maven', 'gradle'], description: '')
	}
	stages {
        stage('Executing..') {
            steps{
                script{
                    if(params.Build_Tool == 'maven'){
                        def ejecucion = load 'maven.groovy'
	                    ejecucion.call()
                    }
                    if(params.Build_Tool == 'gradle'){
                        def ejecucion = load 'gradle.groovy'
	                    ejecucion.call()
                    }
                }
            }
        }
        stage('uploadNexus') {
            steps {
                echo 'Uploading Nexus'
				nexusPublisher nexusInstanceId: 'nsx01', nexusRepositoryId: 'EjercicioUnificar', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: '/var/jenkins_home/workspace/EjercicioUnificar_ejmaven_feature-nexus/build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
 
            }
        }
        stage ('Download Artifact'){
            steps
                {
                    sh 'curl -X GET -u admin:admin http://nexus:8081/repository/EjercicioUnificar/com.devopsusachs2020.DevOpsUsach2020.0.0.1.jar -O'
                }
        }
		stage ('Testing Artifact'){
            steps
                {
                    script{
                    if(params.Build_Tool == 'maven'){
                        sh 'nohup bash mvnw spring-boot:run &'
                    } else{
                        sh 'gradle bootRun &'
                    }
					timeout 5
					sh 'curl -X GET "http://localhost:8081/rest/mscovid/test?msg=testing"'
                    }
                }
        }

    }
}

