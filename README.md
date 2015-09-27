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

Proto-2-Proto Generator
-----------------------

Main idea is to give users simple proto file processor. This generator should
be able to do following:

* convert proto file syntax: `proto3` -> `proto2`
* remove deprecated fields (messages, service methods)

Current status: development in progress.

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
