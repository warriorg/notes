# MCP 介绍思维导图

## 什么是 MCP？
-   一个开放协议，用于标准化应用程序向 LLM 提供上下文的方式。
-   类比：AI 应用的 USB-C 端口。
    -   USB-C：标准化设备连接外设的方式。
    -   MCP：标准化 AI 模型连接不同数据源和工具的方式。

## 为什么需要 MCP？
-   帮助在 LLM 之上构建代理和复杂工作流。
-   LLM 需要集成数据和工具，MCP 提供：
    -   不断增长的预构建集成列表，LLM 可直接接入。
    -   在 LLM 提供商和供应商之间切换的灵活性。
    -   在基础设施内保护数据的最佳实践。

### 通用架构
-   核心：客户端-服务器架构，主机应用程序可连接多个服务器。
    -   **MCP 主机 (Hosts)**：希望通过 MCP 访问数据的程序（如 Claude Desktop, IDEs, AI 工具）。
    -   **MCP 客户端 (Clients)**：与服务器保持 1:1 连接的协议客户端。
    -   **MCP 服务器 (Servers)**：通过标准化 MCP 协议暴露特定功能的轻量级程序。
    -   **本地数据源 (Local Data Sources)**：MCP 服务器可安全访问的计算机文件、数据库和服务。
    -   **远程服务 (Remote Services)**：MCP 服务器可连接的互联网外部系统（例如，通过 API）。

## 开始使用
-   选择最适合您需求的路径：
    -   快速入门 (Quick Starts)
    -   示例 (Examples)

## 教程 (Tutorials)
-   [使用 LLM 构建 MCP](/tutorials/building-mcp-with-llms)：学习如何使用像 Claude 这样的 LLM 加速 MCP 开发。
-   [调试指南](/docs/tools/debugging)：学习如何有效调试 MCP 服务器和集成。
-   [MCP 检查器](/docs/tools/inspector)：使用交互式调试工具测试和检查 MCP 服务器。
-   [MCP 工作坊 (视频, 2小时)](https://www.youtube.com/watch?v=kQmXtrmQ5Zg)

## 探索 MCP
-   深入了解 MCP 的核心概念和功能。

## 贡献 (Contributing)
-   想做出贡献？查看 [贡献指南](/development/contributing) 了解如何帮助改进 MCP。

## 支持与反馈 (Support and Feedback)
-   获取帮助或提供反馈的方式：
    -   MCP 规范、SDK 或文档相关的 Bug 报告和功能请求（开源）：[创建 GitHub issue](https://github.com/modelcontextprotocol)
    -   关于 MCP 规范的讨论或问答：[规范讨论区](https://github.com/modelcontextprotocol/specification/discussions)
    -   关于其他 MCP 开源组件的讨论或问答：[组织讨论区](https://github.com/orgs/modelcontextprotocol/discussions)
    -   Claude.app 和 claude.ai 的 MCP 集成相关的 Bug 报告、功能请求和问题：请参阅 Anthropic 的 [如何获取支持](https://support.anthropic.com/en/articles/9015913-how-to-get-support) 指南。
