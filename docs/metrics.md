# Metrics

## Tools

- PMD
- Sonarqube

## Papers

### A Metrics Suite for Object Oriented Design

- **Number of Methods (NOM)**. Number of methods in a class.
- **Weighted Methods Per Class (WMC)**. Sum of McCabe’s cyclomatic complexity [25] for each method in the class.
- **Coupling Between Object Classes (CBO)**. The number of classes a class depends upon. It counts classes used from both external libraries as well as classes from the project.
- **Response for a Class (RFC)**. The number of methods that can be directly called by a client.
- **Lack of Cohesion in Methods (LCOM)**. The number of pairs of methods in a class that do not access the same set of instance variables.

### SATT: Tailoring Code Metric Thresholds for Different Software Architectures

2016 年，43 引用

Abstract

> 代码度量分析是一种众所周知的评估软件系统质量的方法。 然而，当前的工具和技术并未考虑系统架构（例如 MVC、Android）。 
> 这意味着所有 Class 的评估都是相似的，无论他们的具体职责如何。 在本文中，我们提出了 SATT（软件架构定制阈值），一种检测架构角色(architectural role)
> 在代码度量方面是否与系统中的其他角色有很大不同的方法，并为该角色提供特定的阈值。 
> 我们在 400 多个项目中评估了我们在 2 种不同架构（MVC 和 Android）上的方法。 我们还采访了 6 位专家，以解释为什么某些架构角色与其他角色不同。
> 我们的结果表明，SATT 可以克服传统方法存在的问题，尤其是当某些架构角色呈现出于其他角色截然不同的度量值时。

Spring: Controller-Services-Repository-Entity-Component

Android: Activity-Fragment-AsyncTask or Activity-ViewModel-Repository-Entity-Component

SATT 指标：

- **Dataset creation**. We select systems that follow the analyzed architecture, e.g., Spring MVC applications. We perform this step only once and use the same benchmark to calculate the thresholds for all other architectural roles.
- **Architectural roles extraction**. We identify each class’ architectural role in the benchmark. In case of Spring MVC, CONTROLLER classes are always annotated with @Controller.
- **Metrics calculation**. We calculate code metrics for all classes in the benchmark, regardless of their architectural role. In this example, the McCabe number of all classes.
- **Statistical measurement**. We perform a statistical test to measure the difference between the code metric values in that architectural role (group 1) and the other classes (group 2). As metric values distributions tend not to fol- low a normal distribution (discussed in Section VII), we suggest the use of non-paired Wilcoxon test and Cliff’s Delta between the two groups. Bonferroni correction should be applied, as the approach is performed for all combinations of architectural roles and code metrics.
- **Analysis of the statistical tests**. If the difference is significant and the effect size ranges from medium to large, we continue the approach. Otherwise, we stop. We use Romano et al.’s [30] classification to describe the effect. Supposing D as the effect size, ranging from -1 to 1, |D|<0.147 means “negligible effect", |D|<0.33 means “small effect", |D|<0.474 means “medium effect", and |D|>=0.474 means “large effect".
- **Weight ratio calculation**. From now on, we only look to the classes of the analyzed architectural role. Following the original approach, we use lines of code (LOC) as a weight of all classes. Thus, we calculate LOC for all classes and normalize it for all classes that belong to that architectural role in the benchmark. Normalization ensures that the sum of all weights will be 100%. In the example, suppose that our benchmark contains 100,000 lines of code in CONTROLLER classes, and a class A with 100 lines of code. Thus, A’s weight is 0.001.
- **Weight ratio aggregation**. We order classes according to their metric values in an ascending way. For each class, we aggregate the weights by summing up all the weights from classes that have smaller metric values, i.e., classes that are above the current class.
- **Thresholds derivation**. We extract the code metric value from the class that has its weight aggregation closest to 70% (moderate), 80% (high), and 90% (very high).

### Others ?

- Depth of Inheritance Tree (DIT). The length of the longest path from a class to the root of the inheritance tree.
- Number of Children (NOC). The number of immediate subclasses of a class.
