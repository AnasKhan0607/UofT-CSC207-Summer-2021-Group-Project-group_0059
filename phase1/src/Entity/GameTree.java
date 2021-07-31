package Entity;

import Interface.GameStorage;

import java.util.ArrayList;
import java.util.List;

import Exception.AddDialogueException;

/**
 * The entity class which represents a GameTree. This is a part of the tree structure containing different
 * dialogues and options which users can navigate through to play the game.
 */

public class GameTree implements GameStorage {
    private int id;
    final int choiceNumLimit;
    private String dialogue;
    private List<GameTree> subtrees = new ArrayList<GameTree>();
    private GameTree parentTree;

    /**
     * The contructor method for GameTree which initializes the tree with a dialogue, number of choices,
     * and a generated id.
     *
     * @param choiceNumLimit The limit for how many choices this tree can have.
     * @param initialDialogue The initial dialogue that the user will see when they are at this tree.
     */
    public GameTree(int choiceNumLimit, String initialDialogue){
        this.choiceNumLimit = choiceNumLimit;
        this.dialogue = initialDialogue;
        this.parentTree = null;
        this.id = this.setId();
    }

    private GameTree(String childDialogue, int parentDialogueId, GameTree initialTree) throws AddDialogueException {
        this.dialogue = childDialogue;
        try{
            this.parentTree = initialTree.getTreeById(parentDialogueId);
        }
        catch(IndexOutOfBoundsException b){
            throw new AddDialogueException("Cannot find parent dialogue.");
        }
        this.choiceNumLimit = this.parentTree.choiceNumLimit;

        boolean addSuccess = this.parentTree.addToSubtrees(this);
        if (!addSuccess){
            throw new AddDialogueException("Max number of choices exceeded.");
        }

        this.id = this.setId();
    }

    /**
     * A method which attempts to add choices by creating the next GameTree.
     *
     * @param childDialogue The child's dialogue text that should be added
     * @param parentDialogueId The Id of the parent tree.
     * @return A boolean depending on whether the choice was successfully added.
     */
    public boolean addChoiceToDialogue(String childDialogue, int parentDialogueId){
        try{
            new GameTree(childDialogue, parentDialogueId, this);
            return true;
        }
        catch (AddDialogueException b){
            System.out.println(b.getMessage());
            return false;
        }
    }

    private int setId(){
        if(this.parentTree == null){
            return 0;
        }
        else{
            int parentId = this.parentTree.id;
            return parentId * choiceNumLimit + this.parentTree.subtrees.indexOf(this) + 1;
        }
    }

    private void resetAllId(){
        id = setId();

        for (GameTree tree: subtrees){
            tree.resetAllId();
        }
    }

    /**
     * Method to get the IDs of all subtrees in this tree.
     * @return An ArrayList containing integers for the IDs of all subtrees.
     */

    public ArrayList<Integer> getAllId(){
        ArrayList<Integer> allId = new ArrayList<>();
        allId.add(this.id);
        for (GameTree subtree : this.subtrees) {
            allId.addAll(subtree.getAllId());
        }
        return allId;
    }

    private Integer getParentId(int childId){
        if (childId == 0){
            return null;
        }
        else if (childId % choiceNumLimit == 0){
            return childId / choiceNumLimit - 1;
        }
        else{
            return childId / choiceNumLimit;
        }
    }

    /**
     * Method to get the IDs of all parents in this tree.
     * @return An ArrayList containing integers for the IDs of all parents.
     */

    public ArrayList<Integer> getParentDialogueIds(List<Integer> childrenDialogueIds){
        ArrayList<Integer> parentIds = new ArrayList<>();
        for (int children: childrenDialogueIds){
            parentIds.add(getParentId(children));
        }
        return parentIds;
    }

    private boolean addToSubtrees(GameTree tree){
        if (subtrees.size() < choiceNumLimit){
            this.subtrees.add(tree);
            return true;
        }
        else{
            return false;
        }
    }


    private GameTree getTreeById(int id) throws IndexOutOfBoundsException{
        GameTree tree = this.getInitialTree();
        ArrayList<Integer> path = this.IDtoNodePath(id);

        for (int i : path){
            tree = tree.subtrees.get(i - 1);
        }

        return tree;
    }

    // helper function to getSpecificDialogue()
    // returns the initial tree
    private GameTree getInitialTree(){
        GameTree tree = this;
        while (this.parentTree != null){
            tree = this.parentTree;
        }
        return tree;
    }

    // helper function to getSpecificDialogue()
    private ArrayList<Integer> IDtoNodePath(int id){
        ArrayList<Integer> path = new ArrayList<Integer>();
        int ID = id;
        while (ID != 0){
            int remainder = ID % choiceNumLimit;
            ID = ID / choiceNumLimit;
            if (remainder == 0){
                remainder = choiceNumLimit;
                ID -= 1;
            }
            path.add(0, remainder);
        }
        return path;
    }

    /**
     * Gets a specific dialogue through a tree ID
     * @param id The ID of the tree who's dialogue should be retrieved
     * @return A string which is the dialogue of this tree, if it is found.
     */

    public String getDialogueById(int id) {
        try{
            return getTreeById(id).dialogue;
        }
        catch(IndexOutOfBoundsException b){
            return null;
        }
    }

    /**
     * A method for setting the dialogue of a particular tree according to its ID.
     *
     * @param id The ID of the tree who's dialogue should be set
     * @param dialogue The new dialogue which will be set for this tree
     * @return True if the dialogue is changed successfully, otherwise false
     */

    public boolean setDialogueById(int id, String dialogue){
        try{
            getTreeById(id).dialogue = dialogue;
            return true;
        }
        catch(IndexOutOfBoundsException b){
            return false;
        }
    }

    /**
     * Deletes the dialogue of a tree with the given ID.
     * @param id The ID of the tree who's dialogue should be deleted.
     * @return True if the dialogue was successfully deleted, otherwise false.
     */

    public boolean deleteDialogueById(int id){
        if (id == 0){ return false; }
        try{
            GameTree initialTree = this.getInitialTree();
            GameTree tree = initialTree.getTreeById(id);
            ArrayList<GameTree> subtrees = (ArrayList<GameTree>) tree.parentTree.subtrees;
            int index = subtrees.indexOf(tree);
            if (index == -1){ return false; }
            else{
                subtrees.remove(index);
                if (subtrees.size() != index){
                    tree.parentTree.resetAllId();
                }
                return true;
            }
        }
        catch(IndexOutOfBoundsException b){ return false; }
    }

    private GameTree getTreeByDialogue(String dialogue){
        GameTree tree = null;
        if(this.dialogue.equals(dialogue)){
            return this;
        }

        for (GameTree i : this.subtrees){
            if(tree == null){
                tree = i.getTreeByDialogue(dialogue);
            }
            else{
                break;
            }
        }

        return tree;
    }

    /**
     * Finds the ID of the tree with the given dialogue.
     * @param dialogue the string containing the dialogue text of the tree you are searching for.
     * @return The ID of the tree containing this dialogue.
     */

    public int getIdByDialogue(String dialogue){
        return getInitialTree().getTreeByDialogue(dialogue).id;
    }

    /**
     * Returns the total number of nodes in this tree
     * @return the number of nodes in the tree (the size of the tree)
     */

    public int size(){
        int total = 0;
        for (GameTree subtree : this.subtrees) {
            total += 1 + subtree.size();
        }

        if (this.id == 0){
            return total + 1;
        }

        return total;
    }

    /**
     * Override the default string method to print the tree in a way the user can read it.
     * @return a string representation of this tree.
     */
    @Override
    public String toString(){
        return printTree(0);
    }

    /**
     * Get a string representation of a tree with a particular ID)
     * @param id The ID of the desired tree
     * @return the string which represents this tree
     */
    public String toString(int id){
        return getTreeById(id).printTree(0);
    }

    private String printTree(int depth){
        String space = this.depthToPrespace(depth);

        if (this.subtrees.size() == 0){
            return space + "(" + this.id + ")";
        }
        StringBuilder tree = new StringBuilder(space + "(" + this.id + ")");
        for (GameTree subtree : this.subtrees) {
            tree.append(subtree.printTree(depth + 1));
        }
        return tree.toString();
    }

    // helper function 1 to printTree()
    private String depthToPrespace(int depth){
        StringBuilder space = new StringBuilder("\n");
        for (int i = 0; i < depth; i++){
            space.append("       ");
        }
        return space.toString();
    }

    /**
     * Gets the dialogues of all the child trees.
     * @param parentDialogueId the ID of the parent tree
     * @return a list of strings which are the dialogues of all children tree.
     */

    public ArrayList<String> getChildrenDialogues(int parentDialogueId){
        ArrayList<String> childrenDialogues = new ArrayList<>();
        GameTree parentTree;
        try{
            parentTree = getTreeById(parentDialogueId);
        }
        catch (IndexOutOfBoundsException b){
            return null;
        }
        for (GameTree tree: parentTree.subtrees){
            childrenDialogues.add(tree.dialogue);
        }

        return childrenDialogues;
    }
}
