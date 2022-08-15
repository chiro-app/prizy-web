package io.prizy;

import io.prizy.publicapi.application.Application;
import io.prizy.test.assertion.DatabaseAssertions;
import io.prizy.test.assertion.GraphQLAssertions;
import io.prizy.test.extension.DatabaseExtension;
import io.prizy.test.extension.GraphQLExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/14/2022 1:09 PM
 */


@ActiveProfiles("test")
@AutoConfigureTestDatabase
@ExtendWith({SpringExtension.class, GraphQLExtension.class, DatabaseExtension.class, MockitoExtension.class})
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest implements GraphQLAssertions, DatabaseAssertions {

}
