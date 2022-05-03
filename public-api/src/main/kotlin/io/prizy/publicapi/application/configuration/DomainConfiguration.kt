package io.prizy.publicapi.application.configuration

import io.prizy.adapters.asset.MinioClientProperties
import io.prizy.domain.asset.port.AssetRepository
import io.prizy.domain.asset.port.StorageBackend
import io.prizy.domain.asset.service.AssetService
import io.prizy.domain.auth.port.RefreshTokenRepository
import io.prizy.domain.auth.port.ResetCodeRepository
import io.prizy.domain.auth.port.ResetTokenRepository
import io.prizy.domain.auth.service.PasswordResetService
import io.prizy.domain.auth.service.RefreshTokenService
import io.prizy.domain.contest.ports.ContestPublisher
import io.prizy.domain.contest.ports.ContestRepository
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher
import io.prizy.domain.contest.ports.ContestSubscriptionRepository
import io.prizy.domain.contest.ports.ContestTemplateCompiler
import io.prizy.domain.contest.ports.PackRepository
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.contest.service.ContestSubscriptionService
import io.prizy.domain.notification.publisher.NotificationPublisher
import io.prizy.domain.ranking.port.RankingRowRepository
import io.prizy.domain.ranking.service.RankingService
import io.prizy.domain.referral.ports.ReferralPublisher
import io.prizy.domain.referral.ports.ReferralRepository
import io.prizy.domain.referral.service.ReferralService
import io.prizy.domain.resources.ports.ResourceRepository
import io.prizy.domain.resources.service.ResourceService
import io.prizy.domain.user.port.ConfirmationCodeRepository
import io.prizy.domain.user.port.PasswordHasher
import io.prizy.domain.user.port.UserPublisher
import io.prizy.domain.user.port.UserRepository
import io.prizy.domain.user.service.EmailConfirmationService
import io.prizy.domain.user.service.UserService
import io.prizy.publicapi.application.properties.StorageProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:57 PM
 */

@EnableAsync
@Configuration
@EnableConfigurationProperties(DomainConfiguration.ResourceProperties::class)
class DomainConfiguration {

  @Bean
  fun minioClientProperties(storageProperties: StorageProperties) = MinioClientProperties(
    storageProperties.endpoint,
    storageProperties.accessKey,
    storageProperties.secretKey,
    storageProperties.bucketName,
    storageProperties.region
  )

  @Bean
  fun assetService(storageBackend: StorageBackend, assetRepository: AssetRepository) = AssetService(
    storageBackend,
    assetRepository
  )

  @Bean
  fun rankingService(rankingRowRepository: RankingRowRepository, contestRepository: ContestRepository): RankingService =
    RankingService(rankingRowRepository, contestRepository)

  @Bean
  fun emaiConfirmationService(
    userRepository: UserRepository,
    notificationPublisher: NotificationPublisher,
    confirmationCodeRepository: ConfirmationCodeRepository
  ): EmailConfirmationService =
    EmailConfirmationService(confirmationCodeRepository, userRepository, notificationPublisher)

  @Bean
  fun passwordResetService(
    userRepository: UserRepository,
    passwordHasher: PasswordHasher,
    resetTokenRepository: ResetTokenRepository,
    resetCodeRepository: ResetCodeRepository,
    notificationPublisher: NotificationPublisher
  ): PasswordResetService = PasswordResetService(
    userRepository, resetCodeRepository, resetTokenRepository, passwordHasher, notificationPublisher
  )

  @Bean
  fun refreshTokenService(refreshTokenRepository: RefreshTokenRepository): RefreshTokenService =
    RefreshTokenService(refreshTokenRepository)

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun userService(
    userRepository: UserRepository, passwordHasher: PasswordHasher, userPublisher: UserPublisher
  ): UserService = UserService(userRepository, passwordHasher, userPublisher)

  @Bean
  fun referralService(
    referralRepository: ReferralRepository, referralPublisher: ReferralPublisher
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
      resourceService, contestRepository, referralRepository, subscriptionPublisher, subscriptionRepository
    )
  }

  @ConstructorBinding
  @ConfigurationProperties("prizy.resources")
  data class ResourceProperties(
    val referrerKeyBonus: Int, val referralKeyBonus: Int
  ) {

    val toDomain: io.prizy.domain.resources.properties.ResourceProperties
      get() = io.prizy.domain.resources.properties.ResourceProperties(
        referrerKeyBonus, referralKeyBonus
      )
  }
}
