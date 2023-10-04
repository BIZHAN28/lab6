package managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInput extends InputHandler{
    private File file;
    private static Scanner scanner;

    public FileInput(File file) throws FileNotFoundException {
        this.file = file;
        scanner = new Scanner(file);
    }

    public void setFile(File file) throws FileNotFoundException {
        this.file = file;
        scanner = new Scanner(file);
    }

    @Override
    public Scanner handleInput() {
        return scanner;
    }
}
