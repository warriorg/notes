## 基本概念



## 快速上手



## Yarn

Yarn 是一个包管理器， 你可以通过它使用全世界开发者的代码，或者分享自己的代码。代码通过**包（package）**（或者称为**模块（module）**）的方式来共享。 一个包里包含所有需要共享的代码，以及描述包信息的文件，称为`package.json`。

### 安装

```bash
npm install yarn -g

# macos
brew install yarn  # 安装
brew upgrade yarn  # 升级
```



### 使用

```bash
yarn init   # 初始化
# 添加包依赖
yarn add [package]
yarn add [package]@[version]
yarn add [package]@[tag]
# 将依赖项添加到不同依赖项类别
yarn add [package] --dev
yarn add [package] --peer
yarn add [package] --optional
# 升级依赖包
yarn upgrade [package]
yarn upgrade [package]@[version]
yarn upgrade [package]@[tag]
# 移除依赖包
yarn remove [package]
# 安装项目的全部依赖
yarn
yarn install
```



