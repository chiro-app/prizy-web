package io.prizy.test.integration;

import io.prizy.publicapi.application.Application;
import io.prizy.test.annotation.EnableDatabase;
import io.prizy.test.annotation.EnableGraphQL;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/14/2022 1:09 PM
 */


@EnableGraphQL
@EnableDatabase
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest implements GraphQLAssertions, DatabaseAssertions {

}
