package io.prizy.test.assertion;

import io.prizy.test.extension.ScheduledTasksExtension;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * @author Nidhal Dogga
 * @created 8/28/2022 6:32 PM
 */

public interface ScheduledTasksAssertions {

  @RegisterExtension
  ScheduledTasksExtension SCHEDULED_TASKS_EXTENSION = new ScheduledTasksExtension();

  default <T> void invokeScheduledTask(Class<T> cls, String methodName) {
    SCHEDULED_TASKS_EXTENSION.invokeScheduledTask(cls, methodName);
  }

}
