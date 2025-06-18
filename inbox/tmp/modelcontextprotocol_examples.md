# Model Context Protocol 示例

本页面展示了各种 Model Context Protocol (MCP) 服务器，这些服务器展示了该协议的功能和多功能性。这些服务器使大型语言模型 (LLM) 能够安全地访问工具和数据源。

## 参考实现

这些官方参考服务器展示了核心 MCP 功能和 SDK 用法：

### 数据和文件系统

- **文件系统** - 具有可配置访问控制的安全文件操作
- **PostgreSQL** - 具有模式检查功能的只读数据库访问
- **SQLite** - 数据库交互和商业智能功能
- **Google Drive** - Google Drive 的文件访问和搜索功能

### 开发工具

- **Git** - 用于读取、搜索和操作 Git 仓库的工具
- **GitHub** - 仓库管理、文件操作和 GitHub API 集成
- **GitLab** - 启用项目管理的 GitLab API 集成
- **Sentry** - 从 Sentry.io 检索和分析问题

### Web 和浏览器自动化

- **Brave Search** - 使用 Brave 的 Search API 进行 Web 和本地搜索
- **Fetch** - 针对 LLM 用法优化的 Web 内容抓取和转换
- **Puppeteer** - 浏览器自动化和 Web 抓取功能

### 生产力和沟通

- **Slack** - 频道管理和消息传递功能
- **Google Maps** - 位置服务、路线和地点详情
- **Memory** - 基于知识图谱的持久内存系统

### AI 和专业工具

- **EverArt** - 使用各种模型生成 AI 图像
- **Sequential Thinking** - 通过思维序列进行动态问题解决
- **AWS KB Retrieval** - 使用 Bedrock Agent Runtime 从 AWS 知识库检索

## 官方集成

这些 MCP 服务器由公司为其平台维护：

- **Axiom** - 使用自然语言查询和分析日志、跟踪和事件数据
- **Browserbase** - 在云中自动化浏览器交互
- **BrowserStack** - 访问 BrowserStack 的测试平台以调试、编写和修复测试，进行可访问性测试等。
- **Cloudflare** - 在 Cloudflare 开发者平台上部署和管理资源
- **E2B** - 在安全云沙箱中执行代码
- **Neon** - 与 Neon 无服务器 Postgres 平台交互
- **Obsidian Markdown Notes** - 在 Obsidian 保险库中读取和搜索 Markdown 笔记
- **Prisma** - 管理和与 Prisma Postgres 数据库交互
- **Qdrant** - 使用 Qdrant 向量搜索引擎实现语义记忆
- **Raygun** - 访问崩溃报告和监控数据
- **Search1API** - 用于搜索、抓取和站点地图的统一 API
- **Snyk** - 通过将 Snyk 漏洞扫描直接嵌入到代理工作流程中来增强安全态势。
- **Stripe** - 与 Stripe API 交互
- **Tinybird** - 与 Tinybird 无服务器 ClickHouse 平台交互
- **Weaviate** - 通过您的 Weaviate 集合启用 Agentic RAG

## 社区亮点

不断发展的社区开发服务器生态系统扩展了 MCP 的功能：

- **Docker** - 管理容器、镜像、卷和网络
- **Kubernetes** - 管理 Pod、部署和服务
- **Linear** - 项目管理和问题跟踪
- **Snowflake** - 与 Snowflake 数据库交互
- **Spotify** - 控制 Spotify 播放和管理播放列表
- **Todoist** - 任务管理集成

> **注意：**社区服务器未经测试，使用风险自负。它们不隶属于 Anthropic，也不受其认可。

有关社区服务器的完整列表，请访问 [MCP 服务器仓库](https://github.com/modelcontextprotocol/servers)。

## 入门

### 使用参考服务器

基于 TypeScript 的服务器可以直接使用 `npx`：

```bash
npx -y @modelcontextprotocol/server-memory
```

基于 Python 的服务器可以使用 `uvx`（推荐）或 `pip`：

```bash
# 使用 uvx
uvx mcp-server-git

# 使用 pip
pip install mcp-server-git
python -m mcp_server_git
```

### 使用 Claude 配置

要将 MCP 服务器与 Claude 一起使用，请将其添加到您的配置中：

```json
{
  "mcpServers": {
    "memory": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-memory"]
    },
    "filesystem": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-filesystem", "/path/to/allowed/files"]
    },
    "github": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "<YOUR_TOKEN>"
      }
    }
  }
}
```

## 附加资源

- [MCP 服务器仓库](https://github.com/modelcontextprotocol/servers) - 参考实现和社区服务器的完整集合
- [Awesome MCP Servers](https://github.com/punkpeye/awesome-mcp-servers) - 精选的 MCP 服务器列表
- [MCP CLI](https://github.com/wong2/mcp-cli) - 用于测试 MCP 服务器的命令行检查器
- [MCP Get](https://mcp-get.com/) - 用于安装和管理 MCP 服务器的工具
- [Pipedream MCP](https://mcp.pipedream.com/) - 具有内置身份验证的 MCP 服务器，支持 3,000 多个 API 和 10,000 多个工具
- [Supergateway](https://github.com/supercorp-ai/supergateway) - 通过 SSE 运行 MCP stdio 服务器
- [Zapier MCP](https://zapier.com/mcp) - 具有 7,000 多个应用程序和 30,000 多个操作的 MCP 服务器

访问我们的 [GitHub Discussions](https://github.com/orgs/modelcontextprotocol/discussions) 与 MCP 社区互动。
