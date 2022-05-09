package io.prizy.adapters.notification.email;

import java.io.IOException;

import io.prizy.domain.notification.port.EmailTemplateCompiler;
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
public class EmailTemplateCompilerImpl implements EmailTemplateCompiler {

  @Override
  public String compile(String template, Object data) {
    try {
      return MustacheTemplateCompiler.compile(template, data);
    } catch (IOException e) {
      log.error("Error while compiling contest rules template", e);
      return null;
    }
  }

}
