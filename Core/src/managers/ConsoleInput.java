package managers;

import java.util.Scanner;

public class ConsoleInput extends InputHandler{
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public Scanner handleInput() {
        return scanner;
    }
}
