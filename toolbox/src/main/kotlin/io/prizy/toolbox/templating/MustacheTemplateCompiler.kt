package io.prizy.toolbox.templating

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.MustacheFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.StringWriter

/**
 *  @author Nidhal Dogga
 *  @created 4/25/2022 10:26 PM
 */

object MustacheTemplateCompiler {

  private val mustacheFactory: MustacheFactory = DefaultMustacheFactory()

  suspend fun compile(template: String, data: Any): String = withContext(Dispatchers.IO) {
    StringWriter()
      .also { sw ->
        mustacheFactory
          .compile("templates/$template")
          .execute(sw, data)
          .flush()
      }
      .toString()
  }
}