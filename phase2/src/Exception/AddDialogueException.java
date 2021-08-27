package Exception;

public class AddDialogueException extends Exception{
    /**
     * Prints message; signifies that something wrong had occurred when adding
     * a dialogue to a game.
     * @param message a string that is printed when this exception is encountered
     */
    public AddDialogueException(String message) { super(message); }
}
