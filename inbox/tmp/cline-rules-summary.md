# Cline Rules Documentation Summary (English)

Cline Rules provide system-level guidance to Cline, acting as a persistent way to include context and preferences for projects or globally for all conversations.

## Creating a Rule

Rules can be created by clicking the `+` button in the Rules tab within the IDE, which opens a new file for writing the rule. Saving the file stores Workspace Rules in the `.clinerules/` directory of the project and Global Rules in the `Documents/Cline/Rules` directory. Cline can also create rules using the `/newrule` slash command.

**Example Cline Rule Structure:**

```markdown
# Project Guidelines

## Documentation Requirements
- Update relevant documentation in /docs when modifying features
- Keep README.md in sync with new capabilities
- Maintain changelog entries in CHANGELOG.md

## Architecture Decision Records
Create ADRs in /docs/adr for:
- Major dependency changes
- Architectural pattern changes
- New integration patterns
- Database schema changes
Follow template in /docs/adr/template.md

## Code Style & Patterns
- Generate API clients using OpenAPI Generator
- Use TypeScript axios template
- Place generated code in /src/generated
- Prefer composition over inheritance
- Use repository pattern for data access
- Follow error handling pattern in /src/utils/errors.ts

## Testing Standards
- Unit tests required for business logic
- Integration tests for API endpoints
- E2E tests for critical user flows
```

## Key Benefits

1.  **Version Controlled**: `.clinerules` files are part of the project’s source code.
2.  **Team Consistency**: Ensures consistent behavior across team members.
3.  **Project-Specific**: Rules are tailored to each project’s needs.
4.  **Institutional Knowledge**: Maintains project standards and practices in code.

The `.clinerules` file should be placed in the project’s root directory.

## Tips for Writing Effective Cline Rules

*   **Be Clear and Concise**: Use simple language and avoid ambiguity.
*   **Focus on Desired Outcomes**: Describe the results you want, not specific steps.
*   **Test and Iterate**: Experiment to find what works best for your workflow.

## .clinerules/ Folder System

Cline automatically processes all Markdown files within the `.clinerules/` directory, combining them into a unified set of rules. Numeric prefixes are optional but help organize files logically.

**Example Folder Structure:**

```
your-project/
├── .clinerules/              # Folder containing active rules
│   ├── 01-coding.md          # Core coding standards
│   ├── 02-documentation.md   # Documentation requirements
│   └── current-sprint.md     # Rules specific to current work
├── src/
└── ...
```

### Using a Rules Bank

For projects with multiple contexts or teams, a `clinerules-bank/` directory can be maintained as a repository of available but inactive rules.

**Example Rules Bank Structure:**

```
your-project/
├── .clinerules/              # Active rules - automatically applied
│   ├── 01-coding.md
│   └── client-a.md
│
├── clinerules-bank/          # Repository of available but inactive rules
│   ├── clients/              # Client-specific rule sets
│   │   ├── client-a.md
│   │   └── client-b.md
│   ├── frameworks/           # Framework-specific rules
│   │   ├── react.md
│   │   └── vue.md
│   └── project-types/        # Project type standards
│       ├── api-service.md
│       └── frontend-app.md
└── ...
```

### Benefits of the Folder Approach

1.  **Contextual Activation**: Copy only relevant rules from the bank to the active folder.
2.  **Easier Maintenance**: Update individual rule files without affecting others.
3.  **Team Flexibility**: Different team members can activate rules specific to their current task.
4.  **Reduced Noise**: Keep the active ruleset focused and relevant.

### Usage Examples

*   **Switch between client projects**: `rm .clinerules/client-a.md` and `cp clinerules-bank/clients/client-b.md .clinerules/`
*   **Adapt to different tech stacks**: `cp clinerules-bank/frameworks/react.md .clinerules/`

### Implementation Tips

*   Keep individual rule files focused on specific concerns.
*   Use descriptive filenames.
*   Consider git-ignoring the active `.clinerules/` folder while tracking the `clinerules-bank/`.
*   Create team scripts to quickly activate common rule combinations.

The folder system transforms Cline rules into a dynamic knowledge system.

## Managing Rules with the Toggleable Popover

Cline v3.13 introduces a dedicated popover UI accessible from the chat interface for managing rules. This popover allows users to:

*   **Instantly See Active Rules**: View active global and workspace rules.
*   **Quickly Toggle Rules**: Enable or disable specific rule files within the workspace `.clinerules/` folder.
*   **Easily Add/Manage Rules**: Create a workspace `.clinerules` file or folder, or add new rule files.

This UI simplifies switching contexts and managing different sets of instructions without manual file editing.

---

# Cline 规则文档摘要 (中文)

Cline 规则为 Cline 提供系统级指导，是为项目或所有对话全局提供上下文和偏好的持久方式。

## 创建规则

可以通过点击 IDE 中“规则”选项卡中的“+”按钮来创建规则，这将打开一个新文件用于编写规则。保存文件后，工作区规则将存储在项目的 `.clinerules/` 目录中，全局规则将存储在 `Documents/Cline/Rules` 目录中。Cline 也可以使用 `/newrule` 斜杠命令创建规则。

**Cline 规则示例结构：**

```markdown
# 项目指南

## 文档要求
- 修改功能时更新 /docs 中的相关文档
- 保持 README.md 与新功能同步
- 在 CHANGELOG.md 中维护更新日志条目

## 架构决策记录
在 /docs/adr 中为以下内容创建 ADR：
- 主要依赖项更改
- 架构模式更改
- 新的集成模式
- 数据库模式更改
遵循 /docs/adr/template.md 中的模板

## 代码风格与模式
- 使用 OpenAPI Generator 生成 API 客户端
- 使用 TypeScript axios 模板
- 将生成代码放在 /src/generated
- 优先使用组合而非继承
- 使用存储库模式进行数据访问
- 遵循 /src/utils/errors.ts 中的错误处理模式

## 测试标准
- 业务逻辑需要单元测试
- API 端点需要集成测试
- 关键用户流程需要 E2E 测试
```

## 主要优势

1.  **版本控制**：`.clinerules` 文件成为项目源代码的一部分。
2.  **团队一致性**：确保所有团队成员行为一致。
3.  **项目特定**：规则和标准根据每个项目的需求量身定制。
4.  **机构知识**：在代码中维护项目标准和实践。

`.clinerules` 文件应放置在项目的根目录中。

## 编写有效 Cline 规则的技巧

*   **清晰简洁**：使用简单语言，避免歧义。
*   **关注预期结果**：描述您想要的结果，而不是具体步骤。
*   **测试和迭代**：通过实验找到最适合您工作流程的方法。

## .clinerules/ 文件夹系统

Cline 自动处理 `.clinerules/` 目录中的所有 Markdown 文件，将它们组合成一套统一的规则。数字前缀（可选）有助于按逻辑顺序组织文件。

**文件夹结构示例：**

```
your-project/
├── .clinerules/              # 包含活动规则的文件夹
│   ├── 01-coding.md          # 核心编码标准
│   ├── 02-documentation.md   # 文档要求
│   └── current-sprint.md     # 特定于当前工作的规则
├── src/
└── ...
```

### 使用规则库

对于具有多个上下文或团队的项目，可以维护一个 `clinerules-bank/` 目录作为可用但非活动规则的存储库。

**规则库结构示例：**

```
your-project/
├── .clinerules/              # 活动规则 - 自动应用
│   ├── 01-coding.md
│   └── client-a.md
│
├── clinerules-bank/          # 可用但非活动规则的存储库
│   ├── clients/              # 客户端特定规则集
│   │   ├── client-a.md
│   │   └── client-b.md
│   ├── frameworks/           # 框架特定规则
│   │   ├── react.md
│   │   └── vue.md
│   └── project-types/        # 项目类型标准
│       ├── api-service.md
│       └── frontend-app.md
└── ...
```

### 文件夹方法的优势

1.  **上下文激活**：仅将相关规则从库复制到活动文件夹。
2.  **更易维护**：更新单个规则文件而不影响其他文件。
3.  **团队灵活性**：不同团队成员可以激活特定于其当前任务的规则。
4.  **减少干扰**：保持活动规则集集中且相关。

### 使用示例

*   **在客户端项目之间切换**：`rm .clinerules/client-a.md` 和 `cp clinerules-bank/clients/client-b.md .clinerules/`
*   **适应不同的技术栈**：`cp clinerules-bank/frameworks/react.md .clinerules/`

### 实施技巧

*   保持单个规则文件专注于特定问题。
*   使用描述性文件名。
*   考虑将活动 `.clinerules/` 文件夹添加到 `.gitignore`，同时跟踪 `clinerules-bank/`。
*   创建团队脚本以快速激活常见的规则组合。

文件夹系统将 Cline 规则转换为一个动态知识系统。

## 使用可切换弹出窗口管理规则

Cline v3.13 引入了一个专用的弹出窗口 UI，可从聊天界面访问，用于管理规则。此弹出窗口允许用户：

*   **即时查看活动规则**：查看活动的全局和工作区规则。
*   **快速切换规则**：通过单击启用或禁用工作区 `.clinerules/` 文件夹中的特定规则文件。
*   **轻松添加/管理规则**：如果不存在，则快速创建工作区 `.clinerules` 文件或文件夹，或向现有文件夹添加新规则文件。

此 UI 显著简化了上下文切换和管理不同指令集，而无需在对话期间手动编辑文件或配置。
