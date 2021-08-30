package Exception;

/**
 * Class that is used when an exception is thrown (if a parent dialogue cannot be found or if the maximum number of choices is exceeded).
 */
public class AddDialogueException extends Exception{
    /**
     * Prints message; signifies that something wrong had occurred when adding
     * a dialogue to a game.
     * @param message a string that is printed when this exception is encountered
     */
    public AddDialogueException(String message) { super(message); }
}
