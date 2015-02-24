package nl.tue.s2id90.groupNN;


import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * A simple draughts player that plays the first moves that comes to mind
 * and values all moves with value 0.
 * @author huub
 */
public class MyPlayer extends DraughtsPlayer {
    private AlphaBeta alphaBeta;
    public MyPlayer() {
        
    }
    @Override
    /** @return a random move **/
    public Move getMove(DraughtsState s) {

        GameNode root = new GameNode(s,null);
        alphaBeta = new AlphaBeta(root, 5); 
        GameNode bestNode;
        float best;
        
        //because the evaluation function always evaluates for white
        //we need to know when to maximize and minimize the function
        boolean maximize = s.isWhiteToMove();
        
        //actual computation and bulding the tree
        try {
            if(maximize) {
                alphaBeta.alphaBetaMax(root,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
            }
            else {
                alphaBeta.alphaBetaMin(root,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, 0);       
            }
        }
        catch(AIStoppedException e) {
            
        }
        /**finally {
            //return best successor
            best = root.getSuccessors().get(0).getEval();
            bestNode = root.getSuccessors().get(0);
            
            for(GameNode node : root.getSuccessors()) {
                System.out.println(node.getEval());
                //maximize the evaluation value
                if (maximize && best <= node.getEval() ) {
                    bestNode = node;
                    best = node.getEval();
                }
                // minimize the evaluation value
                else if (!maximize && best >= node.getEval()) { 
                    bestNode = node;
                    best = node.getEval();
                }
            }
        }**/
        //System.out.println("chose:" + best);
        return root.getBestMove();
    }

    @Override
    public Integer getValue() {
        return 0;
    }
    
    public void stop() {
        System.out.println("stop");
        alphaBeta.stop();
    }
}
