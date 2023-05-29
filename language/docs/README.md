# LLM with Arch

三阶六工程

## 定义工序

目标形态：

```yml
- Question: 介绍一下这个应用？
  Answer: 
  Template: 这是一个基于 xx 架构风格的 Web 渠道系统，旨在应用于 xxx 场景。它提供了什么功能？使用了哪些技术栈？请参考 README 文件和渠道信息获取更多详细信息。
  Data: README 文件，Build Tool 配置，渠道信息，应用类型，技术栈，核心业务场景
  PromptEng: 根据提供的材料，组织好问题，确保问题清晰且具体。

- Question: 架构质量如何？
  Answer:
  Template: 该架构质量良好，符合 xxx 指标。xx 需要注意的是 xxx，建议使用 xxx 方式进行优化。
  Data: 架构指标，合理度规范
  PromptEng: 描述架构指标和合理度规范，确保问题具有明确的指向性。

- Question: 检查一下如下的代码，看是否符合规范？
  Answer:
  Template: 其中，第一行代码不符合规范，应该使用 xxx 方式。第二行代码符合规范。
  Data: 已有代码、代码规范、架构规范、API 规范
  PromptEng: 使用多个Prompt，引导模型生成与规范检查相关的问题和解决方案，确保问题和回答的有效性。

- Question: 请解释一下下面代码的业务含义？
  Answer:
  Template: 
  Data: 系统结构、Endpoint/Component 分析结果、Build Tool 配置
  PromptEng: 提供更多关于系统结构和技术的信息，以便模型能够更好地分析业务逻辑。

- Question: 下面的业务逻辑，适合用什么设计模式？
  Answer:
  Template: 根据业务逻辑，可以使用 xxx 设计模式。请参考 xxx 代码示例，了解如何使用该设计模式。
  Data: 设计模式数据集
  PromptEng: 使用Fine-Tuning技术，针对设计模式进行调整和优化，以生成与设计模式相关的代码示例。
```

## 形态

1. 文本应用。
2. markdown 表格，渲染器。

## Data sources?

- 代码库规模：1k~3k?

计划占比

| 应用类型   | 占比  |
|--------|-----|
| Web 前端 | 15% |
| 移动应用   | 15% |
| 后端微服务  | 50% |
| 桌面应用   | 5%  |
| 插件     | 5%  |
| 嵌入式应用  | 5%  |
| 杂项     | 5%  |

Todo: 调研不同类型的桌面应用

| 渠道类型 | 目标应用类型 | 技术栈        | 计划占比 |
|------|--------|------------|------|
| Web  | 桌面 Web | JavaScript | 5%   |
| Web  | 移动 Web | JavaScript | 5%   |
| 桌面应用 | 桌面应用   | .Net/C++?  | 3%   |
| 桌面应用 | 桌面应用   | .Net?      | 3%   |

语言建模

## Define Architecture

定义架构：

- 渠道
- 应用程序
- 集成层
- 业务逻辑层
- 系统对象存储集成层
- 能力层（领域服务）
- 核心平台

| 渠道 | 应用或服务 | 集成层 | 业务逻辑层 | 系统对象存储集成层 | 能力层 | 核心平台 |
|----|-------|-----|-------|-----------|-----|------|
|    |       |     |       |           |     |      |

1. 渠道层：这一层是企业与客户或用户进行交互的入口，包括各种渠道，例如网站、移动应用、电话、邮件等等，这些渠道需要与应用程序层进行集成。
2. 应用程序层：这一层是企业应用程序的核心层，包括各种应用程序，例如ERP、CRM、SCM、OA等等，这些应用程序需要与集成层进行集成。
3. 集成层：这一层负责将各种应用程序进行集成，包括各种服务，例如SOA、微服务等等，这些服务需要与业务逻辑层进行集成。
4. 业务逻辑层：这一层负责实现企业的各种业务逻辑，例如订单管理、客户管理、库存管理等等，这些业务逻辑需要与系统对象存储集成层进行集成。
5. 系统对象存储集成层：这一层负责管理企业的各种数据和对象，包括数据库、文件系统等等，这些数据和对象需要与能力层进行集成。
6. 能力层（领域服务）：这一层负责实现企业的核心能力，例如支付、物流、推荐等等，这些能力需要与核心平台进行集成。
7. 核心平台：这一层负责提供企业的基础设施和共享服务，例如身份认证、日志管理、监控等等，这些服务可以供整个企业使用。

| 分层名称      | 分层逻辑               | 描述                                  | 示例                                        |
|-----------|--------------------|-------------------------------------|-------------------------------------------|
| 渠道层       | 企业与客户或用户进行交互的入口    | 包括网站、移动应用、电话、邮件等等，需要与应用程序层进行集成      | 企业网站、移动应用、客服电话、客户邮件                       |
| 应用程序层     | 企业应用程序的核心层         | 包括ERP、CRM、SCM、OA等等，需要与集成层进行集成       | SAP ERP、Salesforce CRM、Oracle SCM、 OA     |
| 集成层       | 将各种应用程序进行集成，包括各种服务 | 包括SOA、微服务等等，需要与业务逻辑层进行集成            | MuleSoft、Apache Camel、IBM Integration Bus |
| 业务逻辑层     | 实现企业的各种业务逻辑        | 包括订单管理、客户管理、库存管理等等，需要与系统对象存储集成层进行集成 | 订单管理系统、客户关系管理系统、库存管理系统                    |
| 系统对象存储集成层 | 管理企业的各种数据和对象       | 包括数据库、文件系统等等，需要与能力层进行集成             | MySQL数据库、MongoDB数据库、Hadoop文件系统            |
| 能力层（领域服务） | 实现企业的核心能力          | 包括支付、物流、推荐等等，需要与核心平台进行集成            | 支付宝支付、顺丰物流、淘宝推荐                           |
| 核心平台      | 提供企业的基础设施和共享服务     | 包括身份认证、日志管理、监控等等，可以供整个企业使用          | 华为云身份认证、ELK日志管理、Prometheus监控              |

## 领域词汇表

当需要生成领域词汇表时，可以采取以下步骤：

1. 收集项目中的术语：通过查看代码、文档和其他相关资料，收集项目中出现的术语和专业词汇。这些术语可以是业务领域中的专有名词，也可以是技术领域中的专业术语。
2. 整理术语并分类：将收集到的术语整理起来，并按照业务领域和技术领域分别进行分类。对于业务领域的术语，可以按照业务流程、业务对象、业务规则等方面进行分类；对于技术领域的术语，可以按照编程语言、框架、设计模式等方面进行分类。
3. 选择自动生成方式：考虑采用自动生成工具，例如 Swagger、Doxygen、Sphinx 或 NaturalDocs 等，根据项目的实际情况选择适合的工具。
4. 使用自动生成工具：使用所选择的自动生成工具，通过扫描代码中的注释和文档字符串，自动生成函数、类、变量、模块等的文档和词汇表。这些工具可以大大减少手动编写词汇表的工作量，提高词汇表的准确性和完整性。
5. 审查和修改：完成自动生成词汇表后，需要对其进行审查和修改，以确保词汇表的准确性和完整性。可以请相关的领域专家或技术专家进行审查和修改，并根据反馈进行相应的修改和完善。
6. 维护和更新：词汇表是一个动态的文档，需要随着项目的发展和变化进行维护和更新。可以定期对词汇表进行更新，添加新的术语和定义，删除不再使用的术语，以保持词汇表的实用性和有效性。

需要注意的是，自动生成的词汇表可能存在一定的误差或缺漏，需要经过人工审查和修改，以确保词汇表的准确性和实用性。

## 架构风格分析

要从项目中获取关于架构风格的知识，你可以考虑以下几个方面：

1. 项目文档：阅读项目文档，了解项目的业务需求、技术选型、系统设计等信息，这些将有助于你了解项目的整体架构风格。
2. 代码结构和组织：通过阅读代码，了解项目的模块划分、依赖关系、代码风格等，这些将有助于你了解项目的架构模式和组件。
3. 接口和通信：了解项目中的接口和通信方式，包括 REST API、消息队列、RPC 调用等，这些将有助于你了解项目的分布式架构和通信模式。
4. 数据存储和处理：了解项目中的数据存储和处理方式，包括数据库、缓存、搜索引擎等，这些将有助于你了解项目的数据架构和处理模式。
5. 日志和监控：了解项目中的日志和监控方式，包括日志记录、指标监控、异常监控等，这些将有助于你了解项目的健康状态和性能优化。
6. 架构决策和演进历史：了解项目的架构决策和演进历史，包括技术选型、架构模式变化、性能优化等，这些将有助于你了解项目的架构演进路径和背景。

## 设计模式

要从项目中获取关于使用到的设计模式的知识，你可以考虑以下几个方面：

1. 类和接口的命名：通过类和接口的命名，可以大致猜测其所使用的设计模式。比如，以“Factory”结尾的类名可能使用了工厂模式。
2. 继承和实现关系：通过类之间的继承和实现关系，可以推测其所使用的设计模式。比如，如果一个类实现了某个接口，而这个接口只有一个方法，那么很可能使用了策略模式。
3. 方法和属性的实现：通过方法和属性的实现方式，可以推测其所使用的设计模式。比如，如果一个方法中有大量的
   if-else分支，而这些分支都是针对不同的对象类型进行不同的操作，那么很可能使用了访问者模式。
4. 代码注释和文档：通过代码注释和文档，可以了解代码的设计意图和实现方式，从而推测所使用的设计模式。
5. 调用关系和代码逻辑：通过代码的调用关系和逻辑，可以推测所使用的设计模式。比如，如果一个类在构造函数中调用了其他类的构造函数，并将其作为属性保存起来，那么很可能使用了装饰器模式。
6. 工程结构和配置文件：通过工程结构和配置文件，可以了解项目的整体架构和依赖关系，从而推测所使用的设计模式。比如，如果在工程中有一个专门存放配置信息的文件夹，并且这些配置信息会被多个类使用，那么很可能使用了享元模式。

## 业务知识

要从代码中获取业务知识，可以考虑以下几个方面：

1. 查看模型类：在项目中，模型类通常用于表示业务中的实体对象，比如用户、订单、商品等。查看模型类的代码，了解业务对象的属性和方法，以及业务对象之间的关系。
2. 查看控制器类：在项目中，控制器类通常用于处理业务的请求和响应，负责调用模型类和视图类的方法。查看控制器类的代码，了解业务请求的处理流程和方法调用关系。
3. 查看视图类：在项目中，视图类通常用于呈现业务数据和交互界面，可以通过视图类了解业务数据的展示方式和交互方式。
4. 查看服务类：在项目中，服务类通常用于处理复杂的业务逻辑，可以通过查看服务类了解业务逻辑的实现方式和处理流程。
5. 查看数据访问类：在项目中，数据访问类通常用于访问数据库和其他数据存储方式，可以通过查看数据访问类了解业务数据的存储方式和读写操作。
6. 查看路由配置：在项目中，路由配置通常用于将请求路由到对应的控制器类和方法，可以通过查看路由配置了解业务请求的路由规则和URL结构。
7. 查看业务流程图：如果项目中有业务流程图或流程文档，可以通过查看这些文档了解业务流程和业务规则。
8. 查看测试用例：通过查看测试用例，了解测试用例所覆盖的业务逻辑，以及测试用例中所使用的数据和操作。

## Agent / Command

- IO Command
- Refactor Command / Replacer
- Analysis Command / Analyzer

