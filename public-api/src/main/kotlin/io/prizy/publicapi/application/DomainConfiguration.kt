package io.prizy.publicapi.application

import io.prizy.configuration.JpaAdapterConfiguration
import io.prizy.domain.contest.ports.ContestPublisher
import io.prizy.domain.contest.ports.ContestRepository
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher
import io.prizy.domain.contest.ports.ContestSubscriptionRepository
import io.prizy.domain.contest.ports.ContestTemplateCompiler
import io.prizy.domain.contest.ports.PackRepository
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.contest.service.ContestSubscriptionService
import io.prizy.domain.referral.ports.ReferralPublisher
import io.prizy.domain.referral.ports.ReferralRepository
import io.prizy.domain.referral.service.ReferralService
import io.prizy.domain.resources.ports.ResourceRepository
import io.prizy.domain.resources.service.ResourceService
import io.prizy.domain.user.port.PasswordHasher
import io.prizy.domain.user.port.UserRepository
import io.prizy.domain.user.service.UserService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:57 PM
 */

@EnableAsync
@Configuration
@Import(JpaAdapterConfiguration::class)
@EnableConfigurationProperties(DomainConfiguration.ResourceProperties::class)
class DomainConfiguration {

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun userService(userRepository: UserRepository, passwordHasher: PasswordHasher): UserService =
    UserService(userRepository, passwordHasher)

  @Bean
  fun referralService(
    referralRepository: ReferralRepository,
    referralPublisher: ReferralPublisher
  ): ReferralService {
    return ReferralService(referralRepository, referralPublisher)
  }

  @Bean
  fun resourceService(
    contestRepository: ContestRepository,
    transactionRepository: ResourceRepository,
    resourceProperties: ResourceProperties
  ): ResourceService {
    return ResourceService(contestRepository, transactionRepository, resourceProperties.toDomain)
  }

  @Bean
  fun contestService(
    packRepository: PackRepository,
    contestPublisher: ContestPublisher,
    contestRepository: ContestRepository,
    templateCompiler: ContestTemplateCompiler
  ): ContestService {
    return ContestService(contestRepository, contestPublisher, packRepository, templateCompiler)
  }

  @Bean
  fun contestSubscriptionService(
    resourceService: ResourceService,
    contestRepository: ContestRepository,
    referralRepository: ReferralRepository,
    subscriptionPublisher: ContestSubscriptionPublisher,
    subscriptionRepository: ContestSubscriptionRepository
  ): ContestSubscriptionService {
    return ContestSubscriptionService(
      resourceService,
      contestRepository,
      referralRepository,
      subscriptionPublisher,
      subscriptionRepository
    )
  }

  @ConstructorBinding
  @ConfigurationProperties("prizy.resources")
  data class ResourceProperties(
    val referrerKeyBonus: Int,
    val referralKeyBonus: Int
  ) {

    val toDomain: io.prizy.domain.resources.properties.ResourceProperties
      get() = io.prizy.domain.resources.properties.ResourceProperties(
        referrerKeyBonus,
        referralKeyBonus
      )
  }
}
