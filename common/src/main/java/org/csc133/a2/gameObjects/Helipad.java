package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.Game;

public class Helipad extends Fixed{
    final private Point center;
    final private int sSize;
    final private int cSize;
    final private int cOffset;

    public Helipad() {
        setDimension(new Dimension(Game.DISP_H/8, Game.DISP_H/8));
        setColor(ColorUtil.GRAY);

        sSize = getDimension().getWidth();
        cOffset = 40;
        cSize = sSize - cOffset;

        // located at half the screen width and its width above the bottom
        //
        setLocation(new Point(  Game.DISP_W/2 - sSize/2,
                                Game.DISP_H - (int)(sSize*1.5)));

        center = new Point( getLocation().getX() + sSize / 2,
                            getLocation().getY() + sSize / 2);
    }

    public Point getCenter() { return center; }

    int circleDiameter() { return cSize; }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.drawRect(getLocation().getX(), getLocation().getY(),
                sSize, sSize, 5);
        g.drawArc(  getLocation().getX() + cOffset / 2,
                getLocation().getY() + cOffset / 2,
                cSize, cSize, 0, 360);
    }
}
