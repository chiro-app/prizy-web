package io.prizy.test.assertion;

import java.util.List;
import java.util.Map;

import io.prizy.test.extension.DatabaseExtension;
import org.assertj.db.api.TableAssert;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * @author Nidhal Dogga
 * @created 8/14/2022 9:35 PM
 */


public interface DatabaseAssertions {

  @RegisterExtension
  DatabaseExtension DATABASE_EXTENSION = new DatabaseExtension();

  default TableAssert assertThatTable(String name) {
    return new TableAssert(new Table(DATABASE_EXTENSION.getDataSource(), name));
  }

  default TableAssert assertThatTable(String name, String sortColumnName) {
    return new TableAssert(new Table(DATABASE_EXTENSION.getDataSource(), name,
      new Table.Order[]{Table.Order.asc(sortColumnName)}));
  }

  default List<Map<String, Object>> queryForList(String sql) {
    return DATABASE_EXTENSION.queryForList(sql);
  }

  default <T> T queryForObject(String sql, Class<T> cls) {
    return DATABASE_EXTENSION.queryForObject(sql, cls);
  }

  default SqlRowSet queryForRowSet(String sql) {
    return DATABASE_EXTENSION.queryForRowSet(sql);
  }

}
