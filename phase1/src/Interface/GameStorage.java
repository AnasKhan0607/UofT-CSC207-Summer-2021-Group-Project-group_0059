package Interface;

import Entity.GameTree;

import java.util.ArrayList;
import java.util.List;

public interface GameStorage {
    String toString();
    int size();

    String getDialogueById(int id);
    int getIdByDialogue(String dialogue);
    boolean setDialogueById(int id, String dialogue);
    boolean deleteDialogueById(int id);
    ArrayList<Integer> getParentDialogueIds(List<Integer> childrenDialogueIds);
    ArrayList<Integer> getAllId();
    ArrayList<String> getChildrenDialogues(int parentDialogueId);

    static GameStorage initializeStorage(int choiceNumLimit, String initialDialogue) {
        return new GameTree(choiceNumLimit, initialDialogue);
    }
    boolean addChoiceToDialogue(String childDialogue, int parentDialogueId);
}

