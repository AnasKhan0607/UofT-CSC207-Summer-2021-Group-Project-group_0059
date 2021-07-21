import java.util.ArrayList;

public class GameTree {
    public static void main(String[] args) {
        GameTree it = new GameTree(4, "0", null);
        GameTree t1 = new GameTree(4, "1", it);
        GameTree t2 = new GameTree(4, "2", it);
        GameTree t3 = new GameTree(4, "3", it);
        GameTree t4 = new GameTree(4, "4", it);
        GameTree t5 = new GameTree(4, "5", t1);
        GameTree t17 = new GameTree(4, "17", t4);
        GameTree t18 = new GameTree(4, "18", t4);
        GameTree t19 = new GameTree(4, "19", t4);
        GameTree t20 = new GameTree(4, "20", t4);
        GameTree t21 = new GameTree(4, "21", t5);
        GameTree t22 = new GameTree(4, "22", t5);
        GameTree t23 = new GameTree(4, "23", t5);


        System.out.println(it.getSpecificDialogue(22));
        System.out.println("Final Length: " + it.length());
    }

    private int id;
    private int choiceNum;
    private String dialogue;
    private ArrayList<GameTree> subtrees = new ArrayList<GameTree>();
    // initial node's parent tree must be null.
    private GameTree parentTree;

    public GameTree(int choiceNum, String dialogue, GameTree parentTree){
        this.choiceNum = choiceNum;
        this.dialogue = dialogue;
        this.parentTree = parentTree;
        this.id = this.setId();
        if (this.parentTree != null){
            this.parentTree.addToSubtrees(this);
        }
    }

    public int getId() {
        return id;
    }

    public int setId(){
        if(this.parentTree == null){
            return 0;
        }
        else{
            int parentId = this.parentTree.getId();
            return parentId * choiceNum + this.parentTree.getSubtreesSize() + 1;
        }
    }

    public int getSubtreesSize(){
        return subtrees.size();
    }

    public boolean addToSubtrees(GameTree tree){
        if (getSubtreesSize() < choiceNum){
            this.subtrees.add(tree);
            return true;
        }
        else{
            return false;
        }
    }

    public String getDialogue() {
        return dialogue;
    }

    public String getSpecificDialogue(int specificId) {
        // this search for a specific dialogue; log n running time.
        GameTree tree = this.getInitialTree();
        ArrayList<Integer> path = this.IDtoNodePath(specificId);

        for (int i : path){
            try {
                tree = tree.subtrees.get(i - 1);
            } catch (IndexOutOfBoundsException b) {
                return null;
            }
        }

        return tree.getDialogue();
    }

    public GameTree getInitialTree(){
        GameTree tree = this;
        while (this.parentTree != null){
            tree = this.parentTree;
        }
        return tree;
    }

    public ArrayList<Integer> IDtoNodePath(int specificId){
        ArrayList<Integer> path = new ArrayList<Integer>();
        int ID = specificId;
        while (ID != 0){
            int remainder = ID % choiceNum;
            ID = ID / choiceNum;
            if (remainder == 0){
                remainder = choiceNum;
                ID -= 1;
            }
            path.add(0, remainder);
        }
        return path;
    }

    public int length(){
        if (this.subtrees.size() == 0){
            return 1;
        }
        else {
            int total = 0;
            System.out.println(this.subtrees);
            for (GameTree subtree : this.subtrees) {
                System.out.println(subtree.dialogue + ". length: " + subtree.length());
                total += 1 + subtree.length();
            }
            return total;
        }
    }
//
//
//    public ArrayList<GameTree> getSubtrees(String id){
//        if (this.id.equals(id)){
//            return this.subtrees;
//        }
//        else{
//            ArrayList<GameTree> trees;
//            for (GameTree subtree : subtrees) {
//                trees = subtree.getSubtrees(id);
//                if (trees != null){
//                    return trees;
//                }
//            }
//        }
//        return null;
//    }
//
//    public boolean addChoice(String id, String dialogue){
//        ArrayList<GameTree> subtrees = getSubtrees(id);
//
//        if (subtrees != null){
//            subtrees.add(new GameTree(this.choiceNum, dialogue, this));
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//    public boolean is_empty(){
//        return this.subtrees.size() == 0;
//    }
}
