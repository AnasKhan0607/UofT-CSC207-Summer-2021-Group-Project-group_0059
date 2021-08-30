package Interface;

import Entity.GameTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for the storage of Game data.
 */
public interface GameStorage {
    String toString();
    String toString(int id);
    int size();

    String getDescriptionById(int id);
    boolean setDescriptionById(int id, String description);

//    String getPictureById(int id);
//    boolean setPictureById(int id, String pictureName);

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
    boolean addChoiceToDialogue(String childDialogue, String childDescription, int parentDialogueId);
}

