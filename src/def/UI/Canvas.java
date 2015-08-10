/*
 * Copyright (c) 2013. Created by Alexander Voevodin [Alvo]
 */

package def.UI;

import def.Game.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lux on 05.12.13.
 */
public class Canvas extends JPanel {

    private Board board;
    private Dimension brickSize;
    private Font font;
    private BufferedImage brickTexture;
    private final String brickImagePath = "src/def/Res/brick.png";

    public Canvas() throws IOException {
        super();
        super.setDoubleBuffered(true);
        this.brickSize = new Dimension();
        this.font = new Font("Arial", Font.TRUETYPE_FONT, 36);
        this.brickTexture = createBufImage(new File(brickImagePath));
    }

    private BufferedImage createBufImage(File source ) throws IOException {
        BufferedImage tmp  = ImageIO.read(source);
        int width = tmp.getWidth();
        int height = tmp.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(tmp, 0, 0, null);
        return img;
    }

    @Override
    public void paintComponent(Graphics G){
        if(board == null)
            return;
        clear(G);
        setBackground(Color.GRAY);
        Graphics2D g2d = (Graphics2D)G;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        brickSize.setSize(getWidth() / board.getFieldSize(), getHeight() / board.getFieldSize());

        for (int i = 0; i < board.getFieldSize(); i++) {
            for (int j = 0; j < board.getFieldSize(); j++){
                if(board.getBrick(j, i) == 0)
                    continue;
                g2d.drawImage(brickTexture, i * brickSize.width, j * brickSize.height, brickSize.width, brickSize.height, null);
                g2d.setPaint(Color.BLACK);
                g2d.drawString(String.valueOf(board.getBrick(j, i)), i * brickSize.width  + (brickSize.width  / 2),
                                                                     j * brickSize.height + (brickSize.height / 2));
            }
        }
    }

    public void renderBoard(Board board){
        this.board = board;
        repaint();
    }

    private void clear(Graphics g) {
        super.paintComponent(g);
    }
}