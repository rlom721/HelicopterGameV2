package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a2.Game;

// ----------------------------------------------------------------------------
//
public class Helipad {
    final private Point location;
    final private Point center;
    final private int sSize;
    final private int cSize;
    final private int cOffset;
    private int displayWidth, displayHeight;

    public Helipad() {
        displayWidth = Game.DISP_W;
        displayHeight = Game.DISP_H;
        sSize = displayHeight / 8;
        cOffset = 40;
        cSize = sSize - cOffset;

        // located at half the screen width and its width above the bottom
        //
        location = new Point(displayWidth / 2 - sSize / 2,
                displayHeight - (int) (sSize * 1.5));
        center = new Point(location.getX() + sSize / 2,
                location.getY() + sSize / 2);
    }

    public Point getCenter() {
        return center;
    }

    public Point getLocation() {
        return location;
    }

    int circleDiameter() {
        return cSize;
    }

    public void draw(Graphics g) {
        g.setColor(ColorUtil.GRAY);
        g.drawRect(location.getX(), location.getY(), sSize, sSize, 5);
        g.drawArc(location.getX() + cOffset / 2, location.getY() + cOffset / 2,
                cSize, cSize, 0, 360);
    }
}
