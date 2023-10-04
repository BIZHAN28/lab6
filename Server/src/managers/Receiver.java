package managers;

import commands.Save;
import commands.ScriptStack;
import commands.network.Response;
import exceptions.NoneValueArgumentException;
import exceptions.RecursiveException;
import jakarta.xml.bind.JAXBException;
import models.Car;
import models.HumanBeing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Receiver {
    private final CollectionManager collectionManager;
    private final ScriptStack scriptStack;

    public Receiver(CollectionManager collectionManager, ScriptStack scriptStack) {
        this.collectionManager = collectionManager;
        this.scriptStack = scriptStack;
    }

    public Response help(String[] args){
        String message = "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "filter_by_car car : вывести элементы, значение поля car которых равно заданному\n" +
                "print_unique_car : вывести уникальные значения поля car всех элементов в коллекции\n" +
                "print_field_descending_mood : вывести значения поля mood всех элементов в порядке убывания";
        return new Response(message);
    }

    public Response info(String[] args){
        save(null);
        LocalDateTime initTime = collectionManager.getCreationDate();
        String message = "Collection info:" +
                "\nType: " + collectionManager.getHumanBeings().getClass().getName() +
                "\nSize: " + collectionManager.getHumanBeings().size() +
                "\nTime: " + initTime;
        return new Response(message);
    }
    public Response show(String[] args){
        if (collectionManager.getHumanBeings().size() == 0){
            return new Response("Collection is empty");
        }
        StringBuilder message = new StringBuilder();
        collectionManager.getSortedByLocationHumanBeings().forEach((value) -> message.append(value.toString()).append("\n"));
        return new Response(message.toString());
    }

    public Response add(String[] args, Object obj){
        HumanBeing humanBeing = (HumanBeing) obj;
        humanBeing.setCreationDate(java.time.LocalDate.now());
        humanBeing.setId(CollectionManager.generateId());
        collectionManager.add((HumanBeing) obj);
        save(null);
        return new Response("Element was added");
    }
    public Response update(String[] args, Object obj){
        try {
            if (args.length == 0) {
                throw new NoneValueArgumentException();
            }
            int id = Integer.parseInt(args[0]);
            HumanBeing humanBeing = (HumanBeing) obj;
            humanBeing.setCreationDate(java.time.LocalDate.now());
            collectionManager.update((HumanBeing) obj,id);
            return new Response("Element was updated");
        } catch (NoneValueArgumentException e){
            return new Response(e.getMessage());
        } catch (NumberFormatException e) {
            return new Response("Argument should be int");
        }
    }
    public Response removeById(String[] args){
        try {
            if (args.length == 0) {
                throw new NoneValueArgumentException();
            }
            int id = Integer.parseInt(args[0]);
            collectionManager.removeById(id);
            return new Response("Element was removed");
        } catch (NoneValueArgumentException e){
            return new Response(e.getMessage());
        } catch (NumberFormatException e){
            return new Response("Argument should be int");
        }
    }
    public Response clear(String[] args){
        collectionManager.clear();
        return new Response("Collection was cleared");
    }
    public Response save(String[] args) {
        try {
            collectionManager.saveToXML();
            return new Response("Collection was saved");
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Response("Collection was saved");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Response executeScript(String[] args) throws IOException {
        try {
            if (args.length == 0) {
                throw new NoneValueArgumentException();
            }
            File file = new File(args[0]);
            if (scriptStack.contains(args[0])) {
                throw new RecursiveException();
            }
            FileInput fileInput = new FileInput(file);
            Invoker invoker = new Invoker(collectionManager, scriptStack);
            scriptStack.push(args[0]);
            StringBuilder message = new StringBuilder();
            while (fileInput.handleInput().hasNextLine()) {
                String s = fileInput.handleInput().nextLine();
                String[] spl = s.split(" ", 2);
                message.append(invoker.execute(spl[0], Arrays.copyOfRange(spl, 1, spl.length)).getMessage()).append("\n");
            }
            scriptStack.pop();
            message.append("Script " + args[0] + " was executed");
            return new Response(message.toString());
        } catch (RecursiveException | NoneValueArgumentException | FileNotFoundException e) {
            return new Response(e.getMessage());
        }

    }

    public Response addIfMin(String[] args, Object obj) {
        int lastSize = collectionManager.getHumanBeings().size();

        try {
            HumanBeing humanBeing = (HumanBeing) obj;
            humanBeing.setCreationDate(java.time.LocalDate.now());
            humanBeing.setId(CollectionManager.generateId());

            boolean added = collectionManager.getHumanBeings()
                    .stream()
                    .noneMatch(existing -> existing.compareTo(humanBeing) <= 0);

            if (added) {
                collectionManager.add(humanBeing);
                return new Response("Element was added");
            }
        } catch (Exception ignored) {
        }

        return new Response("Element wasn't added");
    }


    public Response removeGreater(String[] args, Object obj) {
        int lastSize = collectionManager.getHumanBeings().size();

        try {
            HumanBeing humanBeing = (HumanBeing) obj;

            collectionManager.getHumanBeings()
                    .removeIf(existing -> existing.compareTo(humanBeing) > 0);
        } catch (Exception ignored) {
        }

        if (lastSize != collectionManager.getHumanBeings().size()) {
            return new Response("Elements were removed");
        } else {
            return new Response("No elements were removed");
        }
    }

    public Response removeLower(String[] args, Object obj) {
        int lastSize = collectionManager.getHumanBeings().size();

        try {
            HumanBeing humanBeing = (HumanBeing) obj;

            collectionManager.getHumanBeings()
                    .removeIf(existing -> existing.compareTo(humanBeing) < 0);
        } catch (Exception ignored) {
        }

        if (lastSize != collectionManager.getHumanBeings().size()) {
            return new Response("Elements were removed");
        } else {
            return new Response("No elements were removed");
        }
    }

    public Response filterByCar(String[] args, Object obj) {
        List<HumanBeing> filteredList = collectionManager.getSortedByLocationHumanBeings()
                .stream()
                .filter(humanBeing -> humanBeing.getCar() != null && humanBeing.getCar().equals((Car) obj))
                .toList();

        if (filteredList.isEmpty()) {
            return new Response("No matching elements found");
        } else {
            StringBuilder message = new StringBuilder();
            filteredList.forEach(humanBeing -> message.append(humanBeing.toString()).append("\n"));
            System.out.println(message);
            return new Response(message.toString());
        }
    }

    public Response printUniqueCar(String[] args){
        return new Response(collectionManager.printUniqueCar());
    }
    public Response printFieldDescendingMood(String[] args){
        return new Response(collectionManager.printFieldDescendingMood());
    }

}
