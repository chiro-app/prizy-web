package io.prizy.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nidhal Dogga
 * @created 8/19/2022 6:12 PM
 */


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface WithMockServer {

  int DEFAULT_PORT = 25099;

  int port() default DEFAULT_PORT;

}
