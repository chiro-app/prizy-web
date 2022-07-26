package io.prizy.domain.notification.port;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 10:15 PM
 */


public interface TemplateCompiler {
  String compileTemplate(String template, Object data);

  String compileRaw(String content, Object data);
}
