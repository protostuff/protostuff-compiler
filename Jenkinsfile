pipeline {
    agent any
    tools {
        maven 'M35'
        jdk 'JDK 8'
    }
    options {
        ansiColor('xterm')
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }
    stages {
        stage('Set Version') {
            steps {
                sh '''                    
                    if [[ "$BRANCH_NAME" =~ ^(master|release/).*$ ]]; then
                      VERSION=$(cat release.txt).${BUILD_ID}                                                              
                    else
                      VERSION=$(cat release.txt).dev.${BUILD_ID}
                    fi                    
                    mvn versions:set versions:commit -DnewVersion=${VERSION}                    
                '''
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -P release'
            }
            post {
                always {
                    junit '**/target/surefire-reports/**/*.xml'
                }
            }
        }
        stage('Publish') {
            // http://www.sonatype.org/nexus/2016/09/14/introducing-the-nexus-jenkins-plugin/
            when {
                expression {
                    return env.BRANCH_NAME =~ /^master|release\/.*$/
                }
            }
            steps {
                sh 'mvn deploy:deploy'
            }
        }
        stage('Tag') {
            when {
                expression {
                    return env.BRANCH_NAME =~ /^master$|^release\/.*$/
                }
            }
            steps {
                sh 'mvn scm:tag'
            }
        }
    }
    post {
        failure {
            emailext(
                    mimeType: 'text/html',
                    body: '${JELLY_SCRIPT,template="html"}',
                    recipientProviders: [
                            [$class: 'CulpritsRecipientProvider'],
                            [$class: 'DevelopersRecipientProvider'],
                            [$class: 'RequesterRecipientProvider']
                    ],
                    subject: 'Build failed - ${JOB_NAME}'
            )
        }
        unstable {
            emailext(
                    mimeType: 'text/html',
                    body: '${JELLY_SCRIPT,template="html"}',
                    recipientProviders: [
                            [$class: 'CulpritsRecipientProvider'],
                            [$class: 'DevelopersRecipientProvider'],
                            [$class: 'RequesterRecipientProvider']
                    ],
                    subject: 'Build unstable - ${JOB_NAME}'
            )
        }
    }
}