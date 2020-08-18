package com.spring.core.lang;
import javax.annotation.Nonnull;
import javax.annotation.meta.When;
import java.lang.annotation.*;


@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nonnull(when = When.MAYBE)
public @interface NonNull {
}
