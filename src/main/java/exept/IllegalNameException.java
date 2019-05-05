package exept;

public class IllegalNameException extends Exception {
    public static IllegalNameException DEFAULT_INSTANCE = new
            IllegalNameException("Job cannot be null or empty");
    public IllegalNameException(String message) {
        super(message);
    }
}
