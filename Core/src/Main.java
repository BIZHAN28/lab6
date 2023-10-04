//import managers.CollectionManager;
//import managers.ConsoleInput;
//import managers.Invoker;
//import commands.ScriptStack;
//import models.HumanBeing;
//import validation.Validator;
//
//import javax.xml.stream.XMLStreamException;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.NoSuchElementException;
//
//public class Main {
//    public static void main(String[] args) throws XMLStreamException, IOException, IllegalAccessException {
//        System.out.println(".__        ___.      .________\n|  | _____ \\_ |__    |   ____/\n|  | \\__  \\ | __ \\   |____  \\ \n|  |__/ __ \\| \\_\\ \\  /       \\\n|____(____  /___  / /______  /\n          \\/    \\/         \\/ ");
//        System.out.println("Welcome to the program");
//        System.out.println("Type \"help\" for a list of commands");
//
//        CollectionManager collectionManager = new CollectionManager();
//        collectionManager.loadFromXML();
//        try {
//            for (HumanBeing humanBeing : collectionManager.getHumanBeings()) {
//                Validator.validate(humanBeing);
//            }
//        } catch (IllegalArgumentException e){
//            System.out.println("File is not valid");
//            collectionManager.clear();
//        }
//        ConsoleInput consoleInput = new ConsoleInput();
//        ScriptStack  scriptStack = new ScriptStack();
//        Invoker invoker = new Invoker(collectionManager,  scriptStack);
//
//        while(true) {
//            try {
//                String s = consoleInput.handleInput().nextLine().toLowerCase().trim();
//                String[] spl = s.split(" ", 2);
//                invoker.execute(spl[0], Arrays.copyOfRange(spl, 1, spl.length));
//            } catch (NullPointerException var6) {
//                System.out.println("This command is not recognized, try again");
//            } catch (NoSuchElementException ignored) {
//                System.exit(0);
//            }
//        }
//    }
//}
