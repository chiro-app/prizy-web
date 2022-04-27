package io.prizy.toolbox.templating;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.StringWriter;

/**
 *  @author Nidhal Dogga
 *  @created 4/25/2022 10:26 PM
 */


@UtilityClass
public class MustacheTemplateCompiler {

  private final MustacheFactory mustacheFactory = new DefaultMustacheFactory();

  public String compile(String template, Object data) throws IOException {
    var sw = new StringWriter();
    mustacheFactory
      .compile(String.format("templates/%s", template))
      .execute(sw, data)
      .flush();
    return sw.toString();
  }
}