# AI

几个方向

- 正则表达式理解
  - Multi-modal Synthesis of Regular Expressions
- SQL 生成
  - SQLizer: Query Synthesis from Natural Language
- 从代码到语义
  - Bimodal Modelling of Source Code and Natural Language
- 从语义到代码


## To AI ?

- "Learning to Map Natural Language to General Purpose Source Code"
- "Enabling Near Real-Time NLU-Driven Natural Language Programming through Dynamic Grammar Graph-Based Translation"

OpenSource Project:

- [https://github.com/sriniiyer/concode](https://github.com/sriniiyer/concode) Mapping Language to Code in Programmatic Context
- [nl2sql](https://github.com/sriniiyer/nl2sql) - Learning a Neural Semantic Parser with User Feedback
- [CODENN](https://github.com/sriniiyer/codenn) -  Summarizing Source Code using a Neural Attention Model - CODENN 

## Constructing an Interactive Natural Language Interface for Relational Databases

The intellectual contributions of this paper are as follows:

1. Interactive Query Mechanism. We design an interaction mechanism for NLIDBs to enable users to ask complex queries and have them interpreted correctly, with a little interaction help.
2. Query Tree. We design a query tree structure to represent the interpretation of a natural language query from the database’s perspective. A query tree can be explained to the user for verification, and once verified, will almost always be correctly translated to SQL.
3. System Architecture. We provide a modular architecture to support such a query mechanism, in which each component can be designed, and improved, independently. We develop a working software system called NaLIR, instantiating this architecture. We discuss the basic ideas in designing heuristic functions for each component and describe the specific choices made in our system.
4. User Study. We demonstrate, through carefully designed user studies, that NaLIR is usable in practice, on which even naive users are able to handle quite complex ad-hoc query tasks.

Overview

整个系统由三个主要部分组成：查询解释部分、交互式通信器和查询树翻译器。 查询解释部分包括解析树节点映射器（第 4 节）和结构调整器（第 5 节），
负责解释自然语言查询并将解释表示为查询树。 交互式通信器负责与用户通信以确保解释过程的正确性。 可能由用户验证的查询树将在查询树转换器（第 6 节）
中转换为 SQL 语句，然后根据 RDBMS 进行评估。

- 依赖解析器。将自然语言查询转换为 SQL 查询的第一个障碍是从语言上理解自然语言查询。
- 解析树节点映射器。解析树节点映射器识别语言解析树中可以映射到 SQL 组件的节点，并将它们标记为不同的标记。
- 解析树结构调整器。在节点映射（可能与用户进行交互式通信）之后，我们假设每个节点都被我们的系统理解。下一步是从数据库的角度正确理解树结构。然而，这并不容易，因为语言解析树可能不正确，超出我们系统的语义覆盖范围，或者从数据库的角度来看是模棱两可的。在这些情况下，我们调整语言解析树的结构并为其生成候选解释（查询树）。特别地，我们分两步调整解析树的结构。在第一步中，我们重新构造解析树中的节点，使其落入我们系统的句法覆盖范围内（有效解析树）。如果查询有多个候选有效解析树，我们选择最好的一个作为第二步的默认输入，并将其中的前 k 个报告给交互式通信器。在第二步中，对选择的（或默认的）有效解析树进行语义分析，并插入隐式节点以使其在语义上更加合理。这个过程也在用户的监督下进行。插入隐式节点后，我们获得了查询的确切解释，表示为查询树。
- 交互式通讯器。如果系统可能误解了用户，交互式通信器会解释她的查询是如何处理的。在我们的系统中，交互式通信分为三个步骤，分别验证解析树节点映射、解析树结构重构和隐式节点插入的中间结果。对于每个模棱两可的部分，我们生成一个多选选择面板，其中每个选择对应不同的解释。每次用户更改选择时，我们的系统都会立即重新处理后续步骤中的所有歧义。
- 查询树翻译器。 给定用户验证的查询树，翻译器利用其结构在 SQL 表达式中生成适当的结构并完成外键主键 (FK-PK) 连接路径。 结果 SQL 语句可能包含聚合函数、多级子查询和各种类型的连接等。 最后，我们的系统根据 RDBMS 评估翻译后的 SQL 语句，并将查询结果返回给用户。
