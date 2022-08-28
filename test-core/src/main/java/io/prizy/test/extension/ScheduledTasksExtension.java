package io.prizy.test.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/28/2022 6:29 PM
 */


public class ScheduledTasksExtension implements BeforeAllCallback, AfterAllCallback {

  private ScheduledTaskHolder holder;


  @Override
  public void afterAll(ExtensionContext context) {

  }

  @Override
  public void beforeAll(ExtensionContext context) {
    holder = SpringExtension.getApplicationContext(context).getBean(ScheduledTaskHolder.class);
  }

  public void invokeScheduledTasks() {
    holder.getScheduledTasks()
      .stream()
      .map(ScheduledTask::getTask)
      .forEach(task -> task.getRunnable().run());
  }

  public <T> void invokeScheduledTask(Class<T> cls, String methodName) {
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
