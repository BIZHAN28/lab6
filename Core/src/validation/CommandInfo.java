package validation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {
    String name();
    int argsCount() default 0;
    Class<?> requiredObjectType() default Void.class;
}
