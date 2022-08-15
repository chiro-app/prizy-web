package io.prizy.test.extension;

import javax.sql.DataSource;

import lombok.Getter;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 10:34 PM
 */


@Getter
public class DatabaseExtension implements TestInstancePostProcessor, BeforeEachCallback {

  private DataSource dataSource;

  @Override
  public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
    this.dataSource = SpringExtension.getApplicationContext(context).getBean(DataSource.class);
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    this.dataSource = SpringExtension.getApplicationContext(context).getBean(DataSource.class);
  }

}
