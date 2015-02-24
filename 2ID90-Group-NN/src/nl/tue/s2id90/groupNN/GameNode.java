/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.groupNN;
import nl.tue.s2id90.game.*;
import org10x10.dam.game.Move;
import java.util.ArrayList;
/**
 *
 * @author Hidden
 */
public class GameNode {
    private final Move lastMove;
    private GameState state;
    private int eval_Value;
    private ArrayList<GameNode> successors = new ArrayList<GameNode>();
    private Move bestMove;
    
    public GameNode(GameState state, Move lastMove) {
        this.lastMove = lastMove;
        this.state = state;
    }
    
    public Move getMove() {
        return this.lastMove;
    }
    
    public void clearSuccessors() {
        this.successors.clear();
    }
    
    public void addSuccessor(GameNode successor){
        this.successors.add(successor);
    }
    
    public void expand() {
        ArrayList<Move> moves = (ArrayList) this.state.getMoves();
        for (Move move : moves) {
            GameState newState = this.state.clone();
            newState.doMove(move);
            GameNode node = new GameNode(newState, move);
            this.successors.add(node);
        }
    }
    
    public ArrayList<GameNode> getSuccessors(){
        return successors;
    }

    public void setGameState(GameState state) {
        this.state = state;
    }
    
    /**
     * The returned GameState is not always the real GameState for this node
     * because in the AlphaBeta-Search the State is NOT cloned for every 
     * new node.
     * @return The GameState associated with this node 
     */
    public GameState getGameState() {
        return state;
    }
    
    public Move getBestMove() {
        return bestMove;
    }
    
    public void setBestMove(Move bestMove) {
        this.bestMove = bestMove;
    }
    
    public void setEval(int eval) {
        this.eval_Value = eval;
    }
    
    public int getEval(){
        return eval_Value;
    }    
    
}
