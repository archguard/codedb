# OO Metric

This is a simple action that will calculate the OO metric for a given project.

TODO: align current ArchGuard metrics

## Statistics

| Metric Label | Metric Name                  | Definition                                                                                                                                                                                                                                            |
|--------------|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ATFD         | Access To Foreign Data       | The number of attributes from unrelated classes belonging to the system, accessed directly or by invoking accessor methods.                                                                                                                           |
| WMC          | Weighted Methods Count       | The sum of complexity of the methods that are defined in the class. We compute the complexity with CYCLO.                                                                                                                                             |
| NOAP         | Number Of Public Attributes  | The number of public attributes of a class                                                                                                                                                                                                            |
| NOAM         | Number Of Accessor Methods   | The number of accessor (getter and setter) methods of a class.                                                                                                                                                                                        |
| LOC          | Lines of Code                | The number of lines of code of an operation or of a class, including blank lines and comments.                                                                                                                                                        |
| CYCLO        | Cyclomatic Complexity        | The maximum number of linearly independent paths in a method. A path is linear if there is no branch in the execution flow of the corresponding code.                                                                                                 |
| MAXNESTING   | Maximum Nesting Level        | The maximum nesting level of control structures within an operation.                                                                                                                                                                                  |
| NOAV         | Number Of Accessed Variables | The total number of variables accessed directly or through accessor methods from the measured operation. Variables include parameters, local variables, but also instance variables and global variables declared in classes belonging to the system. |
| CINT         | Coupling Intensity           | The number of distinct operations called by the measured operation.                                                                                                                                                                                   |
| CM           | Changing Methods             | The number of distinct methods calling the measured method.                                                                                                                                                                                           |
| CC           | Changing Classes             | The number of classes in which the methods that call the measured method are defined in.                                                                                                                                                              |
