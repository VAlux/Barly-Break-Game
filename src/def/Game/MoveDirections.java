/*
 * Copyright (c) 2013. Created by Alexander Voevodin [Alvo]
 */

package def.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: Lux
 * Date: 29.11.13
 * Time: 23:31
 */
public class MoveDirections {
    public static final Point DOWN =    new Point(0, 1);
    public static final Point UP =      new Point(0, -1);
    public static final Point LEFT =    new Point(-1, 0);
    public static final Point RIGHT =   new Point(1, 0);

    public static ArrayList<Point> toList(){
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(DOWN);
        list.add(UP);
        list.add(LEFT);
        list.add(RIGHT);
        return list;
    }
}
