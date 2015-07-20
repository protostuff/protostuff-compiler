Protocol Buffers parser and code generator
------------------------------------------

[![Build Status](https://travis-ci.org/kshchepanovskyi/protostuff-compiler.svg?branch=master)](https://travis-ci.org/kshchepanovskyi/protostuff-compiler)
[![Coverity](https://scan.coverity.com/projects/5635/badge.svg)](https://scan.coverity.com/projects/5635)

Targets
-------

| Component    | Goal                                                                          |
|--------------|-------------------------------------------------------------------------------|
| parser       | Full compatibility with `proto3`                                              |
| parser       | Partial compatibility with `proto2` (groups will not be fully supported).     |
| generator    | `proto3` template: proto-to-proto transformation                              |
| generator    | `java_nano` template: produce same output as `protoc --javanano_out=...`      |
| generator    | extensible template system that enables code generation for any language      |
| maven-plugin | simple maven plugin                                                           |
| cli          | command-line interface with `protoc`-like syntax                              |


Build
-----

```
$ mvn clean install
```

Maven Plugin
------------

Requirements: Maven 3.1.0+

HTML Template
-------------

Note for google chrome: if you want to use generated files without running web server,
then you should run it with a special parameter:

```
$ chrome --allow-file-access-from-files index.html
```
