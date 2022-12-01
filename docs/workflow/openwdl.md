# OpenWDL

## Spec

https://github.com/openwdl/wdl/blob/main/versions/1.1/SPEC.md

```wdl
task hello {
  input {
    File infile
    String pattern
  }

  command <<<
    egrep '~{pattern}' '~{infile}'
  >>>

  runtime {
    container: "my_image:latest"
  }

  output {
    Array[String] matches = read_lines(stdout())
  }
}

workflow wf {
  input {
    File infile
    String pattern
  }

  call hello {
    input: infile, pattern
  }

  output {
    Array[String] matches = hello.matches
  }
}
```

Multiple output

```
task mytask {
  input {
    String s  # required input declaration
    Int? i    # optional input declaration
    Float f   # input delcaration with an initialization
  }

  Boolean b = true  # declaration in the task body

  # This is an error! f is not an input parameter and thus
  # must be initialized.
  File f

  ...

  output {
    Array[String] strarr = ["a", "b"] # output declaration
    File? optfile = "/path/to/file"   # optional output declaration
  } 
}
```