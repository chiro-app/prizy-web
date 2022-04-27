package io.prizy.publicapi.application

import io.prizy.domain.contest.ports.ContestTemplateCompiler
import io.prizy.toolbox.templating.MustacheTemplateCompiler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *  @author Nidhal Dogga
 *  @created 4/25/2022 10:48 PM
 */

@Configuration
class TemplatingConfiguration {

  @Bean
  fun contestTemplateCompiler(): ContestTemplateCompiler {
    return ContestTemplateCompiler { contest -> MustacheTemplateCompiler.compile("contest-rules.html", contest) }
  }
}