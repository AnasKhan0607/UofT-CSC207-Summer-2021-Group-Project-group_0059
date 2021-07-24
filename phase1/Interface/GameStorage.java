public interface GameStorage {
    String toString();
    int size();

    String getDialogueById(int id);
    int getIdByDialogue(String dialogue);
    boolean setDialogueById(int id, String dialogue);
    boolean deleteDialogueById(int id);

    static GameStorage initializeStorage(int choiceNumLimit, String initialDialogue) {
        return null;
    }
    void addChoiceToDialogue(String childDialogue, int parentDialogueId);
}

