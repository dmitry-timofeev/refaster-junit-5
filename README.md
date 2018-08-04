# refaster-playground

Playground for defining [Refaster](https://errorprone.info/docs/refaster)
templates.

## How to use

```bash
./build-rule.sh /path/to/input-rule.java output-rule-name.refaster 
```

Use the output Refaster rule file to produce a patch for your project. 
You will need to enable [Error Prone](https://errorprone.info/docs/installation)
compiler and configure it to use your Refaster rule. A Maven example can be found
in the error prone [repository](https://github.com/google/error-prone/blob/v2.3.1/examples/maven/refaster-based-cleanup/pom.xml). 
