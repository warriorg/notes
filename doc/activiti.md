# 工作流

> 以任务（Task）的形式驱动人处理业务或者驱动业务系统自动完成作业。

# 介绍

* BPM(Business Process Management)  业务流程管理 

* BPMN(Business Process Modeling Notation)  业务流程建模标注，是由BPMN标准组织发布的，其第一版BPMN 1.0规范于2004年5月发布。经过多年的改进新的规范BPMN 2.0于2011年发布。

## 生命周期
1. 定义
2. 发布
3. 执行
4. 监控
5. 优化


## Activiti引擎的七大Service接口

* RepositoryService 流程仓库Service，用于管理流程仓库，例如，部署、删除、读取流程资源
* IdentifyService 身份Service，可以管理和查询用户、组之间的关系
* RuntimeService 运行时Service，可以处理所有正在运行状态的流程实例、任务等
* TaskService 任务Service，用于管理、查询任务，例如，签收、办理、指派等
* FormService 表单Service，用于读取和流程、任务相关的表单数据
* HistoryService 历史Service，可以查询所有历史数据，例如，流程实例、任务、活动、变量、附件等
* ManagementService 引擎管理Service，和具体业务无关，主要是可以查询引擎配置、数据库、作业等

# Step by Step

## 5.0 版本
1. 下载 activiti
2. copy activiti-explorer.war 到tomcat下面
3. 启动tomcat 
4. 访问 http://localhost:8080/activiti-explorer ，用户名/密码 kermit/kermit




