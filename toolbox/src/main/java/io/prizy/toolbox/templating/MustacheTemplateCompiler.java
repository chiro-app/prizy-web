package io.prizy.toolbox.templating;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/25/2022 10:26 PM
 */


@UtilityClass
public class MustacheTemplateCompiler {

  private final MustacheFactory mustacheFactory = new DefaultMustacheFactory();

  public String compileTemplate(String template, Object data) throws IOException {
    var sw = new StringWriter();
    mustacheFactory
      .compile(String.format("templates/%s", template))
      .execute(sw, data)
      .flush();
    return sw.toString();
  }

  public String compileRaw(String rawContent, Object data) throws IOException {
    var sw = new StringWriter();
    mustacheFactory
      .compile(new StringReader(rawContent), "cpu.template.email")
      .execute(sw, data)
      .flush();
    return sw.toString();
  }

}