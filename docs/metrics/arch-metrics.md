# Architecture Metrics

## Software Sustainability: Research and Practice from a Software Architecture Viewpoint

**Architecture level metric**

|             | Smells                                                                                                                                                                                           | Metrics                                                                                                                                                    | Quality attributes                                                      |
|-------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------|
| Maintenance | Smells about ambiguous and unused interfaces, when functionality of modules are rather small or big and those smells concerning delegation of functionality                                      | Module interaction index, Attribute hiding factor, API function usage index,  Module Size Uniformity index, Module Size Boundedness index                  | Complexity, Modularity, Analyzability, Effectiveness, Understandability |
| Maintenance | Smells that effect to duplicate functionality and coupling between components                                                                                                                    | Clone detection, Coupling between object, Ratio of cohesive interaction, Modularization Quality                                                            | Reusability, Complexity, Modifiability, Modularity                      |
| Maintenance | Smells where multiple components realise the same concern or a component implements an excessive number of concerns. Therefore, we can identify components with a suitable percentage of methods | Concern diffusion over architectural components, Component-level interlacing between concerns, Number of concerns per component, Well- sized Methods Index | Reusability, Modifiability, Understandability, Modularity               |
| Maintenance | We identify components with an excessive number of dependencies, cyclic dependencies and dependencies that crosscut layers                                                                       | Cyclic dependency index, API function usage index, Layer Organization Index, Cumulative component dependency, Excessive structural complexity              | Modularity, Understandability, Changeability Modifiability              |
| Maintenance | Other cross-cutting smells affecting any part of the architecture                                                                                                                                | Architectural smell coverage Architectural smell density                                                                                                   | Cost                                                                    |
| Evolution   | Elements that change too often, Number of elements impacted by a change                                                                                                                          | Instability, Ripple effect, Distance from Main Sequence, Module Interaction Stability Index                                                                | Stability, Evolvability                                                 |
| Evolution   | Likelihood of components that evolve together                                                                                                                                                    | Bi-directional coupling component                                                                                                                          | Complexity, Evolvability                                                |

中文：


- 有模棱两可和未使用的接口，当模块的功能相当小或很大时，这些坏味道起来与功能委托有关
- 坏味道：多个组件实现相同关注点或组件实现过多关注点的地方。 因此，我们可以用合适的方法百分比来识别组件

|             | Smells                            | Metrics                                      | Quality attributes                                                      |
|-------------|-----------------------------------|----------------------------------------------|-------------------------------------------------------------------------|
| Maintenance | 坏味道：有模棱两可和未使用的接口                  | 模块交互指标、属性隐藏因子、API 函数使用指标、模块大小均匀性指标、模块大小有界指标  | Complexity, Modularity, Analyzability, Effectiveness, Understandability |
| Maintenance | 坏味道：重复的功能和组件之间耦合                  | 重复检测、对象间耦合、内聚交互比、模块化质量                       | Reusability, Complexity, Modifiability, Modularity                      |
| Maintenance | 坏味道：多个组件实现相同关注点或组件实现过多关注点的地方      | 架构组件上的关注点扩散、关注点之间的组件级交错、每个组件的关注点数量、规模适中的方法索引 | Reusability, Modifiability, Understandability, Modularity               |
| Maintenance | 识别具有过多依赖关系、循环依赖关系和横切层的依赖关系的组件     | 循环依赖指标、API 函数使用指标、层次组织指标、组件依赖累积、结构复杂度过高      | Modularity, Understandability, Changeability Modifiability              |
| Maintenance | 其它纵向影响架构的部分                       | 架构坏味道覆盖率、架构坏味道密度                             | Cost                                                                    |
| Evolution   | 变化太频繁的元素，受变化影响的元素数量               | 不稳定性、涟漪效应、与主序的距离、模块交互稳定性指数                   | Stability, Evolvability                                                 |
| Evolution   | 组件一起演进的可能性                        | 双向耦合组件                                       | Complexity, Evolvability                                                |


**Architecture knowledge level metrics**

|             | Smells                                        | Metrics                                      | Quality attributes       |
|-------------|-----------------------------------------------|----------------------------------------------|--------------------------|
| Maintenance | Excessive number of decisions and trace links | NodeCount, EdgeCount                         | Complexity, Stability    |
| Maintenance | Too many AK ítems and decision alternatives   | Cost of AK capturing effort                  | Cost                     |
| Evolution   | A change impact on many decisions             | Ripple effect, instability, change proneness | Changeability, Stability |
| Evolution   | Obsolete decisions and frequent changes       | Decision volatility                          | Timeliness               |
