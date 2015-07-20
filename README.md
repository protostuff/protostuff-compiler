Protocol Buffers parser and code generator
------------------------------------------

[![Build Status](https://travis-ci.org/kshchepanovskyi/protostuff-compiler.svg?branch=master)](https://travis-ci.org/kshchepanovskyi/protostuff-compiler)
[![Coverity](https://scan.coverity.com/projects/5635/badge.svg)](https://scan.coverity.com/projects/5635)

Targets
-------

Ordered by priority:

| Component    | Goal                                                                      | Status      |
|--------------|---------------------------------------------------------------------------|-------------|
| parser       | Full compatibility with `proto3`.                                         | done        |
| parser       | Partial compatibility with `proto2` (groups will not be fully supported). | done        |
| cli          | Command-line interface with `protoc`-like syntax.                         | done        |
| generator    | `html` template: generate HTML documentation from proto files.            | in progress |
| maven-plugin | Simple maven plugin.                                                      | in progress |
| generator    | `proto3` template: proto-to-proto transformation.                         | in progress |
| generator    | `java_nano` template: produce same output as `protoc --javanano_out=...`. |             |
| generator    | Extensible template system that enables code generation for any language  |             |


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
