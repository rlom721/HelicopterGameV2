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

    public River() {
        this.dimension = new Dimension(Game.DISP_W, Game.DISP_H/10);
        this.location = new Point(0, Game.DISP_H / 5);
        this.color = ColorUtil.BLUE;
    }

//    public Point getLocation() {
//        return super.getLocation();
//    }

    int width() {
        return dimension.getWidth();
    }

    public int height() {
        return dimension.getHeight();
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect( location.getX(), location.getY(),
                    dimension.getWidth(), dimension.getHeight());
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        draw(g);
    }
}
