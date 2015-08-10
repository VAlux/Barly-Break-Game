/*
 * Copyright (c) 2013. Created by Alexander Voevodin [Alvo]
 */

package def.Game;

/**
 * User: Lux
 * Date: 27.11.13
 * Time: 15:37
 */

public class Board {

    public static final int EMPTY_BRICK = 0;

    private int [][] fieldMatrix;
    private int fieldSize;

    public Board(int size) {
        fieldMatrix = new int[size][size];
        fieldSize = size;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getBrick(int i, int j){
        return fieldMatrix[i][j];
    }

    public void setBrick(int i, int j, int brickValue){
        fieldMatrix[i][j]= brickValue;
    }

    public int getBricksAmount(){
        return fieldSize * fieldSize;
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        Board brd = new Board(this.fieldSize);
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = 0; j < fieldMatrix.length; j++) {
                brd.setBrick(i, j, this.getBrick(i, j));
            }
        }
        return brd;
    }

    @Override
    public String toString(){
        String field = "";
        for (int i = 0; i < fieldSize; i++){
            for (int j = 0; j < fieldSize; j++){
                field += fieldMatrix[i][j] + "\t";
            }
            field += "\n";
        }
        return field;
    }
}
