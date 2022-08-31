package io.prizy.test.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/28/2022 6:29 PM
 */


public class ScheduledTasksExtension implements BeforeAllCallback {

  private ApplicationContext applicationContext;

  @Override
  public void beforeAll(ExtensionContext context) {
    applicationContext = SpringExtension.getApplicationContext(context);
  }

  public <T> void invokeScheduledTask(Class<T> cls, String methodName) {
    var holder = applicationContext.getBean(ScheduledTaskHolder.class);
    var cronTask = holder.getScheduledTasks()
      .stream()
      .map(ScheduledTask::getTask)
      .filter(task -> task instanceof CronTask && task.getRunnable() instanceof ScheduledMethodRunnable)
      .filter(task -> {
        var runnable = ((ScheduledMethodRunnable) task.getRunnable());
        return runnable.getTarget().getClass().equals(cls) && runnable.getMethod().getName().equals(methodName);
      })
      .findAny()
      .orElseThrow((() -> new IllegalArgumentException("Cannot find scheduled task " + cls.getCanonicalName() + "." + methodName)));

    cronTask.getRunnable().run();
  }
}
