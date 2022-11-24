# Docs

## 基础数据

代码：

- 包：嵌套深度
- 文件：复杂度
- 类：类名、长度、行数、复杂度
- 函数：函数名、长度、行号、文件名、调用链

Git：

- 变更的文件
- 变更的函数
- 变更复杂度

设计度量？


架构特征：

- Spring: Controller-Repository-Service-Entity-Component

## 复杂度

"Correlations of Software Code Metrics: An Empirical Study" 

![Extracted Project Metrics](images/Extracted-Project-Metrics.png)

## 重复度

物理重复度：

- Number of lines involved in duplications
- Duplicated lines on new code
- Number of duplicated blocks of lines. At least 10 successive and duplicated statements to count a block. Indentation and string literals are ignored while detecting
- Duplicated blocks on new code
- Number of files involved in duplications.
- Density of duplication = Duplicated lines / Lines * 100 Duplicated lines on new code balanced by statements

逻辑重复度：

AST 判定。

## 基础函数/算子

### Aggregate

数据缓存层：基于基础数据计算

## 机器学习算法

