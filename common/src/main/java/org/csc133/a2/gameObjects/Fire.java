package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.util.Random;

import static com.codename1.ui.CN.*;

// ----------------------------------------------------------------------------
//
public class Fire extends Fixed{
    private Point location;
    final private Point center;
    private int size;
    private int color;

    public Fire(int size, Point location) {

        this.size = size;
        this.location = location;
        this.center = new Point(location.getX() + size / 2,
                location.getY() + size / 2);
    }

    // increase size without changing the center of the fire
    //
    public void grow() {
        Random rand = new Random();
        int increase = rand.nextInt(2);

        size += increase;
        location = new Point(center.getX() - size / 2, center.getY() - size / 2);
    }

    void shrink(int reduce) {
        size -= reduce;
        if (size < 0) size = 0;

        location = new Point(center.getX() - size / 2, center.getY() - size / 2);
    }

    Point getCenter() {
        return center;
    }

    public int size() {
        return size;
    }

    public void draw(Graphics g) {
        g.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_MEDIUM));
        g.setColor(ColorUtil.MAGENTA);

        if (size > 0) {
            g.fillArc(location.getX(), location.getY(), size, size,
                    0, 360);
            g.drawString(Integer.toString(size),
                    location.getX() + size, location.getY() + size);
        }
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        draw(g);
    }
}
