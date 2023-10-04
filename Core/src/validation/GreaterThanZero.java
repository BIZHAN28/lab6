package validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // Аннотация применяется только к полям
@Retention(RetentionPolicy.RUNTIME) // Аннотация доступна во время выполнения
public @interface GreaterThanZero {
    String message() default "Value must be greater than zero"; // Сообщение об ошибке по умолчанию
}