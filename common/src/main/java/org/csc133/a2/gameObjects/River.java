package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.Game;

import java.awt.*;

// ----------------------------------------------------------------------------
//
public class River extends Fixed {
//    final private Point location;
//    final private int width;
//    final private int height;

    public River() {
//        width = Game.DISP_W;
//        height = Game.DISP_H / 10;
//        location = new Point(0, Game.DISP_H / 5);
        super(  new Point(0, Game.DISP_H / 5),
                new Dimension(Game.DISP_W, Game.DISP_H / 10),
                ColorUtil.BLUE);
    }

    public Point getLocation() {
        return super.getLocation();
    }

    int width() {
        return getDimension().getWidth();
    }

    public int height() {
        return getDimension().getHeight();
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(ColorUtil.BLUE);
        g.drawRect( getLocation().getX(), getLocation().getY(),
                    getDimension().getWidth(), getDimension().getHeight());
    }
}
