
pipeline {
    agent {
        docker {
            image 'hub.xxxxx.cn/library/node:19-alpine' 
        }
    }

    options {
        disableConcurrentBuilds()
    }

    environment {
        GITCRED = 'gitlab'
        REPO = "https://git.xxxxx.cn/common-service/frt.git"
		SHARED_DIR = "/var/jenkins_home/workspace/frontend_shared_build"
        HARBOR_PATH = "hub.xxxxx.cn"
        SERVICE = "frontend" 
    }
    
    parameters {
        // 环境新增
        choice(choices: ['dev'], description: '操作环境', name: 'PROFILE')
		booleanParam(name: 'DEPLOY', defaultValue: false, description: '收否构建 Image')
        // git分支选择
        gitParameter name: 'BRANCH_TAG',
                type: 'PT_BRANCH_TAG',
                branchFilter: 'origin/(.*)',
                defaultValue: 'dev',
                selectedValue: 'DEFAULT',
                sortMode: 'DESCENDING_SMART',
                description: 'Select your branch or tag.'
    }

    stages {

        stage("Pre work") {
            steps {
                script {
                    env.VERSION = VersionNumber (versionNumberString: '${BUILD_DATE_FORMATTED, "yyyyMMdd"}-v${BUILDS_TODAY}')
                    env.IMAGE_PATH = "${HARBOR_PATH}/${PROFILE}/${SERVICE}"
                }
                echo "Pre work echo"
                echo "choose service: ${SERVICE}"
                echo "branch: ${BRANCH_TAG}"
                echo "imagePath : ${IMAGE_PATH}:${VERSION}"
				cleanWs deleteDirs: true, notFailBuild: true
                git branch: "${BRANCH_TAG}", credentialsId: "${GITCRED}", url: "${REPO}"
            }
        }

        stage('build') {
            steps {
                script {
                    sh 'npm install --registry=https://mvn.xxxxx.cn/repository/npm-group'
                    sh 'npm run build'
                }
            }
        }
        
        stage('copy resource') {
       		steps {
                script {
                    def sharedDir = "${SHARED_DIR}/${BRANCH_TAG}"
                    if (sharedDir?.trim()) {
                        sh "rm -rf ${sharedDir}/platform"
                        sh "mkdir -p ${sharedDir}/platform"
                        sh "mv ./packages/now-portal/dist/* ${sharedDir}/platform/"
                        sh "cp nginx.conf ${sharedDir}"
                        sh "cp Dockerfile ${sharedDir}"
                    } else {
                        error "❌ 共享目录无效: ${sharedDir}"
                    }
                }
            }
        }

		stage('deploy') {
            when {
                expression { params.DEPLOY }
            }
            steps {
                build job: 'dev-front-deploy', wait: false
            }
        }
    }
    
    post {
        success {
            echo '✅ 构建成功!'
        }
        failure {
            echo '❌ 构建失败!'
        }
    }
}
