pipeline {
	agent any
	options {
		// 表示保留10次构建历史
		buildDiscarder(logRotator(numToKeepStr: '10'))
		// 设置流水线运行的超过10分钟，Jenkins将中止流水线
		timeout(time: 30, unit: 'MINUTES')
		// 不允许同时执行流水线，被用来防止同时访问共享资源等
		disableConcurrentBuilds()		
	}

	environment {
		GITCRED = 'gitlab'
		REPO = "https://git.xxxxx.cn/customs-application/tes-service.git"
		HARBOR_PATH = "hub.xxxxx.cn"
		SERVICE = "tes"
		PROJECT = "tes-service"
		DEPLOY_SERVER_IP = "192.168.0.163"
		DEPLOY_SERVER = "ssh163"
	}
	parameters {
		// 环境新增
		choice(choices: ["pro"], description: '操作环境', name: 'PROFILE')
		booleanParam(name: 'UAT', defaultValue: true, description: '部署UAT')

		booleanParam(name: 'MERGE', defaultValue: false, description: '是否进行合并')
				// git分支选择
		gitParameter name: 'FROM_BRANCH_TAG',
				type: 'PT_BRANCH_TAG',
				branchFilter: 'origin/(.*)',
				defaultValue: 'test',
				selectedValue: 'DEFAULT',
				sortMode: 'DESCENDING_SMART',
				description: 'Select your merge source branch or tag.'
		// git分支选择
		gitParameter name: 'BRANCH_TAG',
				type: 'PT_BRANCH_TAG',
				branchFilter: 'origin/(.*)',
				defaultValue: 'pro',
				selectedValue: 'DEFAULT',
				sortMode: 'DESCENDING_SMART',
				description: '选择部署的分支'
	}
	stages {
		stage("Pre work") {
			steps {
				script {
					env.VERSION = VersionNumber(versionNumberString: '${BUILD_DATE_FORMATTED, "yyMMdd"}${BUILDS_TODAY, XX}')
					env.IMAGE_PATH = "${HARBOR_PATH}/${PROFILE}/${PROJECT}"
				}
				echo "Pre work echo"
				echo "choose service: ${PROJECT}"
				echo "branch: ${BRANCH_TAG}"
				echo "imagePath : ${IMAGE_PATH}:${VERSION}"
				cleanWs deleteDirs: true, notFailBuild: true
				
				checkout([$class: 'GitSCM',
                            branches: [[name: "origin/${BRANCH_TAG}"]],
                            doGenerateSubmoduleConfigurations: false,
                            extensions: [[$class: 'SubmoduleOption',
                                            disableSubmodules: false,
                                            parentCredentials: true,
                                            recursiveSubmodules: true,
                                            reference: '',
                                            trackingSubmodules: true]], 
                            submoduleCfg: [], 
                            userRemoteConfigs: [[url: "${REPO}", credentialsId:  "${GITCRED}"]]])
			}
		}

		stage('merge') {
      		when {
				expression { params.MERGE }
			}
            steps {
                checkout([$class: 'GitSCM', branches: [[name: 'origin/${FROM_BRANCH_TAG}']],
                    extensions: [
                        [$class: 'PreBuildMerge', options: [mergeRemote: 'origin',  mergeTarget: BRANCH_TAG]],
                        [$class: 'LocalBranch', localBranch: BRANCH_TAG],
                        [$class: 'ChangelogToBranch', options: [compareRemote: 'origin', compareTarget: BRANCH_TAG]]],
                    userRemoteConfigs: [[url: "${REPO}", credentialsId:  "${GITCRED}"]]])
            }
        }

		// 选择部署
		stage('Docker Build') {
			steps {
				script {
					def dockerImage = docker.build("${IMAGE_PATH}:${VERSION}")
					withDockerRegistry(credentialsId: 'docker', url: "https://${HARBOR_PATH}") {
						dockerImage.push(env.VERSION)
						dockerImage.push('latest')
					}
				}
			}
		}
		
		stage('deploy') {
			when {
				expression { params.UAT }
			}
			steps {
				script {
					def sshServer = [:] // 先定义一个空的 Map
					def serviceName = SERVICE.toUpperCase().replaceAll("-","_")
            
					withCredentials([usernamePassword(credentialsId: DEPLOY_SERVER, passwordVariable: 'password', usernameVariable: 'userName')]) {
						sshServer = [
							name: "server-${DEPLOY_SERVER_IP}",
							host: DEPLOY_SERVER_IP,
							port: 22,
							allowAnyHosts: true,
							user: "${userName}",
							password: "${password}"
						]
						
						sshCommand remote: sshServer, command: "sed -i \"s/^${serviceName}_VERSION=.*/${serviceName}_VERSION=${VERSION}/gi\" /data/app/.env"
						sshCommand remote: sshServer, command: """
							cd /data/app/
							docker-compose pull ${SERVICE}
							docker-compose up -d --force-recreate ${SERVICE}
						"""
					}
				}
			}
		}

		stage('GIt Push') {
			when {
				expression { params.MERGE }
			}
            steps {
                withCredentials([usernamePassword(credentialsId: GITCRED, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    sh """
                        git config --local credential.helper "!f() { echo username='${GIT_USERNAME}'; echo password='${GIT_PASSWORD}'; }; f"
                        git config --local user.name '${GIT_USERNAME}'
                        git config --local user.email '${GIT_USERNAME}@lonnows.cn'
                        git push -u origin ${BRANCH_TAG}
                    """
                }
            }
        }

		stage('Git Tag') {
            steps {
                withCredentials([usernamePassword(credentialsId: GITCRED, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    sh """
                        git config --local credential.helper "!f() { echo username='${GIT_USERNAME}'; echo password='${GIT_PASSWORD}'; }; f"
                        git config --local user.name '${GIT_USERNAME}'
                        git config --local user.email '${GIT_USERNAME}@lonnows.cn'
						git checkout ${BRANCH_TAG}
    					git tag ${VERSION}
                        git push -u origin ${BRANCH_TAG} --tags
                    """
                }
            }
        }
	}
}

