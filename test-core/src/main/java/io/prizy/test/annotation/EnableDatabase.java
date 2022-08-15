package io.prizy.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.prizy.test.extension.DatabaseExtension;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 10:47 PM
 */


@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(DatabaseExtension.class)
public @interface EnableDatabase {
}
