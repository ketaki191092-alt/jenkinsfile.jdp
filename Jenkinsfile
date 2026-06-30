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
                 mvn  sonar:sonar \
                 -Dsonar.projectKey=sonarqube01 \
                 -Dsonar.projectName='sonarqube01' \
                 -Dsonar.host.url=http://54.242.28.230:9000 \
                 -Dsonar.token=sqp_3f25694c956d24d9366f3d8ed0835dfb07e621d7'''
             }
         }           
        stage('S3 UPLOAD') {
            steps {
                sh 'aws s3 cp backend/target/student-registration-backend-0.0.1-SNAPSHOT.jar s3://ketakicv.shop/key'
            }
        }
    }
}
