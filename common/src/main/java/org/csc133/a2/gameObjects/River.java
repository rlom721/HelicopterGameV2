package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a2.Game;

// ----------------------------------------------------------------------------
//
public class River {
    final private Point location;
    final private int width;
    final private int height;

    public River() {
        width = Game.DISP_W;
        height = Game.DISP_H / 10;
        location = new Point(0, Game.DISP_H / 5);
    }

    public Point getLocation() {
        return location;
    }

    int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void draw(Graphics g) {
        g.setColor(ColorUtil.BLUE);
        g.drawRect(location.getX(), location.getY(), width, height);
    }
}
