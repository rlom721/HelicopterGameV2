package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.Game;

// ----------------------------------------------------------------------------
//
public class Helipad extends Fixed{
//    final private Point location;
    final private Point center;
    final private int sSize;
    final private int cSize;
    final private int cOffset;

    public Helipad() {
        this.dimension = new Dimension(Game.DISP_H/8, Game.DISP_H/8);
        this.color = ColorUtil.GRAY;

        sSize = dimension.getWidth();
        cOffset = 40;
        cSize = sSize - cOffset;

        // located at half the screen width and its width above the bottom
        //
        this.location = new Point(  Game.DISP_W/2 - sSize/2,
                                    Game.DISP_H - (int)(sSize*1.5));

        center = new Point( location.getX() + sSize / 2,
                            location.getY() + sSize / 2);
    }

    public Point getCenter() {
        return center;
    }

    int circleDiameter() {
        return cSize;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(location.getX(), location.getY(), sSize, sSize, 5);
        g.drawArc(location.getX() + cOffset / 2, location.getY() + cOffset / 2,
                cSize, cSize, 0, 360);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        draw(g);
    }
}
