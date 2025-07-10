# NullAway with JSpecify

This project demonstrates how to use **NullAway** with **JSpecify** annotations to prevent `NullPointerException` at compile time in a SpringBoot application.

## Overview

NullAway is a static analysis tool that helps eliminate `NullPointerException` bugs by detecting potential null dereferences at compile time.  
When combined with JSpecify annotations, it provides a powerful way to ensure null safety in Java applications.

## Project Structure

```
src/main/java/com/ggardet/nullsafe/
├── NullsafeApplication.java          # Spring Boot main application
├── package-info.java                 # JSpecify configuration with @NullMarked
├── controller/
│   └── UserController.java          # REST controller with intentional bugs
├── model/
│   └── User.java                    # User model with @NonNull/@Nullable annotations
└── service/
    └── UserService.java             # Service layer with null safety examples
```

## Configuration

### Maven Dependencies

```xml
<dependency>
    <groupId>org.jspecify</groupId>
    <artifactId>jspecify</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Maven Compiler Plugin Configuration

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.14.0</version>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>com.google.errorprone</groupId>
                <artifactId>error_prone_core</artifactId>
                <version>2.39.0</version>
            </path>
            <path>
                <groupId>com.uber.nullaway</groupId>
                <artifactId>nullaway</artifactId>
                <version>0.12.7</version>
            </path>
        </annotationProcessorPaths>
        <compilerArgs>
            <arg>-XDcompilePolicy=simple</arg>
            <arg>--should-stop=ifError=FLOW</arg>
            <arg>-Xplugin:ErrorProne
                -XepDisableAllChecks
                -XepOpt:NullAway:JSpecifyMode
                -Xep:NullAway:ERROR
                -XepOpt:NullAway:AnnotatedPackages=com.ggardet.nullsafe
                -XepOpt:NullAway:CheckTestCode=true
            </arg>
        </compilerArgs>
    </configuration>
</plugin>
```

## NullAway Configuration Options

### Core Configuration

- **`-XepOpt:NullAway:JSpecifyMode`**: Enables JSpecify mode for modern null safety annotations
- **`-Xep:NullAway:ERROR`**: Treats NullAway findings as compilation errors
- **`-XepOpt:NullAway:AnnotatedPackages`**: Specifies which packages to analyze
- **`-XepOpt:NullAway:CheckTestCode=true`**: Enables NullAway analysis for test code

### Test Code Analysis

The `-XepOpt:NullAway:CheckTestCode=true` option extends NullAway analysis to test code.  
By default, NullAway only analyzes main source code.

> [!WARNING]  
> NullAway compilation errors in test code will only be displayed when the main source code can compile without errors.  
> If the main source code contains NullAway errors, compilation stops before analyzing tests.

**Error produced:**
```
[ERROR] dereferenced expression user is @Nullable
[ERROR] dereferenced expression user.getEmail() is @Nullable
```

## Running the Examples

Compile and See Errors:

```bash
mvn clean compile
```

This will show compilation errors for the intentional bugs:
```
[ERROR] /path/to/UserController.java:[85,43] [NullAway] dereferenced expression id is @Nullable
[ERROR] /path/to/UserService.java:[63,31] [NullAway] dereferenced expression user.getEmail() is @Nullable
[ERROR] /path/to/UserService.java:[63,20] [NullAway] dereferenced expression user is @Nullable
```

## Resources

- [NullAway Documentation](https://github.com/uber/NullAway)
- [JSpecify Documentation](https://jspecify.dev/)
- [Error Prone Documentation](https://errorprone.info/)


