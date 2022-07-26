package io.prizy.adapters.notification.templating;

import java.io.IOException;

import io.prizy.domain.notification.port.TemplateCompiler;
import io.prizy.toolbox.templating.MustacheTemplateCompiler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 5/8/2022 10:16 PM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateCompilerImpl implements TemplateCompiler {

  @Override
  public String compileTemplate(String template, Object data) {
    try {
      return MustacheTemplateCompiler.compileTemplate(template, data);
    } catch (IOException e) {
      log.error("Error while compiling template {}", template, e);
      return null;
    }
  }

  @Override
  public String compileRaw(String content, Object data) {
    try {
      return MustacheTemplateCompiler.compileRaw(content, data);
    } catch (IOException e) {
      log.error("Error while compiling raw content {}", content, e);
      return null;
    }
  }

}
