import commands.network.CommandDescription;
import commands.network.Request;
import commands.network.Response;
import exceptions.RecursiveException;
import managers.AskManager;
import managers.ConsoleInput;
import managers.SerializeManager;
import models.HumanBeing;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.*;

public class App {
    public static void main(String[] args) {
        while (true) {
            try {
                ScriptStack scriptStack = new ScriptStack();
                InetAddress host = InetAddress.getLocalHost();
                int port = 6789;
                byte[] data = new byte[2684780];
                Socket sock = new Socket(host, port);
                OutputStream os = sock.getOutputStream();
                InputStream is = sock.getInputStream();

                SerializeManager serializeManager = new SerializeManager();

                os.write(serializeManager.serialize(new Request("connect", null, null)));
                is.read(data);
                if (Objects.equals(((Response) serializeManager.deserialize(data)).getMessage(), "The server is currently busy. Come back later.")) {
                    System.out.println("The server is currently busy. Come back later.");
                    System.exit(0);
                }
                os.write(serializeManager.serialize(new Request("next", null, null)));
                ArrayList<CommandDescription> commandDescriptions = (ArrayList<CommandDescription>) ((Response) serializeManager.deserialize(data)).getObject();
                HashMap<String, CommandDescription> commands = new HashMap<>();
                for (CommandDescription commandDescription : commandDescriptions) {
                    commands.put(commandDescription.getName().toLowerCase(), commandDescription);
                }
                is.read(data);
                System.out.println("\n" +
                        "██╗      █████╗ ██████╗  ██████╗ \n" +
                        "██║     ██╔══██╗██╔══██╗██╔════╝ \n" +
                        "██║     ███████║██████╔╝███████╗ \n" +
                        "██║     ██╔══██║██╔══██╗██╔═══██╗\n" +
                        "███████╗██║  ██║██████╔╝╚██████╔╝\n" +
                        "╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝ \n" +
                        "                                 \n");
                System.out.println("Welcome to the program");
                System.out.println("Type \"help\" for a list of commands");

                ConsoleInput consoleInput = new ConsoleInput();
                AskManager askManager = new AskManager(consoleInput);
                String message = "#end";

                while (true) {
                    if (Objects.equals(message, "#end")) {
                        String s = consoleInput.handleInput().nextLine().toLowerCase().trim();
                        String[] spl = s.split(" ", 2);
                        if (commands.containsKey(spl[0])) {
                            os.flush();
                            Object obj = null;
                            if (commands.get(spl[0]).getRequiredObjectType() != Void.class) {
                                obj = askManager.ask(commands.get(spl[0]).getRequiredObjectType().getConstructor().newInstance());
                            }

                            os.write(serializeManager.serialize(new Request(spl[0], Arrays.copyOfRange(spl, 1, spl.length), obj)));

                            data = new byte[2684787];
                            is.read(data);
                            message = ((Response) serializeManager.deserialize(data)).getMessage();
                            if (!Objects.equals(message, "#end")) {
                                System.out.println(message);
                            }


                        } else if (spl[0].equalsIgnoreCase("exit")) {
                            sock.close();
                            System.exit(0);
                        } else if (spl[0].equalsIgnoreCase("execute_script")) {
                            try {
                                List<Request> requests = ScriptExecutor.execute(spl[1], commands, scriptStack);
                                for (Request request : requests) {
                                    os.flush();
                                    os.write(serializeManager.serialize(request));

                                    data = new byte[2684787];
                                    is.read(data);
                                    message = ((Response) serializeManager.deserialize(data)).getMessage();
                                    if (!Objects.equals(message, "#end")) {
                                        System.out.println(message);
                                    }
                                }
                            } catch (RecursiveException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("This command is not recognized, try again");
                        }
                    } else {

                        os.write(serializeManager.serialize(new Request("next", null, null)));

                        data = new byte[2684787];
                        is.read(data);
                        message = ((Response) serializeManager.deserialize(data)).getMessage();
                        if (!Objects.equals(message, "#end")) {
                            System.out.println(message);
                        }
                    }
                }

            } catch (ConnectException e) {
                System.out.println("Server is unavailable. Please wait.");
            } catch (NoSuchElementException e){
                
                System.exit(0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //e.printStackTrace();
            }
        }
    }
}