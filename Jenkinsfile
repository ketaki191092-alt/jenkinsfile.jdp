pipeline {
            agent any
            stages {
        stage('PULL') {
            steps {
                git branch: 'devops', url: 'https://github.com/jambhulkarcloudblitz-alt/CDEC-studentapp.git'
            }
        }
        stage('BUILD') {
            steps {
                sh '''cd backend
                    mvn clean package -DskipTests'''
            }
        }

         stage('TEST-MANUAL') {
             steps {
                 sh '''cd backend
                   mvn sonar:sonar \
                     -Dsonar.projectKey=studentapp \
                     -Dsonar.projectName='studentapp' \
                     -Dsonar.host.url=http://54.242.28.230:9000 \
                       -Dsonar.token=sqp_1bc4acc6e7d8deca719d3b4f4cdf8bda6c4751d0'''
            }
         }

        stage ('TEST-JENKINS') {
            steps {
                withSonarQubeEnv(installationName: 'sonarqube' , credentialsId: 'sonar-cred') {
                   sh '''cd backend
                    mvn sonar:sonar -Dsonar.projectKey=studentapp'''
                }
            }
        }
        stage ('Quality-Gate') {
            steps {
                timeout(10) {
                    waitForQualityGate abortPipeline: true, credentialsId: 'sonar-cred'
                }
        }
        }
        stage('DEPLOY') {
            steps {
                echo 'DEPLOY SUCCSESS'
            }
        }
    }
}
