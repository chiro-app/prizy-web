package io.prizy.toolbox.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 11:44 AM
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorCode {
  String value();
}
