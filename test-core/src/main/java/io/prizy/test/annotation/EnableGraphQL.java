package io.prizy.test.annotation;

import io.prizy.test.extension.GraphQLExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 10:47 PM
 */


@Inherited
@Target({ElementType.TYPE})
@ExtendWith(GraphQLExtension.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableGraphQL {
}
