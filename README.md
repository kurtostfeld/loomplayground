Loom Playground
===============

This is a simple project skeleton to try out early builds of Project Loom with Structured Concurrency.

Steps:

1. Download a JDK 19 build: https://jdk.java.net/19/
2. Unpack it. Point your JAVA_HOME environment variable to it. On MacOS it should look like this:

```bash
echo $JAVA_HOME
/Library/Java/JavaVirtualMachines/jdk-19.jdk/Contents/Home
```

3. Make sure you have Apache Maven installed. Then run the Makefile targets:

```bash
make failfast
make firsttosucceed
```

4. To edit this in the JetBrains IntelliJ IDE with full autocomplete, open the `pom.xml`, then open "Project Structure", set the SDK to the Loom variant of JDK 19. You will have to add a new SDK or use an auto-detected SDK.
