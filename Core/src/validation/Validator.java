package validation;

import java.lang.reflect.Field;

public class Validator {

    public static void validate(Object object, Field field) throws IllegalAccessException {
        // Получаем аннотацию @GreaterThanZero для текущего поля, если она есть
        GreaterThanZero greaterThanZeroAnnotation = field.getAnnotation(GreaterThanZero.class);
        if (greaterThanZeroAnnotation != null) {
            // Устанавливаем флаг доступности поля (важно для доступа к приватным полям)
            field.setAccessible(true);
            // Получаем значение поля и проверяем, что оно типа int и больше 0
            Object value = field.get(object);
            if ((value instanceof Integer) && ((Integer) value) <= 0) {
                // Если поле меньше или равно 0, выбрасываем исключение с заданным сообщением
                throw new IllegalArgumentException(field.getName() + " " + greaterThanZeroAnnotation.message());
            }
        }
        MaxValue MaxValueAnnotation = field.getAnnotation(MaxValue.class);
        if (MaxValueAnnotation != null) {
            // Устанавливаем флаг доступности поля (важно для доступа к приватным полям)
            field.setAccessible(true);
            int maxValue = MaxValueAnnotation.value();
            // Получаем значение поля и проверяем, что оно типа int и больше 0
            Object value = field.get(object);
            if ((value instanceof Integer) && ((Integer) value) > maxValue) {
                String message = MaxValueAnnotation.message() + MaxValueAnnotation.value();
                // Если поле меньше или равно 0, выбрасываем исключение с заданным сообщением
                throw new IllegalArgumentException(field.getName() + " " + message);
            }
        }
        NotEmpty notEmptyAnnotation = field.getAnnotation(NotEmpty.class);
        if (notEmptyAnnotation != null) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (value.toString().trim().equals("")) {
                throw new IllegalArgumentException(field.getName() + " " + notEmptyAnnotation.message());
            }
        }
        NotNull notNullAnnotation = field.getAnnotation(NotNull.class);
        if (notNullAnnotation != null) {
            // Устанавливаем флаг доступности поля, чтобы получить его значение
            field.setAccessible(true);
            // Получаем значение поля у текущего объекта
            Object value = field.get(object);
            // Проверяем, равно ли значение null
            if (value == null) {
                // Если значение null, выбрасываем исключение IllegalArgumentException с сообщением из аннотации
                throw new IllegalArgumentException(field.getName() + " " + notNullAnnotation.message());
            }
        }
    }
    public static void validate (Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            validate(object, field);
        }
    }
}
