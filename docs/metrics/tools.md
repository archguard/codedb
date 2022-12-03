# Tools

## Taxonomy of static code analysis tools

静态代码分析工具分类

![](taxonomy-static-code-anlaysis-tools.png)

Table 1:  Analysis of selected static code analysis tools

| Tool       | Rules                                                                       | Configurability     | Extensibility | Technology       | Supported languages | Availability | User experience                                                | Releases                               | Input       | Output          |
|------------|-----------------------------------------------------------------------------|---------------------|---------------|------------------|---------------------|--------------|----------------------------------------------------------------|----------------------------------------|-------------|-----------------|
| CheckStyle | general, style, naming, performance, maintainability                        | XML file, rulesets  | Possible      | Syntax, dataflow | Java                | Open source  | Environment integration, CL                                    | Frequently >= 3 times a year           | Source code | List, XML, HTML |
| FindBugs   | general, style                                                              | Rulesets            | Possible      | Syntax, dataflow | Java                | Open source  | All options in category included                               | Frequently Byte >= 3 times code a year | Bytecode    | List, XML       |
| Gendarme   | General, style, concurrency, exceptions, interoperability, security, naming | Rulesets, GUI       | Possible      | Syntax, dataflow | All .NET            | Open source  | Automatic locating errors in code                              | Frequently >= 3 times a year           | Byte code   | List            |
| StyleCop   | style, maintainability, naming, general                                     | Rulesets, Text file | Possible      | Syntax, dataflow | C#                  | Free         | CL, Environment integration, Automatic locating errors in code | Occasional ly < 3 times a year         | Source code | List            |

## A Taxonomy for Classifying Runtime Verification Tools⋆

Mindmap overviewing the taxonomy of Runtime Verification

![Runtime Verify](runtime-verify.png)

Mindmap for the specification part of the taxonomy

![](specification.png)

Mindmap for the monitor part of the taxonomy

![](monitor.png)

Fig. 4: Mindmap for the deployment part of the taxonomy

![](deployment.png)

Fig. 5: Mindmap for the reaction part of the taxonomy

![](reaction.png)

Fig. 6: Mindmap for the trace part of the taxonomy

![](trace.png)

