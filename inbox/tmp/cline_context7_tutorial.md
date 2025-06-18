# 🔥超越cursor！Cline+Context7 MCP文档搜索功能高级用法！自定义指令+.clinerules轻松开启vibe coding！零代码构建AutoGen智能体与Next.js应用

Context7是由Upstash团队开发的一个平台，旨在为大型语言模型(LLMs)、AI代码编辑器和开发者提供最新的、特定版本的文档。它能直接从官方文档中提取真实、可用的代码片段，按版本过滤，并准备好供Cursor、Claude或任何LLM使用。

### 🚀本篇笔记所对应的视频：

*   [👉👉👉 通过哔哩哔哩观看](https://www.bilibili.com/video/BV1AKLHzXEX1/)
*   [👉👉👉 通过YouTube观看](https://youtu.be/HdYT915bcfY)
*   [👉👉👉 我的开源项目](https://github.com/win4r/AISuperDomain)
*   [👉👉👉 请我喝咖啡](https://ko-fi.com/aila)
*   👉👉👉 我的微信：stoeng
*   👉👉👉 承接大模型微调、RAG、AI智能体、AI相关应用开发等项目。

### 🔥AI智能体相关视频

1.  [AI智能体视频 1](https://youtu.be/vYm0brFoMwA)
2.  [AI智能体视频 2](https://youtu.be/szTXELuaJos)
3.  [AI智能体视频 3](https://youtu.be/szTXELuaJos)
4.  [AI智能体视频 4](https://youtu.be/RxR3x_Uyq4c)
5.  [AI智能体视频 5](https://youtu.be/IrTEDPnEVvU)

### 使用场景

1.  **AI辅助编码**：
    Context7专为Cursor和Windsurf等AI优先的代码编辑器设计，这些工具基于VSCode，允许您将上下文直接引入聊天或内联补全中。
2.  **文档增强**：
    通过在文档中添加Context7链接，您可以增强文档并提供即时代码示例，这样用户在浏览您的文档时可以快速访问实现片段。
3.  **LLM准确性提升**：
    对于频繁更新的框架（如Next.js）和LLM未训练过的较冷门包，Context7通过索引整个项目的文档并使用专有排名算法按需过滤，提供干净的代码片段和解释。

### context 7用法

```markdown
Show me how to set up a basic mutation using React Query v5. use context7
```

### Cline自定义指令

**什么是Cline自定义指令？**

* 自定义指令可以被认为是 **Cline 的“编程”1**。它们定义了 Cline 的基本行为，并且**始终处于“开启”状态，影响着所有的交互。**

**自定义指令的范围：**

* 自定义指令是 **全局的**，适用于所有项目。

**如何添加自定义指令？**

* 在 VS Code 中，点击 Cline 扩展设置图标 ⚙️，找到 “Custom Instructions” 字段，然后粘贴你的指令即可。

**自定义指令的用途和威力：**

* **强制执行编码风格和最佳实践：** 确保 Cline 始终遵循你团队的编码约定、命名规范和最佳实践**2**。
* **提高代码质量：** 鼓励 Cline 编写更易读、更易维护和更高效的代码**2**。
* **指导错误处理：** 告诉 Cline 如何处理错误、编写错误消息和记录信息**2**。

**重要提示：** 修改自定义指令会更新 Cline 的提示缓存，丢弃累积的上下文。这会导致成本暂时增加，直到上下文被重新建立。建议在对话之间更新自定义指令。

### 示例

1.  When writing JavaScript, prefer functional programming paradigms over object-oriented approaches where appropriate.
2.  When generating documentation, always use Markdown format with clear headings and code blocks.
3.  Ensure that all new components follow a microservices architecture and communicate via REST APIs.
4.  When generating code that might throw exceptions, always include try-catch blocks and log the errors with descriptive messages.
5.  For database operations, use the Postgres MCP server with credentials stored in 1Password under ‘Development > Database’.
6.  Always provide the full code implementation without omitting any parts. Ensure the code is complete.

### **CLine的.clinerules文件**

.clinerules 文件提供了 **项目特定的指令**，它存在于项目的根目录下。

与全局的自定义指令不同，.clinerules 文件是 **项目特定的。**

这些指令会自动附加到你的自定义指令中，并在 Cline 的系统提示中使用，确保它们影响项目上下文中的所有交互。

**.clinerules 文件的关键优势：**

**版本控制：** .clinerules 文件是项目源代码的一部分，可以进行版本控制。

**团队一致性：** 确保所有团队成员在项目中使用 Cline 时行为一致。

**项目特定性：** 规则和标准可以根据每个项目的需求进行定制**4**。

**机构知识：** 将项目标准和实践保存在代码中**4**。

**.clinerules 文件的存放位置：** 应该放在项目的根目录下。

**重要提示：** 修改 .clinerules 文件也会更新 Cline 的提示缓存，导致成本暂时增加。建议在对话之间更新 .clinerules 文件。

### **CLine的.clinerules文件夹系统 📂**

**为什么需要文件夹系统？** 对于更复杂的项目，将规则组织到多个文件中可以带来更好的管理和模块化。

**如何使用文件夹系统？** 在项目根目录下创建一个名为 .clinerules/ 的文件夹，然后将不同的规则文件（例如 01-coding.md，02-documentation.md，current-sprint.md）放在该文件夹中。

**优势：** 这种模块化的方法使得管理和组织复杂的规则集更加方便**5** 。

**可切换的弹出窗口 (Toggleable Popover)**

**功能：** Cline v3.13 引入了一个专用的弹出窗口 UI，可以直接从聊天界面访问，用于更轻松地管理单个 .clinerules 文件和文件夹系统**6**。

**位置：** 位于聊天输入字段下方。

**主要功能：**

**查看活动规则：** 立即查看哪些全局规则和工作区规则（.clinerules 文件或文件夹内容）当前处于活动状态。

**快速切换规则：** 单击即可启用或禁用工作区 .clinerules/ 文件夹中的特定规则文件。这对于仅在需要时激活特定上下文的规则（例如 react-rules.md 或 memory-bank.md）非常有用**6**。

**轻松添加/管理规则：** 快速创建工作区 .clinerules 文件或文件夹（如果尚不存在），或向现有文件夹添加新的规则文件。

**文件指南**

**概述：** .clineignore 文件是一个项目级别的配置文件，告诉 Cline 在分析你的代码库时应该忽略哪些文件和目录。

**目的：** 类似于 .gitignore，它使用模式匹配来指定应该从 Cline 的上下文和操作中排除哪些文件。

### 项目需求

```markdown
开发一个基于Next.js和Tailwind CSS的智能背单词应用，
用户可以在智能背单词应用中输入多个单词，然后点击生成短文的按钮，
通过Ollama中的gemma3:12b-it-qat模型生成包含用户输入单词的短文，
以帮助用户更容易记忆单词。

# AI超元域频道原创视频
```

### `.clinerules`文件配置

```markdown
## 文档搜索

在需要查询 Next.js 和 Tailwind CSS 相关文档时，务必使用 Context7 以获取最新的、版本相关的文档信息。

### Next.js 文档搜索

当需要查询 Next.js 文档时，**搜索 Next.js 的稳定版本文档**。在你的提问中，明确包含 `use context7` 并指明需要 Next.js 的稳定版文档。例如：

use context7 搜索 Next.js 稳定版关于路由功能的文档

### Tailwind CSS 文档搜索

当需要查询 Tailwind CSS 文档时，**搜索 Tailwind CSS 的最新版本文档**。在你的提问中，明确包含 `use context7` 并指明需要 Tailwind CSS 的最新文档。例如：

use context7 搜索 Tailwind CSS 最新版关于响应式设计的文档
```

### `.clinerules Folder`详细内容

### 01-tech-stack.md

```markdown
# 技术栈偏好

在本项目中，我们优先使用以下技术栈进行开发：

- **前端框架**: **Next.js (稳定版)**。请优先使用最新稳定版本的 Next.js 来构建用户界面和路由。当生成或修改前端代码时，请优先考虑使用 **App Router (位于 `/src/app` 目录下)** 来构建用户界面和路由。
- **样式库**: **Tailwind CSS (最新版)**。所有的样式都应该使用最新版本的 Tailwind CSS 的工具类来实现。请务必参考 **`tailwind.config.js`** 文件（通常位于项目根目录或 `/src/` 目录下）以了解项目的 Tailwind CSS 配置。
- **编程语言**: **TypeScript**。所有新的和修改的代码都应该使用 TypeScript 编写，以确保类型安全和更好的代码维护性。
- **AI 模型运行**: **Ollama**。本项目将使用本地运行的 Ollama 来加载和运行 AI 模型。具体使用的模型是 **`gemma3:12b-it-qat`**。
- **包管理器**: 请根据项目已有的配置使用 **npm** 或 **yarn**。
```

### **02-project-structure.md**

```markdown
- **React 组件**:
    - 使用 **Next.js App Router** 时，页面组件应位于 `/src/app` 目录下的相应路由文件夹中。
    - 可重用的 React 组件应放置在 `/src/components` 目录下，并按照功能或模块进行组织（例如，`/src/components/ui`，`/src/components/features/vocabulary`）。
- **样式文件**:
    - Tailwind CSS 的配置文件通常位于项目根目录的 `tailwind.config.js`，或者在 `/src/styles/` 目录下。请始终参考此文件以了解项目的 Tailwind CSS 配置。
    - 任何全局的 CSS 文件（例如，用于 Tailwind CSS 指令 `@tailwind base`, `@tailwind components`, `@tailwind utilities`）通常位于 `/src/app/globals.css`。
- **工具函数**: 实用（utility）函数应放置在 `/src/lib` 或 `/src/utils` 目录下。
- **类型定义**: TypeScript 的类型（types）和接口（interfaces）应定义在 `/src/types` 或 `@types` 目录下。
- **静态资源**: 静态资源（例如图片、字体）应放置在 `/public` 目录下。
- **API 路由**: 使用 Next.js API 路由时，API 接口应位于 `/src/app/api` 目录下，用于处理与 Ollama 的交互或其他后端逻辑。
```

### **03-code-style.md**

```markdown
# 代码风格和模式

请遵循以下代码风格和常用模式：

- **组件命名**: React 组件应使用 **PascalCase** 命名 (例如，`VocabularyCard`, `WordInput`)。
- **函数命名**: 辅助函数应使用 **camelCase** 命名 (例如，`formatWord`, `generateStory`)。
- **变量命名**: 变量应使用 **camelCase** 命名 (例如，`userWords`, `generatedText`)。常量可以使用 **UPPER_SNAKE_CASE** (例如，`MAX_WORDS`)。
- **组件类型**: 除非有特殊理由，否则优先使用**函数式组件**和**箭头函数**语法。
- **状态管理**: 优先考虑使用 React 的 `useState` 和 `useEffect` 进行组件级别的状态管理。如果需要更复杂的状态管理，请根据具体情况考虑 `useContext` 或其他的轻量级状态管理库。
- **错误处理**: 在进行 API 调用和处理用户输入时，务必进行适当的错误处理。
```

### **04-styling.md**

```markdown
# 样式指南

- **Tailwind CSS 优先**: 在编写组件样式时，**始终优先使用 Tailwind CSS 的工具类**直接在 JSX 中进行样式化。
- **自定义 CSS**: 如果需要自定义 CSS，请尽量通过在 `tailwind.config.js` 中扩展 Tailwind 的配置，或者使用 Tailwind 的 **`@apply` 指令**来组合 Tailwind 的工具类。避免编写大量的完全自定义的 CSS。
- **Tailwind 配置**: 任何对 Tailwind CSS 的自定义（例如，添加新的颜色、字体、断点）都应该在 `tailwind.config.js` 文件中进行。
- **响应式设计**: 在设计页面和组件时，请始终考虑**响应式设计**，并利用 Tailwind CSS 的响应式前缀 (例如，`sm:`, `md:`, `lg:`) 来适配不同的屏幕尺寸。
```

### **05-ollama-config.md**

```markdown
# Ollama 配置

本项目使用本地运行的 Ollama 服务来生成短文。请注意以下配置和步骤：

- **Ollama 服务**: 确保 Ollama 服务已经在本地运行，并且可以通过默认端口 `http://localhost:11434/` 访问.
- **模型**: 使用的模型是 **`gemma3:12b-it-qat`**。在与 Ollama 交互之前，请确保该模型已经通过 Ollama 下载 . 可以使用命令 `ollama run gemma3:3b-it-qat` （或者 `gemma3:7b-it-qat` 或 `gemma3:12b-it-qat`，根据实际下载的版本）来下载和运行模型。
- **API 交互**: 当需要生成包含用户输入单词的短文时，请在 Next.js 的 API 路由中实现与 Ollama API 的交互。可以使用 `fetch` 或其他 HTTP 库向 Ollama API 发送 POST 请求，请求生成文本。
- **API 端点**: Ollama 的文本生成 API 端点通常是 `/api/generate`。
- **请求体**: 发送给 Ollama API 的请求体应包含 `model` 参数（设置为 `gemma 3:12b-it-qat`）以及 `prompt` 参数，该 prompt 应该包含用户输入的单词，并清晰地指示模型生成一个包含这些单词的短文以帮助记忆。
- **示例 Prompt**: 一个示例 prompt 可能是：“请使用以下单词创作一个简短的故事：[用户输入的单词列表，用逗号分隔]。”
- **错误处理**: 在与 Ollama API 交互时，请务必处理可能出现的错误，例如网络连接错误或模型生成错误。
```

### **06-app-logic.md**

```markdown
# 应用核心逻辑

本项目旨在创建一个智能背单词应用。以下是核心功能和 Cline 在开发过程中应该考虑的事项：

- **用户输入**: 应用需要提供用户输入多个单词的界面。
- **单词接收**: Next.js 前端需要能够接收用户输入的单词列表。
- **API 调用**: 前端需要调用后端的 API 路由，将用户输入的单词列表发送给后端。
- **Ollama 交互**: 后端 API 路由接收到单词列表后，需要构建合适的 prompt 并调用本地 Ollama 服务，请求生成包含这些单词的短文。
- **短文返回**: 后端 API 路由需要将 Ollama 生成的短文返回给前端。
- **短文展示**: 前端需要将生成的短文展示给用户。
- **用户记忆辅助**: 生成的短文应该自然流畅，能够将用户输入的单词融入其中，帮助用户在语境中记忆单词。
- **提示工程**: 在生成 prompt 时，可以尝试不同的提示策略，以获得更符合用户期望的短文。可以指示模型生成更具创意、情节性或与单词含义相关的短文。
- **迭代优化**: 根据生成短文的效果，不断优化 prompt 和应用逻辑。

请在开发过程中，优先考虑实现上述核心功能，并确保代码的可读性和可维护性。
```

### **07-important-files.md**

```markdown
# 重要文件位置（请勿修改）

为了项目的安全性和稳定性，请指示 Cline **不要读取或修改** 以下文件或目录：

- **环境变量文件**: `.env`，`.env.local`，`.env.development.local`，`.env.production.local` 等包含 API 密钥或其他敏感环境变量的文件。
- **Ollama 相关配置**: 任何 Ollama 的配置文件或模型存储目录（如果 Cline 需要直接操作这些文件）。通常情况下，Cline 只需要通过 API 与 Ollama 交互，不需要直接修改其配置。
- **构建输出目录**: 通常是 `/.next/`，`/build/`，`/dist/` 等。
- **包管理器的锁文件**: `package-lock.json` (npm) 或 `yarn.lock` (yarn)。

请确保 Cline 在执行任何操作时都遵守这些限制，以防止意外修改或暴露敏感信息。
```