# Coverage

for more information: http://openclover.org/doc/manual/latest/general--about-code-coverage.html

```
TPC = (BT + BF + SC + MC)/(2*B + S + M) * 100%

where

BT - branches that evaluated to "true" at least once
BF - branches that evaluated to "false" at least once
SC - statements covered
MC - methods entered

B - total number of branches
S - total number of statements
M - total number of methods
```

others:


based:

- https://github.com/bazelbuild/bazel/blob/master/tools/test/CoverageOutputGenerator/java/com/google/devtools/coverageoutputgenerator/LcovParser.java

Can be

- Bazel
