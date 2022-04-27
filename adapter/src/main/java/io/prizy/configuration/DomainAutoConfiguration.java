package io.prizy.configuration;

import io.prizy.domain.contest.ports.ContestPublisher;
import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.ports.ContestSubscriptionPublisher;
import io.prizy.domain.contest.ports.ContestSubscriptionRepository;
import io.prizy.domain.contest.ports.ContestTemplateCompiler;
import io.prizy.domain.contest.ports.PackRepository;
import io.prizy.domain.referral.ports.ReferralRepository;
import io.prizy.domain.contest.service.ContestService;
import io.prizy.domain.contest.service.ContestSubscriptionService;
import io.prizy.domain.referral.ports.ReferralPublisher;
import io.prizy.domain.referral.service.ReferralService;
import io.prizy.domain.resources.ports.ResourceRepository;
import io.prizy.domain.resources.service.ResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:57 PM
 */

@EnableAsync
@Configuration
@Import(JpaAdapterConfiguration.class)
public class DomainAutoConfiguration {

  @Bean
  ReferralService referralService(
    ReferralRepository referralRepository,
    ReferralPublisher referralPublisher
  ) {
    return new ReferralService(referralRepository, referralPublisher);
  }

  @Bean
  ResourceService resourceService(ContestRepository contestRepository, ResourceRepository transactionRepository) {
    return new ResourceService(contestRepository, transactionRepository);
  }

  @Bean
  ContestService contestService(
    PackRepository packRepository,
    ContestPublisher contestPublisher,
    ContestRepository contestRepository,
    ContestTemplateCompiler templateCompiler
  ) {
    return new ContestService(contestRepository, contestPublisher, packRepository, templateCompiler);
  }

  @Bean
  ContestSubscriptionService contestSubscriptionService(
    ResourceService resourceService,
    ContestRepository contestRepository,
    ReferralRepository referralRepository,
    ContestSubscriptionPublisher subscriptionPublisher,
    ContestSubscriptionRepository subscriptionRepository
  ) {
    return new ContestSubscriptionService(
      resourceService,
      contestRepository,
      referralRepository,
      subscriptionPublisher,
      subscriptionRepository
    );
  }
}
