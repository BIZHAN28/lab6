package exceptions;

public class NoneValueArgumentException extends IllegalArgumentException{
    public NoneValueArgumentException() {super("There is no value in argument. Try again.");}
}
