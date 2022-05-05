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
import io.prizy.domain.game.port.GameBoardRepository
import io.prizy.domain.game.service.GameBoardService
import io.prizy.domain.notification.publisher.NotificationPublisher
import io.prizy.domain.ranking.port.RankingRepository
import io.prizy.domain.ranking.service.RankingService
import io.prizy.domain.referral.ports.ReferralPublisher
import io.prizy.domain.referral.ports.ReferralRepository
import io.prizy.domain.referral.service.ReferralService
import io.prizy.domain.resources.ports.ResourcePublisher
import io.prizy.domain.resources.ports.ResourceRepository
import io.prizy.domain.resources.service.ResourceBonusService
import io.prizy.domain.resources.service.ResourceBoostService
import io.prizy.domain.resources.service.ResourceService
import io.prizy.domain.reward.port.RewardPublisher
import io.prizy.domain.reward.port.RewardRepository
import io.prizy.domain.reward.service.RewardService
import io.prizy.domain.user.port.ConfirmationCodeRepository
import io.prizy.domain.user.port.PasswordHasher
import io.prizy.domain.user.port.UserPreferencesRepository
import io.prizy.domain.user.port.UserPublisher
import io.prizy.domain.user.port.UserRepository
import io.prizy.domain.user.service.EmailConfirmationService
import io.prizy.domain.user.service.UserPreferencesService
import io.prizy.domain.user.service.UserService
import io.prizy.publicapi.application.properties.ResourceProperties
import io.prizy.publicapi.application.properties.StorageProperties
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
class DomainConfiguration {

  @Bean
  fun gameBoardService(repository: GameBoardRepository) = GameBoardService(repository)

  @Bean
  fun rewardService(
    publisher: RewardPublisher,
    repository: RewardRepository,
    contestRepository: ContestRepository,
    rankingRepository: RankingRepository
  ) = RewardService(publisher, repository, contestRepository, rankingRepository)

  @Bean
  fun userPreferencesService(repository: UserPreferencesRepository) = UserPreferencesService(repository)

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
  fun rankingService(rankingRepository: RankingRepository, contestRepository: ContestRepository) =
    RankingService(rankingRepository, contestRepository)

  @Bean
  fun emaiConfirmationService(
    userRepository: UserRepository,
    notificationPublisher: NotificationPublisher,
    confirmationCodeRepository: ConfirmationCodeRepository
  ) = EmailConfirmationService(confirmationCodeRepository, userRepository, notificationPublisher)

  @Bean
  fun passwordResetService(
    userRepository: UserRepository,
    passwordHasher: PasswordHasher,
    resetTokenRepository: ResetTokenRepository,
    resetCodeRepository: ResetCodeRepository,
    notificationPublisher: NotificationPublisher
  ) = PasswordResetService(
    userRepository, resetCodeRepository, resetTokenRepository, passwordHasher, notificationPublisher
  )

  @Bean
  fun refreshTokenService(refreshTokenRepository: RefreshTokenRepository) = RefreshTokenService(refreshTokenRepository)

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun userService(
    userRepository: UserRepository, passwordHasher: PasswordHasher, userPublisher: UserPublisher
  ) = UserService(userRepository, passwordHasher, userPublisher)

  @Bean
  fun referralService(
    referralRepository: ReferralRepository, referralPublisher: ReferralPublisher
  ) = ReferralService(referralRepository, referralPublisher)

  @Bean
  fun resourceService(
    contestRepository: ContestRepository,
    resourceRepository: ResourceRepository,
  ) = ResourceService(resourceRepository, contestRepository)

  @Bean
  fun resourceBonusService(
    boostService: ResourceBoostService,
    resourcePublisher: ResourcePublisher,
    resourceRepository: ResourceRepository,
    contestSubscriptionRepository: ContestSubscriptionRepository,
    resourceProperties: ResourceProperties
  ) = ResourceBonusService(
    resourceRepository,
    contestSubscriptionRepository,
    resourcePublisher,
    boostService,
    resourceProperties.toDomain
  )

  @Bean
  fun resourceBoostService(
    referralRepository: ReferralRepository,
    contestRepository: ContestRepository,
    resourceProperties: ResourceProperties
  ) = ResourceBoostService(referralRepository, contestRepository, resourceProperties.toDomain)

  @Bean
  fun contestService(
    packRepository: PackRepository,
    contestPublisher: ContestPublisher,
    contestRepository: ContestRepository,
    templateCompiler: ContestTemplateCompiler
  ) = ContestService(contestRepository, contestPublisher, packRepository, templateCompiler)

  @Bean
  fun contestSubscriptionService(
    contestRepository: ContestRepository,
    referralRepository: ReferralRepository,
    resourceRepository: ResourceRepository,
    subscriptionPublisher: ContestSubscriptionPublisher,
    subscriptionRepository: ContestSubscriptionRepository
  ) = ContestSubscriptionService(
    resourceRepository, contestRepository, referralRepository, subscriptionPublisher, subscriptionRepository
  )
}
