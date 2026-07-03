# Java 26 Demo

A Spring Boot application containing examples from several Java releases, interview exercises, animal/polymorphism examples, and SQL/JPA demonstrations. The project includes a separate `java9-module-demo` Gradle module and uses an in-memory H2 database.

## Prerequisites

- JDK 26 (the project currently targets Java 26)
- An internet connection the first time Gradle runs, so the wrapper can download Gradle and project dependencies

You do **not** need to install Gradle or configure an external database. The repository includes the Gradle wrapper, and H2 runs in memory.

Verify Java is available:

```powershell
java --version
```

The output should report Java 26. If your JDK is installed somewhere other than `C:\Program Files\Java\jdk-26.0.1`, update `org.gradle.java.home` in `gradle.properties` or remove that property and set `JAVA_HOME` to your JDK 26 directory.

## Start the application

Start docker on your machine.

From the repository root in PowerShell or Command Prompt:

```powershell
.\gradlew.bat bootRun
```

On macOS or Linux:

```bash
./gradlew bootRun
```

Wait for the log to show that the application has started. It listens on port `8080` by default. Open the Swagger UI to browse and run the available API examples:

- Swagger UI: <http://localhost:8080/swagger-ui.html>
- OpenAPI JSON: <http://localhost:8080/api-docs>
- H2 database console: <http://localhost:8080/h2-console>

To stop the application, press `Ctrl+C` in the terminal where it is running.

## H2 console settings

Use these values on the H2 login screen:

| Setting | Value |
| --- | --- |
| Driver class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:mem:demo-db` |
| User name | `sa` |
| Password | Leave blank |

The database is recreated when the application starts and discarded when it stops.

## Test and build

Run the tests:

```powershell
.\gradlew.bat test
```

Create an executable Spring Boot JAR:

```powershell
.\gradlew.bat bootJar
```

Then run the generated JAR from `build/libs`:

```powershell
java -jar build\libs\Java26Demo-0.0.1-SNAPSHOT.jar
```

On macOS or Linux, replace `gradlew.bat` with `./gradlew` and use `/` in the JAR path.

## Run from an IDE

1. Import the repository as a Gradle project.
2. Select JDK 26 as the project and Gradle JVM.
3. Run `com.JohnBieniek.Java26Demo.Java26DemoApplication`.

## Troubleshooting

- **Gradle cannot find Java:** confirm `java --version`, `JAVA_HOME`, and the `org.gradle.java.home` value in `gradle.properties` all point to JDK 26.
- **Port 8080 is already in use:** start on another port with `.\gradlew.bat bootRun --args="--server.port=8081"`.
- **First startup is slow:** Gradle downloads its distribution and dependencies on the first run; later runs use the local cache.
- **Database changes disappear:** this is expected because the configured H2 database is in memory.
