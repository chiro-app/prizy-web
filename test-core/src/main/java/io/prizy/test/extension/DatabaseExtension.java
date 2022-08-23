package io.prizy.test.extension;

import javax.sql.DataSource;

import lombok.Getter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 10:34 PM
 */


@Getter
public class DatabaseExtension implements TestInstancePostProcessor, BeforeAllCallback, BeforeEachCallback,
  AfterEachCallback {

  private DataSource dataSource;

  @Override
  public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
    dataSource = SpringExtension.getApplicationContext(context).getBean(DataSource.class);
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    dataSource = SpringExtension.getApplicationContext(context).getBean(DataSource.class);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    // Clean up tables after each test
    truncateTables(context);
  }

  @Override
  public void beforeAll(ExtensionContext context) {
    // Clean up tables before all tests
    truncateTables(context);
  }

  private void truncateTables(ExtensionContext context) {
    // TODO(Nidhal): Make this driver agnostic
    var jdbcTemplate = SpringExtension.getApplicationContext(context).getBean(JdbcTemplate.class);
    var tableNames = jdbcTemplate
      .queryForList("""
        select tablename as table_name
        from pg_tables
        where schemaname = 'public'
          and tablename != 'schema_version'
          and tablename != 'flyway_schema_history';
        """)
      .stream()
      .map(map -> (String) map.get("table_name"))
      .toList();
    var truncateQueries = tableNames
      .stream()
      .map(tableName -> String.format("truncate table %s restart identity cascade;", tableName))
      .toList();
    jdbcTemplate.execute(String.format("""
      set session_replication_role = replica;
      %s
      set session_replication_role = default;
      """, String.join("\n", truncateQueries)));
  }
}
