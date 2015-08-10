/*
 * Copyright (c) 2013. Created by Alexander Voevodin [Alvo]
 */

package def.AI;

import def.Game.Board;
import def.Game.BoardController;
import def.Game.MoveDirections;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: Lux
 * Date: 27.11.13
 * Time: 21:50
 */

public class Brain {

    private ArrayList<GraphVertex> opened;
    private ArrayList<GraphVertex> closed;
    private GraphVertex target;
    private BoardController controller;
    private GraphVertex NVert;

    public Brain(Board init, Board target) {
        this.opened = new ArrayList<GraphVertex>();
        this.closed = new ArrayList<GraphVertex>();
        this.target = new GraphVertex(target);
        this.NVert = new GraphVertex(init);
        this.NVert.setParent(NVert); // root vertex with loop parent reference,
        this.calcFitness(NVert); // Calc fitness for the initial vertex
        this.opened.add(NVert);
        this.controller = new BoardController(NVert.getBoard());
    }

    public boolean findSolution() throws CloneNotSupportedException {
        while(!getMinFitnessVertex().equals(target)){
            if(opened.isEmpty())
                return false;

            NVert = getMinFitnessVertex();
            controller.setBoard(NVert.getParent().getBoard()); // we have to memorize exactly the PARENT to the current graph vertex.
            controller.memorizeState(); // this should be done before beginning the next transition sequence to avoid "mirroring" transitions.
            closed.add(NVert);
            opened.remove(NVert);
            GraphVertex newVertex = prepareToOpenFrom(NVert);
            for (Point direction : MoveDirections.toList()){
                if(controller.performMove(direction.x, direction.y)){
                    calcFitness(newVertex);
                    newVertex.setParent(NVert);
                    if(updateList(newVertex, opened, true) || updateList(newVertex, closed, false)) // search for the same vertices in opened and closed lists
                        continue;
                    opened.add(newVertex);
                    newVertex = prepareToOpenFrom(NVert);
                }
            }
        }
        return true;
    }

    private GraphVertex prepareToOpenFrom(GraphVertex vertex) throws CloneNotSupportedException {
        GraphVertex newVertex = vertex.clone();
        newVertex.setLevel(vertex.getLevel() + 1); // deepness increased.
        controller.setBoard(newVertex.getBoard());
        return newVertex;
    }

    private boolean updateList(GraphVertex vertex, ArrayList<GraphVertex> list, boolean isOpened){
                int equal;
                boolean equalsFound = false;
                for (int k = 0; k < list.size(); k++){
                    equal = 0;
                    for (int i = 0; i < vertex.getBoard().getFieldSize(); i++){
                        for(int j = 0; j < vertex.getBoard().getFieldSize(); j++){
                            if (list.get(k).getBoard().getBrick(i, j) == vertex.getBoard().getBrick(i, j))
                                equal++;
                        }
                    }
                    if(equal == vertex.getBoard().getBricksAmount()){
                        equalsFound = true;
                        if(list.get(k).getFitness() > vertex.getFitness()) {
                            list.get(k).setFitness(vertex.getFitness());
                            list.get(k).setParent(vertex.getParent());
                            if(!isOpened){
                                opened.add(list.get(k));
                                closed.remove(list.get(k));
                            }
                        }
            }
        }
        return equalsFound;
    }

    private GraphVertex getMinFitnessVertex(){
        GraphVertex minFitnessVertex = opened.get(0);
        for(GraphVertex vertex : opened){
            if(minFitnessVertex.getFitness() > vertex.getFitness()){
                minFitnessVertex = vertex;
            }
        }
        return minFitnessVertex;
    }

    private void calcFitness(GraphVertex vertex){
        int nInPlace = 0; // bricks amount, that are not in the right place
        for (int i = 0; i < target.getBoard().getFieldSize(); i++){
            for (int j = 0; j < target.getBoard().getFieldSize(); j++){
                if(vertex.getBoard().getBrick(i, j) != target.getBoard().getBrick(i, j)){
                    nInPlace++;
                }
            }
        }
        vertex.setFitness(vertex.getLevel() + nInPlace);
    }

    public ArrayList<GraphVertex> getSolution() {
        //restore the solution path using parent references.
        ArrayList<GraphVertex> solution = new ArrayList<GraphVertex>();
        GraphVertex target = getMinFitnessVertex();
        solution.add(target);
        while(target.hasParent()){
            target = target.getParent();
            solution.add(target);
        }
        return solution;
    }
}