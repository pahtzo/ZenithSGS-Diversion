# ZenithSGS-Diversion
For the SANS Holiday Hack Challenge 2023 Diversion objective.

Nick DeBaggis (pahtzo) - 20231223

A basic Java object deserialize/serialize app for decoding/encoding the deserialization exploit object
to leverage the vulnerabilities found in the SatelliteQueryFileFolderUtility class.

Built with JetBrains IntelliJ IDEA CE using Eclipse Adoptium JDK 11.0.21.9 as the build tools.

A precompiled JAR file is located in the out/artifacts/ZenithSGS_jar directory.

Usage:
```
java -jar ZenithSGS.jar -d "<base64 encoded serialized object to decode>"
java -jar ZenithSGS.jar -sf "<file or directory to list on server>"
java -jar ZenithSGS.jar -sq "<SQL query to run on server>"
java -jar ZenithSGS.jar -su "<SQL update query to run on server>"
```
