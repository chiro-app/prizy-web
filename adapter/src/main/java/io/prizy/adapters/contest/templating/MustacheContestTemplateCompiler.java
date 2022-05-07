package io.prizy.adapters.contest.templating;

import java.io.IOException;

import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.ports.ContestTemplateCompiler;
import io.prizy.toolbox.templating.MustacheTemplateCompiler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 11:05 AM
 */


@Slf4j
@Component
public class MustacheContestTemplateCompiler implements ContestTemplateCompiler {

  @Override
  public String rulesTemplate(Contest contest) {
    try {
      return MustacheTemplateCompiler.compile("contest-rules.html", contest);
    } catch (IOException e) {
      log.error("Error while compiling contest rules template", e);
      return null;
    }
  }

}
