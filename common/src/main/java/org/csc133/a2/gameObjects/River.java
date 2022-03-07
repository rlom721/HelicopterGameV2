package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.Game;

import java.awt.*;

public class River extends Fixed {

    public River() {
        setDimension(new Dimension(Game.DISP_W, Game.DISP_H/10));
        setLocation(new Point(0, Game.DISP_H / 5));
        setColor(ColorUtil.BLUE);
    }

    public int width() { return getDimension().getWidth(); }

    public int height() { return getDimension().getHeight(); }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.drawRect( containerOrigin.getX() + getLocation().getX(),
                    containerOrigin.getY() + getLocation().getY(),
                    width(), height());
    }
}
