# Auto-Converter

A service for incrementally converting old NetLogo models to newer versions

## Dependencies

### NPM

Install the version of NPM specified in `service/package.json` and add it to your PATH.

### Java

Install Java 6 and Java 17 to your preferred locations. Create an environment variable called `JAVA6` and set it to the location of the `java` binary from your Java 6 installation. Do the same for Java 17, naming the environment variable `JAVA17`.

### Scala

Install Scala 2.9.2 and Scala 3.7.0 to your preferred locations. Create an environment variable called `SCALA2` and set it to the location of the `scalac` binary from your Scala 2.9.2 installation. Do the same for your Scala 3.7.0 installation, naming the environment variable `SCALA3`.

## Building

Once the above setup is complete, run the following commands to build and run the Auto-Converter service:

```bash
cd service

npm install
npm run build
npm run serve
```

To build and run the front end, run the following commands:

```bash
cd frontend

npm install
npm run dev
```
