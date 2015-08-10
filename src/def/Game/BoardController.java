/*
 * Copyright (c) 2013. Created by Alexander Voevodin [Alvo]
 */

package def.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * User: Lux
 * Date: 27.11.13
 * Time: 15:43
 */

public class BoardController {

    private Board board;
    private Point emptyBrick;
    private Point prevState;

    public BoardController(Board board) {
        emptyBrick = new Point();
        if(board == null)
            return;
        this.board = board;
        prevState = new Point(board.getBricksAmount(), board.getBricksAmount());
    }

    private void findEmptyBrickPos(){
        if(board == null)
            return;
        for (int m = 0; m < board.getFieldSize(); m++){
            for (int n = 0; n < board.getFieldSize(); n++){
                if(board.getBrick(m, n) == Board.EMPTY_BRICK){
                    emptyBrick.x = m;
                    emptyBrick.y = n;
                    return;
                }
            }
        }
    }

    public void fillTargetBoard(){
        int num = 0;
        for (int m = 0; m < board.getFieldSize(); m++){
            for (int n = 0; n < board.getFieldSize(); n++){
                board.setBrick(m, n, num++);
            }
        }
    }

    public void shuffle(int times){
        Random rnd = new Random();
        ArrayList<Point> directions = MoveDirections.toList();
        int index;
        for (int i = 0; i < times; i++){
            index = rnd.nextInt(4);
            performMove(directions.get(index).x, directions.get(index).y);
        }
    }

    private void swap(int i, int j){
        board.setBrick(emptyBrick.x, emptyBrick.y, board.getBrick(i, j));
        board.setBrick(i, j, Board.EMPTY_BRICK);
        emptyBrick.x = i;
        emptyBrick.y = j;
    }

    public boolean performMove(int i, int j){
        if(prevState.x == emptyBrick.x + j && prevState.y == emptyBrick.y + i){
            return false;
        }
        if(emptyBrick.x + j >= board.getFieldSize() || emptyBrick.x + j < 0 ||
           emptyBrick.y + i >= board.getFieldSize() || emptyBrick.y + i < 0){
           return false;
        }
        swap(emptyBrick.x + j, emptyBrick.y + i); // i and j should be used in invert order because of 2d array indexing
        return true;
    }

    public void memorizeState(){
        prevState.x = emptyBrick.x;
        prevState.y = emptyBrick.y;
    }

    public void setBoard(Board brd){
        this.board = brd;
        findEmptyBrickPos();
    }
}