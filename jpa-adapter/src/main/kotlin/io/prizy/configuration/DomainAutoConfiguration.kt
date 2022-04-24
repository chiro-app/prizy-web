package io.prizy.configuration

import io.prizy.domain.contest.ports.ContestPublisher
import io.prizy.domain.contest.ports.ContestRepository
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher
import io.prizy.domain.contest.ports.ContestSubscriptionRepository
import io.prizy.domain.contest.ports.ContestTemplateCompiler
import io.prizy.domain.contest.ports.PackRepository
import io.prizy.domain.contest.ports.ReferralRepository
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.contest.service.ContestSubscriptionService
import io.prizy.domain.resources.service.ResourceService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:57 PM
 */

@Configuration
@Import(JpaAdapterConfiguration::class)
class DomainAutoConfiguration {

  @Bean
  fun resourceService(): ResourceService = ResourceService()

  @Bean
  fun contestService(
    packRepository: PackRepository,
    contestPublisher: ContestPublisher,
    contestRepository: ContestRepository,
    templateCompiler: ContestTemplateCompiler
  ): ContestService = ContestService(packRepository, templateCompiler , contestPublisher, contestRepository)

  @Bean
  fun contestSubscriptionService(
    resourceService: ResourceService,
    contestRepository: ContestRepository,
    referralRepository: ReferralRepository,
    subscriptionPublisher: ContestSubscriptionPublisher,
    subscriptionRepository: ContestSubscriptionRepository
  ): ContestSubscriptionService = ContestSubscriptionService(
    resourceService,
    contestRepository,
    referralRepository,
    subscriptionPublisher,
    subscriptionRepository
  )
}
