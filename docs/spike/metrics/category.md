# Taxonomy of Static Code Analysis Tools

## STATIC CODE ANALYSIS TOOLS

A. What kind of errors are found by the static code analyzers?

- Syntactic problems,
- unreachable source code,
- undeclared variables,
- non-initialized variables,
- non used functions and procedures, 
- variables used before initialization,
- non use of values from functions,
- wrong use of pointers.

## Categories for systematic tree

1. Input – what types of files can be loaded into tool
    1. Source code – textual source file can be loaded
    2. Byte code – file with Java Byte code or Microsoft Intermediate Language (MSIL) can be loaded
2. Releases – how many releases are per year
    1. Frequently >= 3 times a year – new release of the tool is released three or more times per year
    2. Occasionally < 3 times a year – new release of the tool is released less than three times per year
    3. Obsolete 0 times a year – time from new release is more than a year
3. Supported languages – which programming languages tool supports
    1. .NET – all programming languages which are compiled into libraries or programs of the .NET framework
        1. VB .NET – supports VB.NET
        2. C# - supports C#
    2. Java – supports Java programming language
    3. C, C++ - supports C or C++ programming language.
4. Technology - which technologies are used for searching errors in code
    1. Dataflow – search for errors with dataflow
    2. Syntax – search for errors with syntax correctness
    3. Theorem proving – search for errors with proving different theorems
    4. Model checking – search for errors with model checking
5. Rules – set of rules, which are supported by different static code
    1. Style – inspects the visualization look of the source code
    2. Naming – review of the if the variables are correctly named (spelling, naming standards, ...)
    3. General – general rules of the static code analysis
    4. Concurrency – errors with concurrency running code
    5. Exceptions – errors by throwing or not throwing exceptions
    6. Performance – errors with performance of the applications
    7. Interoperability – errors with common behavior
    8. Security – errors which could impact security of the application
        - SQL – searches for “SQL injections” and other SQL errors
        - Buffer overflow – security errors, which take advantage from buffer overflow
    9. Maintainability – rules for better maintainability of the application
6. Configurability – ability to customize tool
    1. Text document – configuration is set from text document
    2. XML – configuration is set from XML document
    3. GUI – configuration is set through graphic user interface
    4. Ruleset – tool can turn on/off set of rules
7. Extensibility – if tool can be extended with own rules
    1. Possible – it is possible to extend
    2. Not Possible – it is not possible to extend
8. Availability – in what way is tool available
    1. Open Source – tool is free and source code is available
    2. Free – tool is free, but source code is not available
    3. Commercial – tool is available for payment
9. User experience – in what way can tool be used in what is offered to us
    1. Environment integration – how is tool integrated with working environment
    2. Automatic locating errors in code – when tool finds an error, it can put as at the location of the error
    3. Extensive help on faults – if tool gives you help on resolving errors
    4. User interface – availability of user interface
        - Command Line – it can be run from command line prompt
        - GUI – tool can be run from GUI interface
10. Output – presentation of the results from tool
    1. Text file – tool can present results in text file
    2. List – tool can present results in custom user interface control in GUI
    3. XML file – tool can present results in XML data
    4. HTML file – tool can present results in HTML data