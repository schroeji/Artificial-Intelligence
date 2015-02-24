package nl.tue.s2id90.groupNN;


import java.util.ArrayList;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * A simple draughts player that plays the first moves that comes to mind
 * and values all moves with value 0.
 * @author huub
 */
public class MyPlayerIDS_1 extends DraughtsPlayer {
    private AlphaBetaCopy alphaBeta;
    private ArrayList<GameNode> successorsBackup = new ArrayList<GameNode>();
    private int playedValue = 0;
    public MyPlayerIDS_1() {
        
    }
    @Override
    /** @return a random move **/
    public Move getMove(DraughtsState s) {
        DraughtsState originalState = s.clone();
        GameNode root = new GameNode(s,null);
        alphaBeta = new AlphaBetaCopy(root, 1); 
        Move bestMove = null;
        int maxDepth = 20;
        int startValue = alphaBeta.eval_GameState(s);
        int i = 0;
        //because the evaluation function always evaluates for white
        //we need to know when to maximize and minimize the function
        boolean maximize = s.isWhiteToMove();
        
        //actual computation and bulding the tree
        try {
            for(i = 1; i <=20; i++) {
                alphaBeta.setMaxDepth(i);
                root.clearSuccessors();
                if(maximize) {
                    alphaBeta.alphaBetaMax(root,
                        Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
                }
                else {
                    alphaBeta.alphaBetaMin(root,
                        Integer.MIN_VALUE, Integer.MAX_VALUE, 0);       
                }
                bestMove = root.getBestMove();
                if(bestMove == null){
                    System.out.println("error bei: " + i);
                }
            }
        }
        catch(AIStoppedException e) {
            System.out.println(i);
        }
        //System.out.println("chose:" + bestNode.getEval());
        originalState.doMove(bestMove);
        this.playedValue = alphaBeta.eval_GameState(originalState) - startValue;
        this.playedValue = maximize ? playedValue : -playedValue;
        return bestMove;
    }

    @Override
    public Integer getValue() {
        return playedValue;
    }
    
    private void backupSuccessors(GameNode root) {
        successorsBackup.clear();
        for (GameNode successor : root.getSuccessors()) {
            successorsBackup.add(successor);
        }
    }
    
    public void stop() {
        System.out.println("stop");
        alphaBeta.stop();
    }
}
