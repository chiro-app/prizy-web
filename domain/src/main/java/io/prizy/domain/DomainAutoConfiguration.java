package io.prizy.domain;

import java.io.IOException;

import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 10:57 AM
 */


@Configuration
@ComponentScan("io.prizy.domain")
public class DomainAutoConfiguration {

  @Bean
  UserAgentParser userAgentParser() {
    try {
      return new UserAgentService().loadParser();
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

}
