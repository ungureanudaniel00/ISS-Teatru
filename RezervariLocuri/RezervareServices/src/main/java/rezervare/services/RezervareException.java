package rezervare.services;

public class RezervareException extends Exception{
    public RezervareException(){}

    public RezervareException(String message){
        super(message);
    }

    public RezervareException(String message, Throwable cause){
        super(message, cause);
    }
}
