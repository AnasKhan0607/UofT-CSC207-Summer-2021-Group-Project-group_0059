import java.util.ArrayList;

public class GameTree {
    public static void main(String[] args) {

    }

    private String id;
    private String dialogue;
    private ArrayList<GameTree> subtrees = new ArrayList<GameTree>();
    private GameTree parentTree;

    public GameTree(String dialogue){
        this.dialogue = dialogue;
    }

    public int length(){
        if (subtrees.size() == 0){
            return 1;
        }
        else {
            int total = 0;
            for (GameTree subtree : subtrees) {
                total += subtree.length();
            }
            return 1 + total;
        }
    }

    public ArrayList<GameTree> returnSubtrees(GameTree tree, String id){
        if (this.id.equals(id)){
            return this.subtrees;
        }
        else{
            for (GameTree subtree : subtrees) {
                return
            }
        }
    }

    public void addChoice(String id, String dialogue){
        ArrayList<GameTree> subtrees = returnSubtrees(id);
        subtrees.add(new GameTree(dialogue));
    }
}
