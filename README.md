Protocol Buffers parser and code generator
------------------------------------------

[![Build Status](https://travis-ci.org/protostuff/protostuff-compiler.svg?branch=master)](https://travis-ci.org/protostuff/protostuff-compiler)

Usage
-----

* [maven plugin](https://github.com/protostuff/protostuff-compiler/wiki/Maven-Plugin)
* [command-line interface](https://github.com/protostuff/protostuff-compiler/wiki/Command-line-interface)

Java Source Code Generator
--------------------------

Current status: development in progress.

Generated code API: [draft](http://www.protostuff.io/documentation/compiler/java/generated-code).

Documentation Generator
-----------------------

`protostuff-compiler` can generate html from proto files.

Sample output: http://www.protostuff.io/samples/protostuff-compiler/html/#com.example.Address

This generator is an alternative to https://github.com/estan/protoc-gen-doc

Requirements
------------

| Component                                 | Version   |
|-------------------------------------------|-----------|
| JDK                                       | 1.8.0_45+ |  
| [Apache Maven](https://maven.apache.org/) | 3.0.5+    |

Build
-----

```
mvn clean install
```
