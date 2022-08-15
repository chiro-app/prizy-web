package io.prizy.test.integration;

import io.prizy.test.extension.DatabaseExtension;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * @author Nidhal Dogga
 * @created 8/14/2022 9:35 PM
 */


public interface DatabaseAssertions {

  @RegisterExtension
  DatabaseExtension DATABASE_EXTENSION = new DatabaseExtension();

  default Table assertThatTable(String name) {
    return new Table(DATABASE_EXTENSION.getDataSource(), name);
  }

}
