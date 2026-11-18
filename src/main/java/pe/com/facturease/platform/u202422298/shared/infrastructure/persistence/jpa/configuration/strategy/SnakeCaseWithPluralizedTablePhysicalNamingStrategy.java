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
