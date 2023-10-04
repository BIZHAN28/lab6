package validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // Аннотация применяется только к полям
@Retention(RetentionPolicy.RUNTIME) // Аннотация доступна во время выполнения
public @interface MaxValue {
    int value(); // Максимальное значение
    String message() default "Argument value should be less than ";// Сообщение об ошибке по умолчанию
}
