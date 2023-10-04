package exceptions;

public class RecursiveException extends StackOverflowError{
    public RecursiveException() {
        super("A recursive script is called. Script execution is stopped.");
    }
}
