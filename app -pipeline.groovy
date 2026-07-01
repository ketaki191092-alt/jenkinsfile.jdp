pipeline {
    agent any 
    stages {
        stage ('PULL') {
            steps{
                git branch: 'test', url: 'https://github.com/PratikshaHalloli/CDEC-studentapp.git'
            }
        }
        stage ('BUILD-FRONTEND-DOCKERFILE'){
            steps{
                sh '''cd frontend 
                  docker build . -t pratiksha0111/easy-frontend:latest'''
            }
        }

        stage ('BUILD-BACKEND-DOCKERFILE'){
            steps{
                sh '''cd backend
                   docker build . -t pratiksha0111/easy-backend:latest'''
            }
        }

                stage ('DOCKERHUB-LOGIN') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        stage ('PUSH-IMAGES') {
            steps {
                sh '''
                    docker push pratiksha0111/easy-frontend:latest
                    docker push pratiksha0111/easy-backend:latest
                '''
            }
        }

         stage('DOCKER REMOVE'){
            steps{
                sh '''docker rmi  pratiksha0111/easy-frontend:latest
                    docker rmi  pratiksha0111/easy-backend:latest'''
            }
        }


        stage ('DEPLOY-TO-EKS') {
             steps {
                sh '''
                   aws eks update-kubeconfig --name my-eks --region ap-south-1
                    kubectl apply -f simple-deploy/ 
                      '''
                 }
            }



    }
}
