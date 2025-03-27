    pipeline {
        agent {
            label 'jenkins-agent'
        }
        options {
            // 禁止同时运行多个流水线
            disableConcurrentBuilds()
        }

        //常量参数，初始确定后一般不需更改
        environment {
            BUSINESS = "bbbb"    // 针对不同项目，命名空间不同
            PROJECT = "bbbb-xxxx"     // 项目名  修改项
            GITCRED = 'c91592d4-b863-47fc-adce-c146a49b5c10'
            SERICEGITPATH= "http://${GIT_URL}/bbbb/bbbb-xxxxx.git"  //git地址  修改项
            HARBORPATH = "${HARBOR_URL}"
            current_module = "." //模块名
            SERVICE = "bbbb-xxxxx" //应用名，对应K8S服务名称  修改项

        }
        parameters {
            // 环境新增
            choice(choices: ['dev'], description: '操作环境', name: 'Profile')
            // 动作
            choice(name: 'Status', choices: ['Deploy', 'Rollback'], description: 'Deploy 发布;Rollback 回退')
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
                        module = "${current_module}".toString()
                        namespace = "${BUSINESS}"
                        VERSION = VersionNumber (versionNumberString: '${BUILD_DATE_FORMATTED, "yyyyMMdd"}-${BRANCH_TAG}-v${BUILDS_TODAY}')
                        imagePath = "${HARBORPATH}/${BUSINESS}-${Profile}/${SERVICE}:${VERSION}"
                    }
                    echo "Pre work echo"
                    echo "choose service: ${SERVICE}"
                    echo "branch: ${BRANCH_TAG}"
                    echo "imagePath : ${imagePath}"
                    deleteDir()

                    dir("src") {
                        git branch: "${BRANCH_TAG}", credentialsId: "${GITCRED}", url: "${SERICEGITPATH}"
                    }
                    dir("cicddir") {
                        git branch: "main", credentialsId: "${GITCRED}", url: 'http://${GIT_URL}/wufan/jenkins-pipeline.git'
                        sh("chmod -R +x ./cicd/*")
                    }
                }
            }

            // 选择部署
            stage('Docker Build') {
                when {
                    environment name: 'Status', value: 'Deploy'
                }
                steps {
                    withCredentials([usernamePassword(credentialsId: 'DockerServer', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {                 
                        sh 'docker --version' 
                        sh "echo 'docker login -u ${USERNAME} -p ${PASSWORD} ${HARBORPATH}'"
                        sh "docker login -u ${USERNAME} -p ${PASSWORD} ${HARBORPATH}"
                    }
                    echo 'Credentials SUCCESS'
                    script {
                        echo "image : ${imagePath}"
                        dir("cicddir") {
                            sh("bash -x ./cicd/java_build_package.sh ${module} ${imagePath} ${PROJECT}")
                        }
                        echo "Docker 编译完成"
                    }

                }
            }
            stage('代码审查') {
                 steps {
                    echo '开始代码审查'
                    script {
                        // 引入SonarQube scanner，名称与jenkins 全局工具SonarQube Scanner的name保持一致
                        def scannerHome = tool 'SonarQube'
                        // 引入SonarQube Server，名称与jenkins 系统配置SonarQube servers的name保持一致
                        withSonarQubeEnv('sonarqube') {
                            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${Profile}-${PROJECT} -Dsonar.java.binaries=./src/target/classes"
                        }
                    }
                    echo '代码审查完成'
                }
            }
            stage("Deploy") {
                when {
                    expression { env.Status == "Deploy" || env.Status == "Rollback"}
                }
                steps {
                    dir("cicddir") {
                        script {

                            if (Status == 'Deploy') {
                                sh("./cicd/deploy.sh --env=${Profile} --action=deploy  --namespace=${namespace} --servicename=${SERVICE} --serviceimage=${imagePath}")
                                echo "deploy success"
                            } else if (Status == 'Rollback') {
                                sh("./cicd/deploy.sh --env=${Profile} --action=rollback --namespace=${namespace} --servicename=${SERVICE}")
                                echo "rollback success"
                            }
                        }
                    }
                }
            }
        }
    }