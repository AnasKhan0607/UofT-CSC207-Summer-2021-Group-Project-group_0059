package Interface;

import Entity.GameTree;

public interface GameStorage {
    String toString();
    int size();

    String getDialogueById(int id);
    int getIdByDialogue(String dialogue);
    boolean setDialogueById(int id, String dialogue);
    boolean deleteDialogueById(int id);

    static GameStorage initializeStorage(int choiceNumLimit, String initialDialogue) {
        return new GameTree(choiceNumLimit, initialDialogue);
    }
    void addChoiceToDialogue(String childDialogue, int parentDialogueId);
}

