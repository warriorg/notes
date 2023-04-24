def sshServer = getServer("192.168.0.148")

pipeline {
    environment {
        zipFilename = "xy.zip"
        imageName = "warriorg/xy"
        dockerImage = ''
        branch_name = "pro"
        ENV_REPO = "https://github.com/warriorg/xy-java"
    }
    agent any

    parameters {
        string(name: 'TAG', defaultValue: getDefaultTag(), description: '当前的版本号')
        booleanParam(name: 'HOTFIX', defaultValue: false, description: 'hotfix')
    }

    stages {

           stage('checkout') {
                 when {
                    expression {
                       return params.HOTFIX
                    }
                }
                steps {
                    checkout scmGit(
                        branches: [[name: branch_name]],
                        userRemoteConfigs: [[url: ENV_REPO]])
                }
            }

        stage('merge') {
             when {
                expression {
                   return !params.HOTFIX
                }
            }
            steps {
                checkout([$class: 'GitSCM', branches: [[name: 'test']],
                    extensions: [
                        [$class: 'PreBuildMerge', options: [mergeRemote: 'origin',  mergeTarget: 'pro']],
                        [$class: 'LocalBranch', localBranch: 'pro'],
                        [$class: 'ChangelogToBranch', options: [compareRemote: 'origin', compareTarget: 'pro']]],
                    userRemoteConfigs: [[
                            credentialsId: 'gitlab',
                            refspec: '+refs/heads/test:refs/remotes/origin/test +refs/heads/test:refs/remotes/origin/pro',
                            url: ENV_REPO
                    ]]])
            }
        }

        stage('build') {
            steps {
                script {
                    dockerImage = docker.build imageName
                }
            }
        }
        stage('image') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'docker', url: 'https://hub.docker.com/') {
                        dockerImage.push('latest')
                        dockerImage.push("$params.tag")
                    }
                }
            }
        }

        stage('deploy') {
            steps {
                sshPut remote: sshServer, from: 'assets', into: '/data/deploy/backend/xy'
                sshCommand remote: sshServer, command: "cd /data/deploy/backend/ && zip -rm - xy/assets > " + zipFilename
            }
        }

        stage('git push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'gitlab', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    sh """
                        git config --local credential.helper "!f() { echo username='${GIT_USERNAME}'; echo password='${GIT_PASSWORD}'; }; f"
                        git config --local user.name '${GIT_USERNAME}'
                        git config --local user.email '${GIT_USERNAME}@lonnows.cn'
                        git fetch
                        git rebase pro
                        git tag $params.TAG
                        git push -f -u origin pro --tags
                    """
                }
            }
        }
    }

    post {
        success {
            sh "docker rmi $imagename:$params.tag"
            echo 'succeeded!'
        }
    }
}

def getServer(ip){
    def remote = [:]
    remote.name = "server-${ip}"
    remote.host = ip
    remote.port = 22
    remote.allowAnyHosts = true
    remote.sudo = true
    withCredentials([usernamePassword(credentialsId: 'ssh148', passwordVariable: 'password', usernameVariable: 'userName')]) {
        remote.user = "${userName}"
        remote.password = "${password}"
    }
    return remote
}

def getDefaultTag() {
    def now = new Date()
    return now.format("yyMMdd01", TimeZone.getTimeZone('UTC'))
}
