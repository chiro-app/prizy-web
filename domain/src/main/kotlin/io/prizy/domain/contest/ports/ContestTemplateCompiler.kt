package io.prizy.domain.contest.ports

import io.prizy.domain.contest.model.Contest

/**
 *  @author Nidhal Dogga
 *  @created 4/25/2022 10:00 PM
 */

interface ContestTemplateCompiler {
  suspend fun rulesTemplate(contest: Contest): String
}