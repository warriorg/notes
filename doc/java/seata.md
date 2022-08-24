# Seata 分布式事务

## 概述

### 分布式事务简介

对于分布式事务，通俗地说就是，一次操作由若干分支操作组成，这些分支操作分属不同应用，分布在不同的服务器上。分布式事务需要保证这些分支操作要么全部成功，要么全部失败。分布式事务与普通事务一样，就是为了保证操作的一致性。

### Seata 简介

seata 是一款开源的分布式事务解决方案，致力于在微服务下提供高性能和简单易用的分布式事务服务

### Seata 术语

#### TC

Transaction Coordinator，事务协调者。维护全局和分支事务的状态，驱动全局事务提交或回滚。

#### TM

Transaction Manager，事务管理器。定义全局事务的范围：开始全局事务、提交或回滚全局事务。它实际是全局事务的发起者。

#### RM

Resource Manager，资源管理器。管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。

常见的DBMS在分布式系统中都是以RM的角色出现的。



## 分布式事务模式



## 安装与配置




