package io.prizy.test.extension;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import lombok.Getter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 10:34 PM
 */


@Getter
public class DatabaseExtension implements BeforeAllCallback, AfterEachCallback {

  private DataSource dataSource;
  private JdbcTemplate jdbcTemplate;

  @Override
  public void afterEach(ExtensionContext context) {
    // Clean up tables after each test
    truncateTables();
  }

  @Override
  public void beforeAll(ExtensionContext context) {
    // Clean up tables before all tests
    dataSource = SpringExtension.getApplicationContext(context).getBean(DataSource.class);
    jdbcTemplate = SpringExtension.getApplicationContext(context).getBean(JdbcTemplate.class);
    truncateTables();
  }

  public List<Map<String, Object>> queryForList(String sql) {
    return jdbcTemplate.queryForList(sql);
  }

  public <T> T queryForObject(String sql, Class<T> cls) {
    return jdbcTemplate.queryForObject(sql, cls);
  }

  public SqlRowSet queryForRowSet(String sql) {
    return jdbcTemplate.queryForRowSet(sql);
  }

  private void truncateTables() {
    // TODO(Nidhal): Make this driver agnostic
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
