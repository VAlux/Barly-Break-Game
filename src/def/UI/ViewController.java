/*
 * Copyright (c) 2013. Created by Alexander Voevodin [Alvo]
 */

package def.UI;

import def.AI.Brain;
import def.AI.GraphVertex;
import def.Game.Board;
import def.Game.BoardController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lux on 05.12.13.
 */

public class ViewController extends JFrame{
    private JSlider sldProgress;
    private JPanel pnlRoot;
    private JPanel pnlSurface;
    private JButton btnShuffle;
    private JButton btnSolve;

    private Canvas canvas;
    private ArrayList<GraphVertex> solution;
    private BoardController boardController;
    private Brain brain;
    private Board targetBoard;
    private Board initBoard;

    private final int SHUFFLE_TIMES = 50;
    private final int BOARD_SIZE = 3;

    public ViewController() {
        //frame initialization
        setSize(600, 600);
        setTitle("Barly-Break game solver");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(pnlRoot);
        addActionListeners();

        //fields
        sldProgress.setMinimum(0);
        sldProgress.setEnabled(false); // we have no solution now...
        solution = new ArrayList<GraphVertex>();
        initBoard = new Board(BOARD_SIZE);
        targetBoard = new Board(BOARD_SIZE);
        boardController = new BoardController(targetBoard);
        boardController.fillTargetBoard();

        resetInitBoard();
        this.setVisible(true);
        canvas.renderBoard(initBoard);
    }

    private void resetInitBoard(){
        try {
            initBoard = targetBoard.clone(); // they are equal initially
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getLocalizedMessage());
        }
        boardController.setBoard(initBoard);
    }

    private void refreshSolution(){
        this.solution = brain.getSolution();
        sldProgress.setMaximum(solution.size() - 1);
        sldProgress.setEnabled(true); // now we can scroll through the solution
    }

    private void reset(){
        sldProgress.setValue(0);
        sldProgress.setEnabled(false);
        solution.clear();
        resetInitBoard();
    }

    private void addActionListeners(){
        sldProgress.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(solution.size() > 0){
                    canvas.renderBoard(solution.get((solution.size() - 1) - sldProgress.getValue()).getBoard());
                }
            }
        });

        btnShuffle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                btnSolve.setEnabled(true);
                boardController.shuffle(SHUFFLE_TIMES);
                canvas.renderBoard(initBoard);
            }
        });

        btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSolve.setEnabled(false);
                solve();
            }
        });
    }

    private void createUIComponents() {
        try {
            this.pnlSurface = new Canvas(); // set the canvas
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        this.canvas = (Canvas)pnlSurface; // we just need to communicate with pnlSurface as with canvas, so we morph it.
    }

    private void solve(){
        brain = new Brain(initBoard, targetBoard);
        try {
            if(brain.findSolution())
                refreshSolution();
            else
                JOptionPane.showMessageDialog(this, "There is no solution", "Oops!", JOptionPane.ERROR_MESSAGE); // nobody can see this message.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
