package exceptions;

public class ByteLossException extends RuntimeException{
    public ByteLossException(){super("It looks like some bytes were lost during transmission");}
}
