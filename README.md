# log4j2-plugins-cache

This plugin fixes conflicting Log4j2Plugins.dat files when building an uberjar using leiningen.

See [LOG4J2-673](https://issues.apache.org/jira/browse/LOG4J2-673) for reference.

You might see error messages such as below if you depend on any Log4j2 plugins in your project.

```text
ERROR StatusLogger Unrecognized format specifier [d]
ERROR StatusLogger Unrecognized conversion specifier [d] starting at position 16 in conversion pattern.
ERROR StatusLogger Unrecognized format specifier [thread]
ERROR StatusLogger Unrecognized conversion specifier [thread] starting at position 25 in conversion pattern.
ERROR StatusLogger Unrecognized format specifier [level]
ERROR StatusLogger Reconfiguration failed: No configuration found for '2b43529a' at 'null' in 'null'
```

The code is based off of the same technique used for Maven in https://github.com/edwgiz/maven-shaded-log4j-transformer

## Usage

Add the following to your project.clj to laod the plugin.

```clj
  :plugins
  [[arctype/log4j2-plugins-cache "1.0.0"]]

  :middleware [leiningen.log4j2-plugins-cache/middleware]
```

## License

Copyright © 2018 Arctype Corporation.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
