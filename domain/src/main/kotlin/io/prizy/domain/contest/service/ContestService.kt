package io.prizy.domain.contest.service

import io.prizy.domain.contest.command.CreateContest
import io.prizy.domain.contest.command.CreatePack
import io.prizy.domain.contest.command.UpdateContest
import io.prizy.domain.contest.event.ContestCreated
import io.prizy.domain.contest.event.ContestUpdated
import io.prizy.domain.contest.model.Contest
import io.prizy.domain.contest.model.Pack
import io.prizy.domain.contest.ports.ContestPublisher
import io.prizy.domain.contest.ports.ContestRepository
import io.prizy.domain.contest.ports.ContestTemplateCompiler
import io.prizy.domain.contest.ports.PackRepository
import io.prizy.toolbox.coroutines.runDetached
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:15 AM
 */

class ContestService(
  private val packRepository: PackRepository,
  private val templateCompiler: ContestTemplateCompiler,
  private val contestPublisher: ContestPublisher,
  private val contestRepository: ContestRepository,
) {

  suspend fun contestById(id: UUID): Contest? {
    return contestRepository.byId(id)
  }

  suspend fun contests(): Collection<Contest> {
    return contestRepository.list()
  }

  suspend fun userContests(userId: UUID): Collection<Contest> {
    return contestRepository.list()
  }

  suspend fun listAllContests(): Collection<Contest> {
    return contestRepository.list()
  }

  suspend fun createContest(command: CreateContest): Contest = contestRepository
    .create(command.contest)
    .also { contest -> runDetached { contestPublisher.publish(ContestCreated(contest)) } }

  suspend fun updateContest(command: UpdateContest): Contest = contestRepository
    .update(command.contest)
    .also { contest -> runDetached { contestPublisher.publish(ContestUpdated(contest)) } }

  suspend fun packById(id: UUID): Pack? {
    return packRepository.byId(id)
  }

  suspend fun contestRules(contestId: UUID): String = contestRepository
    .byId(contestId)
    ?.let { contest -> templateCompiler.rulesTemplate(contest) }
    ?: throw IllegalStateException("Contest[$contestId] not found")

  private val CreateContest.contest: Contest
    get() = Contest(
      id = UUID.randomUUID(),
      name = name,
      description = description,
      fromDate = fromDate,
      toDate = toDate,
      adultOnly = adultOnly,
      category = category,
      coverMediaId = coverMediaId,
      mediaIds = mediaIds,
      cost = cost,
      facebookPage = facebookPage,
      instagramPage = instagramPage,
      website = website,
      newsletterSubscription = newsletterSubscription,
      merchant = merchant,
      boardId = boardId,
      packs = packs.map { command -> command.pack }
    )

  private val UpdateContest.contest: Contest
    get() = Contest(
      id = id,
      name = name,
      description = description,
      fromDate = fromDate,
      toDate = toDate,
      adultOnly = adultOnly,
      category = category,
      coverMediaId = coverMediaId,
      mediaIds = mediaIds,
      cost = cost,
      facebookPage = facebookPage,
      instagramPage = instagramPage,
      website = website,
      newsletterSubscription = newsletterSubscription,
      merchant = merchant,
      boardId = boardId,
      packs = packs.map { command -> command.pack }
    )

  private val CreatePack.pack: Pack
    get() = when (this) {
      is CreatePack.Product -> Pack.Product(
        id = UUID.randomUUID(),
        name = name,
        lastWinnerPosition = lastWinnerPosition,
        firstWinnerPosition = firstWinnerPosition,
        value = value,
        mediaId = mediaId,
        quantity = quantity,
      )
      is CreatePack.Coupon -> Pack.Coupon(
        id = UUID.randomUUID(),
        name = name,
        lastWinnerPosition = lastWinnerPosition,
        firstWinnerPosition = firstWinnerPosition,
        code = code,
        expiration = expiration,
      )
    }
}
