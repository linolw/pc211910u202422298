# Guía integral de práctica PC2 — Facturease Perú (NRC 11990)

**Proyecto:** `pc211990u202422298` · **Package:** `pe.com.facturease.platform.u202422298` · **Java:** 26 · **Spring Boot:** 4.1.0 · **Puerto:** 8097 · **PostgreSQL:** `facturease` / esquema `facturease_db`.

**Importante:** material de práctica de un enunciado anterior. El PDF indica que en la evaluación formal NO se permite usar IA para generar código.

## Cómo usar esta guía en IntelliJ desde cero

1. Abre `https://start.spring.io`, elige Maven, Java y Spring Boot 4.1.0. Si Java 26 no está disponible, genera con 25 y corrige `pom.xml` a `<java.version>26</java.version>`. El nombre del proyecto debe ser `pc211990u202422298` y el package `pe.com.facturease.platform.u202422298`. Usa Web MVC, Data JPA, Validation, PostgreSQL, Lombok; Swagger se añade en `pom.xml`.
2. Genera, descomprime y abre el directorio en IntelliJ con File → Open; elige JDK 26 en Project Structure y Maven Runner. Como alternativa, abre el ZIP entregado (ya trae todo).
3. Crea una base de datos llamada `facturease` en pgAdmin y ejecuta `CREATE SCHEMA IF NOT EXISTS facturease_db;` dentro de esa base.
4. Todas las rutas Java que aparecen abajo van dentro de `src/main/java`. En el panel Project, clic derecho sobre `pe.com.facturease.platform.u202422298` → New → Package. Por ejemplo, crea `billing.domain.model.valueobjects` y luego New → Java Class → `InvoiceIdentifier`.
5. Cada bloque del documento es **un archivo completo**, con su ruta exacta. Sustituye `Replace With Your Full Name` por tus nombres y apellidos.
6. Crea un repositorio **vacío y privado** en GitHub cuando tengas la URL/autorización del profesor. Si el repo ya existe con commits, clónalo primero y no fuerces un push sobre historias incompatibles.
7. Para subir progresivamente desde un proyecto creado manualmente, tras cada bloque: `git add .`, `git commit -m "mensaje del bloque"`, `git push -u origin main` (la primera vez), luego `git push`.
8. Si usas el ZIP listo con los 10 commits, `git log --oneline --reverse` ya mostrará el historial: **no repitas los commits**. Conecta el remoto y haz `git push -u origin main`; GitHub recibirá todos los commits.

### Configurar Git y publicar (una sola vez)

```powershell
git init
git branch -M main
git config user.name "TU NOMBRE"
git config user.email "TU_EMAIL_DE_GITHUB"
# Crea primero el repositorio vacío en github.com/new, luego sustituye la URL.
git remote add origin https://github.com/TU_USUARIO/pc211990u202422298.git
```

Los comandos de este bloque son para crear un repositorio nuevo. Si el profesor te da uno, utiliza exactamente la URL y rama indicadas, y comprueba antes `git remote -v` y `git branch -a`.


## COMMIT 1/10 — `chore: initialize Facturease project and PostgreSQL`

Create the Maven project with Java 26 / Spring Boot 4.1.0. Open `pom.xml` in IntelliJ, configure Project SDK and Maven Runner to JDK 26. In pgAdmin create the database `facturease` and execute `db/create_schema.sql`. The local server listens on port 8097. The Maven Wrapper is already included and avoids the broken earlier ZIP.

### Archivo: `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.1.0</version>
        <relativePath/>
    </parent>
    <groupId>pe.com.facturease.platform</groupId>
    <artifactId>pc211990u202422298</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>pc211990u202422298</name>
    <description>Facturease Peru REST API - PC2 practice</description>
    <properties>
        <java.version>26</java.version>
        <lombok.version>1.18.48</lombok.version>
        <springdoc.version>3.1.1</springdoc.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.encryptorcode</groupId>
            <artifactId>pluralize</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <proc>full</proc>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Archivo: `.gitignore`

```gitignore
/target/
/.idea/
*.iml
*.log
.env
.DS_Store
```

### Archivo: `mvnw.cmd`

```bat
@ECHO OFF
SETLOCAL
REM Maven Wrapper (Windows): downloads the Maven distribution on first use.
REM This project uses the only-script approach; no wrapper JAR is needed.
SET "WRAPPER_SCRIPT=%~dp0.mvn\wrapper\run-maven.ps1"
IF NOT EXIST "%WRAPPER_SCRIPT%" (
  ECHO ERROR: Missing .mvn\wrapper\run-maven.ps1
  EXIT /B 1
)
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%WRAPPER_SCRIPT%" %*
EXIT /B %ERRORLEVEL%
```

### Archivo: `mvnw`

```bash
#!/bin/sh
# Maven Wrapper for Unix (only-script style; no wrapper JAR).
set -eu
PROJECT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
PROPS="$PROJECT_DIR/.mvn/wrapper/maven-wrapper.properties"
if [ ! -f "$PROPS" ]; then
  echo 'ERROR: missing .mvn/wrapper/maven-wrapper.properties' >&2
  exit 1
fi
DISTRIBUTION_URL=$(sed -n 's/^distributionUrl=//p' "$PROPS" | head -n 1)
ARCHIVE_NAME="${DISTRIBUTION_URL##*/}"
FOLDER_NAME="${ARCHIVE_NAME%-bin.zip}"
if [ -z "$DISTRIBUTION_URL" ] || [ "$ARCHIVE_NAME" = "$FOLDER_NAME" ]; then
  echo 'ERROR: invalid distributionUrl in Maven wrapper properties' >&2
  exit 1
fi
MAVEN_USER_HOME="${MAVEN_USER_HOME:-$HOME/.m2}"
MAVEN_HOME="$MAVEN_USER_HOME/wrapper/dists/$FOLDER_NAME"
MAVEN_BIN="$MAVEN_HOME/bin/mvn"
if [ ! -x "$MAVEN_BIN" ]; then
  TMP_DIR=$(mktemp -d)
  trap 'rm -rf "$TMP_DIR"' EXIT HUP INT TERM
  printf 'Downloading Apache Maven from %s\n' "$DISTRIBUTION_URL"
  if command -v curl >/dev/null 2>&1; then
    curl --fail --location --silent --show-error "$DISTRIBUTION_URL" --output "$TMP_DIR/$ARCHIVE_NAME"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DISTRIBUTION_URL" -O "$TMP_DIR/$ARCHIVE_NAME"
  else
    echo 'ERROR: curl or wget required on first launch' >&2; exit 1
  fi
  mkdir -p "$TMP_DIR/expanded" "$(dirname "$MAVEN_HOME")"
  unzip -q "$TMP_DIR/$ARCHIVE_NAME" -d "$TMP_DIR/expanded"
  mv "$TMP_DIR/expanded/$FOLDER_NAME" "$MAVEN_HOME"
  rm -rf "$TMP_DIR"
  trap - EXIT HUP INT TERM
fi
exec "$MAVEN_BIN" "$@"
```

### Archivo: `.mvn/wrapper/maven-wrapper.properties`

```properties
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.11/apache-maven-3.9.11-bin.zip
wrapperUrl=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.4/maven-wrapper-3.3.4.jar
```

### Archivo: `.mvn/wrapper/run-maven.ps1`

```powershell
# Maven Wrapper for Windows (only-script style).
# Reads the Maven distribution URL from maven-wrapper.properties, installs it
# under the current user's .m2 directory, and passes commands to mvn.cmd.
# No maven-wrapper.jar is required.
$ErrorActionPreference = 'Stop'

try {
    $wrapperDirectory = Split-Path -Parent $PSCommandPath
    $propertiesPath = Join-Path $wrapperDirectory 'maven-wrapper.properties'
    if (-not (Test-Path -LiteralPath $propertiesPath)) {
        throw "Missing Maven wrapper configuration: $propertiesPath"
    }

    $rawProperties = Get-Content -LiteralPath $propertiesPath -Raw
    $match = [regex]::Match($rawProperties, '(?m)^\s*distributionUrl\s*=\s*(\S+)\s*$')
    if (-not $match.Success) {
        throw 'No distributionUrl found in maven-wrapper.properties.'
    }
    $distributionUrl = $match.Groups[1].Value
    $archiveName = [System.IO.Path]::GetFileName(([uri]$distributionUrl).AbsolutePath)
    if ($archiveName -notmatch '^(apache-maven-[0-9.]+)-bin\.zip$') {
        throw "Unexpected Maven distribution archive: $archiveName"
    }

    $distributionFolder = $Matches[1]
    $mavenUserHome = if ($env:MAVEN_USER_HOME) {
        $env:MAVEN_USER_HOME
    } else {
        Join-Path $HOME '.m2'
    }
    $mavenHome = Join-Path $mavenUserHome ("wrapper\dists\" + $distributionFolder)
    $mavenLauncher = Join-Path $mavenHome 'bin\mvn.cmd'

    if (-not (Test-Path -LiteralPath $mavenLauncher)) {
        $stagingDirectory = Join-Path ([System.IO.Path]::GetTempPath()) ('maven-wrapper-' + [guid]::NewGuid().ToString('N'))
        try {
            New-Item -ItemType Directory -Path $stagingDirectory -Force | Out-Null
            $archivePath = Join-Path $stagingDirectory $archiveName
            Write-Host "Downloading Apache Maven from $distributionUrl"
            [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
            Invoke-WebRequest -UseBasicParsing -Uri $distributionUrl -OutFile $archivePath

            $expandedPath = Join-Path $stagingDirectory 'expanded'
            Expand-Archive -LiteralPath $archivePath -DestinationPath $expandedPath -Force
            $sourceFolder = Join-Path $expandedPath $distributionFolder
            if (-not (Test-Path -LiteralPath (Join-Path $sourceFolder 'bin\mvn.cmd'))) {
                throw 'Downloaded Maven distribution did not contain bin/mvn.cmd.'
            }
            New-Item -ItemType Directory -Path (Split-Path -Parent $mavenHome) -Force | Out-Null
            if (-not (Test-Path -LiteralPath $mavenLauncher)) {
                Move-Item -LiteralPath $sourceFolder -Destination $mavenHome
            }
        }
        finally {
            if (Test-Path -LiteralPath $stagingDirectory) {
                Remove-Item -LiteralPath $stagingDirectory -Recurse -Force -ErrorAction SilentlyContinue
            }
        }
    }

    if (-not (Test-Path -LiteralPath $mavenLauncher)) {
        throw "Maven could not be installed: $mavenLauncher"
    }

    & $mavenLauncher @args
    exit $LASTEXITCODE
}
catch {
    Write-Host ('ERROR: ' + $_.Exception.Message) -ForegroundColor Red
    Write-Host 'Check your internet connection, Maven Central access, and JDK configuration.'
    exit 1
}
```

### Archivo: `db/create_schema.sql`

```sql
-- Execute inside the `facturease` database in pgAdmin.
CREATE SCHEMA IF NOT EXISTS facturease_db;
```

### Archivo: `src/main/resources/application.properties`

```properties
spring.application.name=facturease-platform
server.port=8097
spring.datasource.url=jdbc:postgresql://localhost:5432/facturease?currentSchema=facturease_db
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD:CHANGE_ME}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.default_schema=facturease_db
spring.jpa.hibernate.naming.physical-strategy=pe.com.facturease.platform.u202422298.shared.infrastructure.persistence.jpa.configuration.strategy.SnakeCaseWithPluralizedTablePhysicalNamingStrategy
spring.messages.basename=messages
spring.messages.encoding=UTF-8
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/Pc211990u202422298Application.java`

```java
package pe.com.facturease.platform.u202422298;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry point for the Facturease Peru corporate invoice API.
 * @author Replace With Your Full Name
 */
@SpringBootApplication
@EnableJpaAuditing
public class Pc211990u202422298Application {
    public static void main(String[] args) {
        SpringApplication.run(Pc211990u202422298Application.class, args);
    }
}
```

### Guardar el commit 1

```powershell
git add .
git commit -m "chore: initialize Facturease project and PostgreSQL"
git push -u origin main
```


## COMMIT 2/10 — `feat(shared): add aggregate base, audit and tax value object`

`TaxSummary` belongs to `shared`, not `billing`. It uses `BigDecimal` with an exact `0.18` multiplier and throws a subclass of `IllegalArgumentException` when invalid. Auditing uses `@MappedSuperclass` while the aggregate base is independent from JPA.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/domain/model/aggregates/AbstractDomainAggregateRoot.java`

```java
package pe.com.facturease.platform.u202422298.shared.domain.model.aggregates;

import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

/**
 * Base for domain aggregate roots, with no JPA persistence concerns.
 * Adapted from the provided Learning Center v2620 reference.
 *
 * @param <T> the concrete aggregate root type
 * @author Replace With Your Full Name
 */
public abstract class AbstractDomainAggregateRoot<T extends AbstractDomainAggregateRoot<T>>
        extends AbstractAggregateRoot<T> {

    /**
     * Registers a domain event.
     * @param event event to register
     */
    protected void registerDomainEvent(Object event) {
        super.registerEvent(event);
    }

    /**
     * Returns currently registered domain events.
     * @return domain events
     */
    @Override
    public Collection<Object> domainEvents() {
        return super.domainEvents();
    }

    /**
     * Clears pending domain events.
     */
    @Override
    public void clearDomainEvents() {
        super.clearDomainEvents();
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/domain/model/exceptions/BusinessRuleViolationException.java`

```java
package pe.com.facturease.platform.u202422298.shared.domain.model.exceptions;

/** Shared domain exception for illegal business states with a localization key.
 * @author Replace With Your Full Name
 */
public class BusinessRuleViolationException extends IllegalStateException {
    private final String messageKey;

    public BusinessRuleViolationException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public String messageKey() { return messageKey; }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/domain/model/exceptions/InvalidCalculatedTaxException.java`

```java
package pe.com.facturease.platform.u202422298.shared.domain.model.exceptions;

/** Illegal argument error for a tax amount inconsistent with 18 percent VAT.
 * @author Replace With Your Full Name
 */
public class InvalidCalculatedTaxException extends IllegalArgumentException {
    public InvalidCalculatedTaxException() {
        super("Calculated tax must equal 18 percent of net amount.");
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/domain/model/valueobjects/TaxSummary.java`

```java
package pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects;

import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.InvalidCalculatedTaxException;
import java.math.BigDecimal;

/**
 * Immutable tax summary; calculatedTax must equal 18% of netAmount.
 * The statement does not define rounding, so the check uses exact decimal arithmetic.
 * @author Replace With Your Full Name
 */
public record TaxSummary(BigDecimal netAmount, BigDecimal calculatedTax) {
    public TaxSummary {
        if (netAmount == null || calculatedTax == null) {
            throw new IllegalArgumentException("Net amount and calculated tax are required.");
        }
        BigDecimal expected = netAmount.multiply(new BigDecimal("0.18"));
        if (expected.compareTo(calculatedTax) != 0) {
            throw new InvalidCalculatedTaxException();
        }
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/infrastructure/persistence/jpa/entities/AuditableAbstractPersistenceEntity.java`

```java
package pe.com.facturease.platform.u202422298.shared.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * Infrastructure-only base entity for generated IDs and JPA auditing.
 * Adapted from the provided Learning Center v2620 reference.
 * @author Replace With Your Full Name
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableAbstractPersistenceEntity {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;
}
```

### Guardar el commit 2

```powershell
git add .
git commit -m "feat(shared): add aggregate base, audit and tax value object"
git push
```


## COMMIT 3/10 — `feat(shared): configure naming, i18n, Swagger and errors`

The PhysicalNamingStrategy converts `Invoice` to `invoices` and camelCase fields to snake_case. i18n responds to `Accept-Language` (English or Spanish), OpenAPI configures Swagger, and error handlers produce HTTP Problem Details.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/application/result/ApplicationError.java`

```java
package pe.com.facturease.platform.u202422298.shared.application.result;

/** Application-layer error independent of the HTTP transport.
 * @param code machine-readable error category
 * @param messageKey resource-bundle key used by REST translation
 * @author Replace With Your Full Name
 */
public record ApplicationError(String code, String messageKey) {
    public static ApplicationError conflict(String messageKey) {
        return new ApplicationError("CONFLICT", messageKey);
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/application/result/Result.java`

```java
package pe.com.facturease.platform.u202422298.shared.application.result;

/** Explicit success/failure return type used by command services.
 * @param <T> successful value type
 * @param <E> failure type
 * @author Replace With Your Full Name
 */
public sealed interface Result<T, E> {
    record Success<T, E>(T value) implements Result<T, E> {}
    record Failure<T, E>(E error) implements Result<T, E> {}

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/infrastructure/documentation/openapi/configuration/OpenApiConfiguration.java`

```java
package pe.com.facturease.platform.u202422298.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/** Configures API documentation and Swagger UI metadata.
 * @author Replace With Your Full Name
 */
@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI factureaseOpenApi() {
        return new OpenAPI()
            .info(new Info().title("Facturease Peru API")
                    .description("Corporate electronic invoice registration API")
                    .version("1.0.0"))
            .servers(List.of(new Server().url("http://localhost:8097")
                    .description("Local development")));
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/infrastructure/i18n/configuration/LocaleConfiguration.java`

```java
package pe.com.facturease.platform.u202422298.shared.infrastructure.i18n.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import java.util.List;
import java.util.Locale;

/** Reads supported languages from the Accept-Language request header.
 * @author Replace With Your Full Name
 */
@Configuration
public class LocaleConfiguration {
    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setSupportedLocales(List.of(
                Locale.ENGLISH,
                Locale.US,
                Locale.forLanguageTag("es"),
                Locale.forLanguageTag("es-PE")));
        return resolver;
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/infrastructure/persistence/jpa/configuration/strategy/SnakeCaseWithPluralizedTablePhysicalNamingStrategy.java`

```java
package pe.com.facturease.platform.u202422298.shared.infrastructure.persistence.jpa.configuration.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import java.util.Locale;

import static io.github.encryptorcode.pluralize.Pluralize.pluralize;

/** Converts table names to plural snake_case and other names to snake_case.
 * @author Replace With Your Full Name
 */
public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy implements PhysicalNamingStrategy {
    @Override
    public Identifier toPhysicalCatalogName(Identifier id, JdbcEnvironment env) {
        return id;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier id, JdbcEnvironment env) {
        return snake(id);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier id, JdbcEnvironment env) {
        if (id == null) return null;
        return snake(Identifier.toIdentifier(pluralize(id.getText()), id.isQuoted()));
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier id, JdbcEnvironment env) {
        return snake(id);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier id, JdbcEnvironment env) {
        return snake(id);
    }

    private Identifier snake(Identifier id) {
        if (id == null) return null;
        String converted = id.getText()
                .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
                .toLowerCase(Locale.ROOT);
        return Identifier.toIdentifier(converted, id.isQuoted());
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/interfaces/rest/GlobalExceptionHandler.java`

```java
package pe.com.facturease.platform.u202422298.shared.interfaces.rest;

import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.BusinessRuleViolationException;
import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.InvalidCalculatedTaxException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Locale;

/** Converts errors to localized HTTP problem-details responses.
 * @author Replace With Your Full Name
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messages;
    public GlobalExceptionHandler(MessageSource messages) { this.messages = messages; }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, Locale locale) {
        var fields = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField).distinct().toList();
        var response = problem(HttpStatus.BAD_REQUEST, "error.validation", "VALIDATION_ERROR", locale);
        response.getBody().setProperty("fields", fields);
        return response;
    }

    @ExceptionHandler(InvalidCalculatedTaxException.class)
    public ResponseEntity<ProblemDetail> handleTax(InvalidCalculatedTaxException ex, Locale locale) {
        return problem(HttpStatus.BAD_REQUEST, "error.tax.invalid", "INVALID_CALCULATED_TAX", locale);
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ProblemDetail> handleRule(BusinessRuleViolationException ex, Locale locale) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, ex.messageKey(), "BUSINESS_RULE_VIOLATION", locale);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraint(DataIntegrityViolationException ex, Locale locale) {
        return problem(HttpStatus.CONFLICT, "invoice.duplicate", "CONFLICT", locale);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleJson(HttpMessageNotReadableException ex, Locale locale) {
        return problem(HttpStatus.BAD_REQUEST, "error.invalid.json", "INVALID_REQUEST", locale);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleArgument(IllegalArgumentException ex, Locale locale) {
        return problem(HttpStatus.BAD_REQUEST, "error.invalid.argument", "INVALID_ARGUMENT", locale);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception ex, Locale locale) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "error.server", "UNEXPECTED_ERROR", locale);
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String key, String code, Locale locale) {
        var detail = ProblemDetail.forStatusAndDetail(status,
                messages.getMessage(key, null, locale));
        String titleKey = switch (status) {
            case BAD_REQUEST -> "error.title.bad-request";
            case CONFLICT -> "error.title.conflict";
            case UNPROCESSABLE_ENTITY -> "error.title.unprocessable-entity";
            default -> "error.title.internal-server-error";
        };
        detail.setTitle(messages.getMessage(titleKey, null, locale));
        detail.setProperty("code", code);
        return ResponseEntity.status(status).body(detail);
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/interfaces/rest/transform/ProblemDetailFromApplicationErrorAssembler.java`

```java
package pe.com.facturease.platform.u202422298.shared.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import java.util.Locale;

/** Maps application failures to localized HTTP ProblemDetail responses.
 * @author Replace With Your Full Name
 */
public final class ProblemDetailFromApplicationErrorAssembler {
    private ProblemDetailFromApplicationErrorAssembler() {}

    public static ResponseEntity<ProblemDetail> toResponseFromApplicationError(
            ApplicationError error, MessageSource messages, Locale locale) {
        HttpStatus status = "CONFLICT".equals(error.code())
                ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
        String titleKey = status == HttpStatus.CONFLICT
                ? "error.title.conflict" : "error.title.bad-request";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status,
                messages.getMessage(error.messageKey(), null, locale));
        problem.setTitle(messages.getMessage(titleKey, null, locale));
        problem.setProperty("code", error.code());
        return ResponseEntity.status(status).body(problem);
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/shared/interfaces/rest/transform/ResponseEntityAssembler.java`

```java
package pe.com.facturease.platform.u202422298.shared.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import pe.com.facturease.platform.u202422298.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Locale;
import java.util.function.Function;

/** Converts a CQRS command result to an HTTP response.
 * @author Replace With Your Full Name
 */
public final class ResponseEntityAssembler {
    private ResponseEntityAssembler() {}

    public static <T, R> ResponseEntity<?> toResponseEntityFromResult(
            Result<T, ApplicationError> result,
            Function<T, R> successMapper,
            HttpStatus successStatus,
            MessageSource messages,
            Locale locale) {
        return switch (result) {
            case Result.Success<T, ApplicationError> success ->
                    ResponseEntity.status(successStatus)
                            .body(successMapper.apply(success.value()));
            case Result.Failure<T, ApplicationError> failure ->
                    ProblemDetailFromApplicationErrorAssembler
                            .toResponseFromApplicationError(failure.error(), messages, locale);
        };
    }
}
```

### Archivo: `src/main/resources/messages.properties`

```properties
error.title.bad-request=Bad request
error.title.conflict=Conflict
error.title.unprocessable-entity=Unprocessable entity
error.title.internal-server-error=Internal server error
error.validation=The request contains missing or invalid fields.
error.invalid.argument=One or more argument values are invalid.
error.invalid.json=The request body is invalid JSON or contains invalid types.
error.tax.invalid=Calculated tax must equal 18 percent of net amount.
error.invoice.items=ISSUED and PAID invoices require more than zero items.
invoice.duplicate=An invoice with this invoice identifier already exists.
error.server=An unexpected error occurred.
```

### Archivo: `src/main/resources/messages_es.properties`

```properties
error.title.bad-request=Solicitud incorrecta
error.title.conflict=Conflicto
error.title.unprocessable-entity=Entidad no procesable
error.title.internal-server-error=Error interno del servidor
error.validation=La solicitud tiene campos ausentes o invalidos.
error.invalid.argument=Uno o mas argumentos son invalidos.
error.invalid.json=El cuerpo de la solicitud contiene JSON o tipos invalidos.
error.tax.invalid=El impuesto calculado debe equivaler al 18 por ciento del monto neto.
error.invoice.items=Las facturas ISSUED y PAID requieren mas de cero items.
invoice.duplicate=Ya existe una factura con ese identificador.
error.server=Ocurrio un error inesperado.
```

### Guardar el commit 3

```powershell
git add .
git commit -m "feat(shared): configure naming, i18n, Swagger and errors"
git push
```


## COMMIT 4/10 — `feat(billing): define invoice identifier and statuses`

`InvoiceIdentifier` is a UUID Value Object; `InvoiceStatus` is an enum of `DRAFT`, `ISSUED`, and `PAID`, where `DRAFT` is used when absent. Do not put domain rules in controllers.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/domain/model/valueobjects/InvoiceIdentifier.java`

```java
package pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

/** Domain value object identifying an invoice by external UUID.
 * @author Replace With Your Full Name
 */
public record InvoiceIdentifier(UUID value) {
    public InvoiceIdentifier { Objects.requireNonNull(value, "Invoice identifier is required."); }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/domain/model/valueobjects/InvoiceStatus.java`

```java
package pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects;

/** Allowed invoice statuses; DRAFT is the default.
 * @author Replace With Your Full Name
 */
public enum InvoiceStatus {
    DRAFT, ISSUED, PAID;
    public static InvoiceStatus fromString(String text) {
        if (text == null || text.isBlank()) return DRAFT;
        try { return InvoiceStatus.valueOf(text); }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unsupported invoice status: " + text, ex);
        }
    }
}
```

### Guardar el commit 4

```powershell
git add .
git commit -m "feat(billing): define invoice identifier and statuses"
git push
```


## COMMIT 5/10 — `feat(billing): add Invoice aggregate, command and repository`

`Invoice` is the Aggregate Root with six business fields. `ISSUED` and `PAID` require `itemCount > 0`, enforced in its constructor through a subtype of `IllegalStateException`. Repository declares operations only; it does not contain Spring Data JPA.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/domain/model/aggregates/Invoice.java`

```java
package pe.com.facturease.platform.u202422298.billing.domain.model.aggregates;

import pe.com.facturease.platform.u202422298.billing.domain.model.exceptions.InvalidInvoiceItemCountException;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceStatus;
import pe.com.facturease.platform.u202422298.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects.TaxSummary;
import java.util.Objects;

/** Aggregate Root for a corporate electronic invoice.
 * @author Replace With Your Full Name
 */
public class Invoice extends AbstractDomainAggregateRoot<Invoice> {
    private final Long id;
    private final InvoiceIdentifier invoiceIdentifier;
    private final String description;
    private final InvoiceStatus status;
    private final TaxSummary taxSummary;
    private final Integer itemCount;

    /** Creates a new invoice without its database-generated identifier. */
    public Invoice(InvoiceIdentifier invoiceIdentifier, String description,
                   InvoiceStatus status, TaxSummary taxSummary, Integer itemCount) {
        this(null, invoiceIdentifier, description, status, taxSummary, itemCount);
    }

    private Invoice(Long id, InvoiceIdentifier invoiceIdentifier, String description,
                    InvoiceStatus status, TaxSummary taxSummary, Integer itemCount) {
        this.id = id;
        this.invoiceIdentifier = Objects.requireNonNull(invoiceIdentifier, "Invoice identifier is required.");
        this.description = description;
        this.status = status == null ? InvoiceStatus.DRAFT : status;
        this.taxSummary = Objects.requireNonNull(taxSummary, "Tax summary is required.");
        if (itemCount == null) throw new IllegalArgumentException("Item count is required.");
        if (this.status != InvoiceStatus.DRAFT && itemCount <= 0) {
            throw new InvalidInvoiceItemCountException();
        }
        this.itemCount = itemCount;
    }

    /** Rebuilds an aggregate from its persistence representation.
     * @return a populated Invoice aggregate
     */
    public static Invoice reconstitute(Long id, InvoiceIdentifier identifier, String description,
                                       InvoiceStatus status, TaxSummary taxSummary, Integer itemCount) {
        return new Invoice(Objects.requireNonNull(id, "Persisted id is required."),
                identifier, description, status, taxSummary, itemCount);
    }

    public Long getId() { return id; }
    public InvoiceIdentifier getInvoiceIdentifier() { return invoiceIdentifier; }
    public String getDescription() { return description; }
    public InvoiceStatus getStatus() { return status; }
    public TaxSummary getTaxSummary() { return taxSummary; }
    public Integer getItemCount() { return itemCount; }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/domain/model/exceptions/InvalidInvoiceItemCountException.java`

```java
package pe.com.facturease.platform.u202422298.billing.domain.model.exceptions;

import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.BusinessRuleViolationException;

/** Illegal state for issuing or paying an empty invoice.
 * @author Replace With Your Full Name
 */
public class InvalidInvoiceItemCountException extends BusinessRuleViolationException {
    public InvalidInvoiceItemCountException() {
        super("error.invoice.items", "ISSUED and PAID invoices require at least one item.");
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/domain/model/commands/CreateInvoiceCommand.java`

```java
package pe.com.facturease.platform.u202422298.billing.domain.model.commands;

import java.math.BigDecimal;

/** CQRS command representing the intention to create an invoice.
 * @author Replace With Your Full Name
 */
public record CreateInvoiceCommand(
        String invoiceIdentifier,
        String description,
        String status,
        BigDecimal netAmount,
        BigDecimal calculatedTax,
        Integer itemCount) { }
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/domain/repositories/InvoiceRepository.java`

```java
package pe.com.facturease.platform.u202422298.billing.domain.repositories;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;

/** Domain repository port; not coupled to Spring Data JPA.
 * @author Replace With Your Full Name
 */
public interface InvoiceRepository {
    boolean existsByInvoiceIdentifier(InvoiceIdentifier identifier);
    Invoice save(Invoice invoice);
}
```

### Guardar el commit 5

```powershell
git add .
git commit -m "feat(billing): add Invoice aggregate, command and repository"
git push
```


## COMMIT 6/10 — `feat(billing): implement invoice command service`

CommandService orchestrates the use case: convert UUID → check duplicates → create TaxSummary → create Invoice → save via domain repository. `@Transactional` ensures persistence participates in a transaction.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/application/commandservices/InvoiceCommandService.java`

```java
package pe.com.facturease.platform.u202422298.billing.application.commandservices;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.commands.CreateInvoiceCommand;
import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import pe.com.facturease.platform.u202422298.shared.application.result.Result;

/** Use-case contract for invoice commands.
 * @author Replace With Your Full Name
 */
public interface InvoiceCommandService {
    Result<Invoice, ApplicationError> handle(CreateInvoiceCommand command);
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/application/internal/commandservices/InvoiceCommandServiceImpl.java`

```java
package pe.com.facturease.platform.u202422298.billing.application.internal.commandservices;

import pe.com.facturease.platform.u202422298.billing.application.commandservices.InvoiceCommandService;
import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.commands.CreateInvoiceCommand;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceStatus;
import pe.com.facturease.platform.u202422298.billing.domain.repositories.InvoiceRepository;
import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import pe.com.facturease.platform.u202422298.shared.application.result.Result;
import pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects.TaxSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

/** Application use case: enforce uniqueness and persist a valid invoice.
 * @author Replace With Your Full Name
 */
@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {
    private final InvoiceRepository repository;
    public InvoiceCommandServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<Invoice, ApplicationError> handle(CreateInvoiceCommand command) {
        if (command == null) throw new IllegalArgumentException("Command is required.");

        InvoiceIdentifier identifier = new InvoiceIdentifier(
                UUID.fromString(command.invoiceIdentifier()));
        if (repository.existsByInvoiceIdentifier(identifier)) {
            return Result.failure(ApplicationError.conflict("invoice.duplicate"));
        }
        InvoiceStatus status = InvoiceStatus.fromString(command.status());
        TaxSummary tax = new TaxSummary(command.netAmount(), command.calculatedTax());
        Invoice invoice = new Invoice(identifier, command.description(), status,
                tax, command.itemCount());
        return Result.success(repository.save(invoice));
    }
}
```

### Guardar el commit 6

```powershell
git add .
git commit -m "feat(billing): implement invoice command service"
git push
```


## COMMIT 7/10 — `feat(billing): implement JPA invoice persistence adapter`

JPA entity inherits `id`, `createdAt`, and `updatedAt` from the shared auditing base. Repository Adapter implements the domain contract and maps to/from PersistenceEntity. Database table should become `facturease_db.invoices`.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/infrastructure/persistence/jpa/adapters/InvoiceRepositoryImpl.java`

```java
package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.adapters;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.domain.repositories.InvoiceRepository;
import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.assemblers.InvoicePersistenceAssembler;
import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.repositories.InvoicePersistenceRepository;
import org.springframework.stereotype.Repository;

/** Repository adapter connecting the domain repository to JPA.
 * @author Replace With Your Full Name
 */
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
    private final InvoicePersistenceRepository persistenceRepository;
    public InvoiceRepositoryImpl(InvoicePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public boolean existsByInvoiceIdentifier(InvoiceIdentifier identifier) {
        return persistenceRepository.existsByInvoiceIdentifier(identifier.value());
    }

    @Override
    public Invoice save(Invoice invoice) {
        var entity = InvoicePersistenceAssembler.toPersistenceFromDomain(invoice);
        var saved = persistenceRepository.saveAndFlush(entity);
        return InvoicePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/infrastructure/persistence/jpa/assemblers/InvoicePersistenceAssembler.java`

```java
package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.assemblers;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.entities.InvoicePersistenceEntity;
import pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects.TaxSummary;

/** Maps invoice aggregate to and from its JPA representation.
 * @author Replace With Your Full Name
 */
public final class InvoicePersistenceAssembler {
    private InvoicePersistenceAssembler() { }
    public static InvoicePersistenceEntity toPersistenceFromDomain(Invoice domain) {
        var entity = new InvoicePersistenceEntity();
        entity.setId(domain.getId());
        entity.setInvoiceIdentifier(domain.getInvoiceIdentifier().value());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setNetAmount(domain.getTaxSummary().netAmount());
        entity.setCalculatedTax(domain.getTaxSummary().calculatedTax());
        entity.setItemCount(domain.getItemCount());
        return entity;
    }

    public static Invoice toDomainFromPersistence(InvoicePersistenceEntity entity) {
        return Invoice.reconstitute(entity.getId(),
                new InvoiceIdentifier(entity.getInvoiceIdentifier()),
                entity.getDescription(), entity.getStatus(),
                new TaxSummary(entity.getNetAmount(), entity.getCalculatedTax()),
                entity.getItemCount());
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/infrastructure/persistence/jpa/entities/InvoicePersistenceEntity.java`

```java
package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.entities;

import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceStatus;
import pe.com.facturease.platform.u202422298.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

/** Persistence entity (not the domain aggregate).
 * Physical naming strategy maps Invoice to facturease_db.invoices.
 * @author Replace With Your Full Name
 */
@Entity
@Table(name = "Invoice", schema = "facturease_db")
public class InvoicePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true)
    private UUID invoiceIdentifier;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    @Column(nullable = false, columnDefinition = "numeric")
    private BigDecimal netAmount;

    @Column(nullable = false, columnDefinition = "numeric")
    private BigDecimal calculatedTax;

    @Column(nullable = false)
    private Integer itemCount;

    public InvoicePersistenceEntity() { }
    public UUID getInvoiceIdentifier() { return invoiceIdentifier; }
    public void setInvoiceIdentifier(UUID value) { invoiceIdentifier = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }
    public InvoiceStatus getStatus() { return status; }
    public void setStatus(InvoiceStatus value) { status = value; }
    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal value) { netAmount = value; }
    public BigDecimal getCalculatedTax() { return calculatedTax; }
    public void setCalculatedTax(BigDecimal value) { calculatedTax = value; }
    public Integer getItemCount() { return itemCount; }
    public void setItemCount(Integer value) { itemCount = value; }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/infrastructure/persistence/jpa/repositories/InvoicePersistenceRepository.java`

```java
package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.repositories;

import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.entities.InvoicePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/** Spring Data JPA repository for invoice rows.
 * @author Replace With Your Full Name
 */
public interface InvoicePersistenceRepository extends JpaRepository<InvoicePersistenceEntity, Long> {
    boolean existsByInvoiceIdentifier(UUID identifier);
}
```

### Guardar el commit 7

```powershell
git add .
git commit -m "feat(billing): implement JPA invoice persistence adapter"
git push
```


## COMMIT 8/10 — `feat(billing): add REST resources and assemblers`

HTTP Resource is a flat JSON object: invoiceIdentifier (String), description, status, netAmount, calculatedTax, itemCount. Response uses exact required names `invoiceId`, `netAmountValue`, `calculatedTaxValue`. Assemblers bridge the layers.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/interfaces/rest/resources/CreateInvoiceResource.java`

```java
package pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** Flat JSON request used to create an invoice; no ID in request.
 * @author Replace With Your Full Name
 */
public record CreateInvoiceResource(
        @NotBlank
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
        @Schema(example = "f17f40be-a9d3-4e3c-9384-885ab050e54a")
        String invoiceIdentifier,
        String description,
        @Schema(description = "Defaults to DRAFT when absent", example = "ISSUED")
        String status,
        @NotNull BigDecimal netAmount,
        @NotNull BigDecimal calculatedTax,
        @NotNull Integer itemCount) { }
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/interfaces/rest/resources/InvoiceResource.java`

```java
package pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources;

import java.math.BigDecimal;

/** HTTP response with the exact flat field names required by PC2.
 * @author Replace With Your Full Name
 */
public record InvoiceResource(Long id, String invoiceId, String description,
        String status, BigDecimal netAmountValue, BigDecimal calculatedTaxValue,
        Integer itemCount) { }
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/interfaces/rest/transform/CreateInvoiceCommandFromResourceAssembler.java`

```java
package pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.billing.domain.model.commands.CreateInvoiceCommand;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources.CreateInvoiceResource;

/** Converts the HTTP create resource into the CQRS command.
 * @author Replace With Your Full Name
 */
public final class CreateInvoiceCommandFromResourceAssembler {
    private CreateInvoiceCommandFromResourceAssembler() { }
    public static CreateInvoiceCommand toCommandFromResource(CreateInvoiceResource resource) {
        return new CreateInvoiceCommand(resource.invoiceIdentifier(), resource.description(),
                resource.status(), resource.netAmount(), resource.calculatedTax(),
                resource.itemCount());
    }
}
```

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/interfaces/rest/transform/InvoiceResourceFromEntityAssembler.java`

```java
package pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources.InvoiceResource;

/** Converts a saved Invoice aggregate into an HTTP response.
 * @author Replace With Your Full Name
 */
public final class InvoiceResourceFromEntityAssembler {
    private InvoiceResourceFromEntityAssembler() { }
    public static InvoiceResource toResourceFromEntity(Invoice invoice) {
        return new InvoiceResource(invoice.getId(),
                invoice.getInvoiceIdentifier().value().toString(),
                invoice.getDescription(), invoice.getStatus().name(),
                invoice.getTaxSummary().netAmount(),
                invoice.getTaxSummary().calculatedTax(),
                invoice.getItemCount());
    }
}
```

### Guardar el commit 8

```powershell
git add .
git commit -m "feat(billing): add REST resources and assemblers"
git push
```


## COMMIT 9/10 — `feat(billing): expose POST invoices endpoint`

Controller exposes ONLY `POST /api/v1/invoices` and returns 201 on success. It does not implement business rules or SQL. Validations happen in the Resource, Value Object and Aggregate.

### Archivo: `src/main/java/pe/com/facturease/platform/u202422298/billing/interfaces/rest/InvoicesController.java`

```java
package pe.com.facturease.platform.u202422298.billing.interfaces.rest;

import pe.com.facturease.platform.u202422298.billing.application.commandservices.InvoiceCommandService;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources.CreateInvoiceResource;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform.CreateInvoiceCommandFromResourceAssembler;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import pe.com.facturease.platform.u202422298.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

/** REST entry point for corporate invoice creation.
 * @author Replace With Your Full Name
 */
@RestController
@RequestMapping(value = "/api/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invoices", description = "Corporate electronic invoices")
public class InvoicesController {
    private final InvoiceCommandService service;
    private final MessageSource messages;
    public InvoicesController(InvoiceCommandService service, MessageSource messages) {
        this.service = service;
        this.messages = messages;
    }

    /** Registers an invoice and returns the generated ID.
     * @param resource request data
     * @param locale resolved request locale
     * @return HTTP 201 resource or localized error
     */
    @Operation(summary = "Create a corporate electronic invoice",
            description = "Validates the 18 percent tax and item count rules before storing the invoice.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Invoice created"),
            @ApiResponse(responseCode = "400", description = "Invalid input or tax value"),
            @ApiResponse(responseCode = "409", description = "Duplicate invoice identifier"),
            @ApiResponse(responseCode = "422", description = "ISSUED or PAID invoice has zero items")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody CreateInvoiceResource resource, Locale locale) {
        var command = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = service.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result,
                InvoiceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED, messages, locale);
    }
}
```

### Guardar el commit 9

```powershell
git add .
git commit -m "feat(billing): expose POST invoices endpoint"
git push
```

## COMMIT 10/10 — `docs: add examples, README and verification`

En IntelliJ crea `docs/examples` (directorio) y abre los cuatro JSON. Los archivos se incluyen a continuación. No necesitas añadir endpoints GET, seguridad, CORS ni testing automatizado porque no forman parte del alcance.

### Archivo: `README.md`

```markdown
# Facturease Peru - Corporate Electronic Invoice API

**Author:** Replace With Your Full Name

## Purpose
REST API for registering corporate electronic invoices using Domain-Driven Design,
CQRS commands, layered architecture, repository adapters, and Spring Data JPA.

## Technical stack
Java 26 · Spring Boot 4.1.0 · Maven · PostgreSQL · Spring Data JPA · Swagger/OpenAPI.

## API
`POST http://localhost:8097/api/v1/invoices`

Request fields: invoiceIdentifier (UUID string), description (optional), status
(optional, DRAFT by default), netAmount, calculatedTax, itemCount.
The generated `id` is not part of the POST request.

Successful response: HTTP 201 containing id, invoiceId, description, status,
netAmountValue, calculatedTaxValue and itemCount.

## Domain invariants
- Unique invoiceIdentifier (domain check and database unique constraint).
- TaxSummary has netAmount and calculatedTax; calculatedTax = netAmount * 0.18 exactly.
- ISSUED or PAID requires itemCount > 0; DRAFT is default.
- Invoice is auditable through the persistence entity.

## Project setup
1. Create PostgreSQL database `facturease`.
2. Execute `db/create_schema.sql` against that database in pgAdmin.
3. Use JDK 26 in IntelliJ Project SDK and Maven Runner.
4. Set local environment variable `DB_PASSWORD` (never commit a real password).
5. Run `./mvnw.cmd clean compile` then `./mvnw.cmd spring-boot:run` in PowerShell.
6. Open `http://localhost:8097/swagger-ui/index.html`.
7. Run the JSON examples with `curl.exe` from the project root.
8. Verify rows with `db/verify_schema.sql`.

## Errors and internationalization
Validation and business-rule errors use HTTP Problem Details; messages support
`Accept-Language: en-US` and `Accept-Language: es-PE`.

## Scope
Only POST /api/v1/invoices is implemented. No CORS, Security, or test suite.
```

### Archivo: `db/verify_schema.sql`

```sql
SELECT table_schema, table_name
FROM information_schema.tables
WHERE table_schema='facturease_db';
SELECT id, invoice_identifier, description, status, net_amount,
       calculated_tax, item_count, created_at, updated_at
FROM facturease_db.invoices ORDER BY id DESC;
```

### Archivo: `docs/examples/invalid-issued.json`

```json
{
  "invoiceIdentifier": "8086f608-cb81-4d27-9ae7-2197901adb1c",
  "description": "No line items",
  "status": "ISSUED",
  "netAmount": 100.00,
  "calculatedTax": 18.00,
  "itemCount": 0
}
```

### Archivo: `docs/examples/invalid-tax.json`

```json
{
  "invoiceIdentifier": "31c8440b-b019-4b07-a93e-3747b507cd22",
  "description": "Incorrect tax example",
  "status": "ISSUED",
  "netAmount": 100.00,
  "calculatedTax": 17.00,
  "itemCount": 3
}
```

### Archivo: `docs/examples/valid-draft.json`

```json
{
  "invoiceIdentifier": "729a8afe-4b29-46f6-ac61-d34e621f379a",
  "description": "Draft without items",
  "netAmount": 100.00,
  "calculatedTax": 18.00,
  "itemCount": 0
}
```

### Archivo: `docs/examples/valid-issued.json`

```json
{
  "invoiceIdentifier": "f17f40be-a9d3-4e3c-9384-885ab050e54a",
  "description": "Corporate services invoice",
  "status": "ISSUED",
  "netAmount": 100.00,
  "calculatedTax": 18.00,
  "itemCount": 3
}
```

### Ejecutar localmente (PowerShell desde la raíz del proyecto)

```powershell
java -version
.\mvnw.cmd -version
# La variable debe existir en la misma terminal donde ejecutas Spring Boot.
$env:DB_PASSWORD="TU_PASSWORD_POSTGRES"
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

Abre `http://localhost:8097/swagger-ui/index.html` y prueba el POST. Si Tomcat no inicia, revisa primero el error de consola. `BUILD SUCCESS` únicamente confirma que Maven compiló, no que el POST funciona.

### Probar los JSON SIN repetir el fallo de curl

**Primero** cambia a la carpeta raíz y verifica existencia:

```powershell
Get-Location
Get-ChildItem .\docs\examples
Test-Path .\docs\examples\valid-issued.json
```

Debe devolver `True`. Si no, en IntelliJ usa Copy Path/Reference → Absolute Path. Luego:

```powershell
$archivo=(Resolve-Path '.\docs\examples\valid-issued.json').Path
curl.exe -i -X POST 'http://localhost:8097/api/v1/invoices' `
  -H 'Content-Type: application/json' `
  -H 'Accept-Language: en-US' `
  --data-binary "@$archivo"
```

Resultados esperados si la app corre y los UUID son nuevos:

| Caso | Resultado |
|---|---|
| `valid-issued.json` (3 ítems, IGV 18%) | **HTTP 201** |
| `invalid-tax.json` (17% en lugar de 18%) | **HTTP 400** |
| `invalid-issued.json` (ISSUED con 0 ítems) | **HTTP 422** |
| `valid-draft.json` (sin status, 0 ítems) | **HTTP 201**, status `DRAFT` |
| Enviar dos veces el mismo UUID | **HTTP 409** en la segunda solicitud |

Para ejecutar un caso inválido, cambia solo el archivo:

```powershell
$archivo=(Resolve-Path '.\docs\examples\invalid-tax.json').Path
curl.exe -i -X POST 'http://localhost:8097/api/v1/invoices' `
  -H 'Content-Type: application/json' `
  -H 'Accept-Language: es-PE' `
  --data-binary "@$archivo"
```

Comprueba en pgAdmin la tabla:

```sql
SELECT id, invoice_identifier, description, status, net_amount,
       calculated_tax, item_count, created_at, updated_at
FROM facturease_db.invoices ORDER BY id DESC;
```

### Comprobación final, commit y push

```powershell
.\mvnw.cmd clean package -DskipTests
git add .
git commit -m "docs: add examples, README and verification"
git push
git log --oneline --reverse
git status
```

**Entrega exigida por el PDF:** un ZIP llamado `pc211990u202422298.zip` con `src/`, `pom.xml`, `mvnw.cmd`, `.mvn/`, recursos y README. No incluyas `target/`, `.idea/` ni credenciales. La URL de GitHub es un flujo adicional de práctica: el enunciado no la pide.

### Cómo pensar la solución sin memorizar todo

```
POST JSON
 -> CreateInvoiceResource (@Valid)
 -> CreateInvoiceCommandFromResourceAssembler
 -> CreateInvoiceCommand
 -> InvoiceCommandServiceImpl (duplicados)
 -> TaxSummary (IGV 18%)
 -> Invoice (ISSUED/PAID requieren itemCount>0)
 -> InvoiceRepository (contrato)
 -> InvoiceRepositoryImpl (adapter)
 -> InvoicePersistenceAssembler
 -> InvoicePersistenceEntity (@Entity + auditoría)
 -> InvoicePersistenceRepository (Spring Data JPA)
 -> facturease_db.invoices
 -> InvoiceResourceFromEntityAssembler
 -> HTTP 201 + InvoiceResource
```

**Enunciado:** Spring Boot 4.1.0, Java 26, DDD separado y POST único. **Decisiones de implementación:** usamos HTTP 400 para IGV incorrecto, HTTP 422 para regla de ítems y HTTP 409 para duplicado; el PDF no asigna códigos específicos a cada excepción, solo exige HTTP apropiado.

**Estado de verificación:** validación estructural y compilación/ejecución aislada de reglas de dominio; la compilación Maven integral y conexión a tu PostgreSQL deben verificarse localmente en IntelliJ con JDK 26.
