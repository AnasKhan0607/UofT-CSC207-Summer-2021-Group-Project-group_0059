public class AddDialogueException extends Exception{
    public static void main(String[] args) {
        Exception exception = new AddDialogueException("bruh", new IndexOutOfBoundsException());
//        System.out.println(exception.getCause());
    }
    public AddDialogueException() { super(); }
    public AddDialogueException(String message) { super(message); }
    public AddDialogueException(String message, Throwable cause) { super(message, cause); }
    public AddDialogueException(Throwable cause) { super(cause); }
}
