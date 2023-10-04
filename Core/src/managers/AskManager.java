package managers;

import managers.InputHandler;
import models.Mood;
import models.WeaponType;
import validation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AskManager {
    private InputHandler inputHandler;
    public AskManager(InputHandler inputHandler){
        this.inputHandler = inputHandler;
    }
    public Object ask(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Object param;
            Ask askAnnotation = field.getAnnotation(Ask.class);
            if ((askAnnotation != null) && (field.getType().isPrimitive() || field.getType().isEnum() || field.getType().getSimpleName().equals("String") || field.getType().getSimpleName().equals("Integer"))){
                while (true) {
                    System.out.println("Enter " + field.getName() + " (" + field.getType().getSimpleName() + ")");
                    if (field.getType().isEnum()){
                        Object[] enumConstants = field.getType().getEnumConstants();
                        Set<?> enumSet = new HashSet<>(Arrays.asList(enumConstants));
                        System.out.print("Possible values: ");
                        for (Object value : enumSet) {
                            System.out.print("\""+value + "\" ");
                        }
                        System.out.print("\n");
                    }
                    if (field.getAnnotation(GreaterThanZero.class) != null){
                        System.out.println(field.getAnnotation(GreaterThanZero.class).message());
                    }
                    if (field.getAnnotation(MaxValue.class) != null){
                        System.out.println(field.getAnnotation(MaxValue.class).message() + field.getAnnotation(MaxValue.class).value());
                    }
                    if (field.getAnnotation(NotEmpty.class) != null){
                        System.out.println(field.getAnnotation(NotEmpty.class).message());
                    }


                    String input = inputHandler.handleInput().nextLine();
                    switch (field.getType().getSimpleName()){
                        case ("boolean") -> {
                            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                                param = Boolean.parseBoolean(input);
                                field.setBoolean(obj, (Boolean) param);
                            } else {
                                System.out.println("Wrong input, try again");
                                continue;
                            }
                        }
                        case ("int") -> {
                            try {
                                param = Integer.parseInt(input);
                                field.setInt(obj, (int) param);
                            } catch (Exception e) {
                                System.out.println("Wrong input, try again");
                                continue;
                            }
                        } case ("Integer") -> {
                            try {
                                param = Integer.parseInt(input);
                                field.set(obj, param);
                            } catch (Exception e) {
                                System.out.println("Wrong input, try again");
                                continue;
                            }
                        } case ("double") -> {
                            try {
                                param = Double.parseDouble(input);
                                field.setDouble(obj, (double) param);
                            } catch (Exception e) {
                                System.out.println("Wrong input, try again");
                                continue;
                            }
                        }
                        case ("String") -> {
                            field.set(obj, input);
                        }
                        case ("Mood") -> {

                            try {
                                param = Mood.valueOf(input.toUpperCase());
                                field.set(obj, (Mood) param);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Wrong input, try again");
                                continue;
                            }
                        }
                        case ("WeaponType") -> {

                            try {
                                param = WeaponType.valueOf(input.toUpperCase());
                                field.set(obj, (WeaponType) param);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Wrong input, try again");
                                continue;
                            }
                        }

                    }
                    try {
                        Validator.validate(obj, field);
                        break;
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } else if (askAnnotation != null) {
                try {
                    field.set(obj, ask(field.getType().getDeclaredConstructor().newInstance()));
                } catch (Exception ignored){}
            }
            field.setAccessible(false);
        }
        return obj;
    }
}
