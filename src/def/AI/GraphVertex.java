/*
 * Copyright (c) 2013. Created by Alexander Voevodin [Alvo]
 */

package def.AI;

import def.Game.Board;

/**
 * User: Lux
 * Date: 27.11.13
 * Time: 21:52
 */

public class GraphVertex {

    private Board board;
    // metric, which represents the deepness of  vertex in graph + amount of bricks in incorrect positions
    private int fitness;
    private GraphVertex parent;
    private int level;   // vertex deepness level

    public GraphVertex(Board board) {
        this.board = board;
        this.fitness = 0;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof GraphVertex))
            return false;
        GraphVertex vertex = (GraphVertex)other;
        int inPlace = 0;
        for(int i = 0; i < vertex.getBoard().getFieldSize(); i++){
            for(int j = 0; j < vertex.getBoard().getFieldSize(); j++){
                if(this.getBoard().getBrick(i, j) == vertex.getBoard().getBrick(i, j))
                    inPlace++;
            }
        }
        return inPlace == vertex.getBoard().getBricksAmount();
    }

    @Override
    public GraphVertex clone() throws CloneNotSupportedException {
        GraphVertex target = new GraphVertex(new Board(board.getFieldSize()));
        for (int i = 0; i < target.getBoard().getFieldSize(); i++) {
            for (int j = 0; j < target.getBoard().getFieldSize(); j++) {
                target.getBoard().setBrick(i, j, board.getBrick(i, j));
            }
        }
        target.setFitness(fitness);
        target.setParent(parent);
        return target;
    }

    @Override
    public String toString(){
        return String.valueOf(this.fitness);
    }

    public boolean hasParent(){
        return !parent.equals(this);
    }

    public GraphVertex getParent() {
        return parent;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setParent(GraphVertex parent) {
        this.parent = parent;
    }

    public Board getBoard() {
        return board;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getLevel() {
        return level;
    }
}
