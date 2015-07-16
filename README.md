Protocol Buffers parser and code generator
------------------------------------------

[![Build Status](https://travis-ci.org/kshchepanovskyi/protostuff-compiler.svg?branch=master)](https://travis-ci.org/kshchepanovskyi/protostuff-compiler)
[![Coverity](https://scan.coverity.com/projects/5635/badge.svg)](https://scan.coverity.com/projects/5635)

Targets
-------

| # | Component    | Goal                                                                          |
|---|--------------|-------------------------------------------------------------------------------|
| 1 | parser       | Full compatibility with `proto3`                                              |
| 2 | parser       | Partial compatibility with `proto2` (groups will not be fully supported).     |
| 3 | generator    | `proto3` template: proto-to-proto transformation                              |
| 4 | generator    | `java_nano` template: produce same output as `protoc --javanano_out=...`      |
| 5 | maven plugin | simple maven plugin                                                           |
| 6 | generator    | extensible template system that enables code generation for any language      |


Build
-----

```
$ mvn clean install
```

Maven Plugin
------------

Requirements: Maven 3.1.0+