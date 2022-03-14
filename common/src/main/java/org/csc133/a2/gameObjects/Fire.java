package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

import java.util.Random;

import static com.codename1.ui.CN.*;

public class Fire extends Fixed{
    final private Point center;
    private int size;
    private FireState state;

//    public Fire(int size){
//
//    }

    public Fire(int size, Point location) {
        this.size = size;
        state = new UnStarted();
        setDimension(new Dimension(size, size));
        setLocation(location);
        setColor(ColorUtil.BLACK);
        this.center = new Point(location.getX() + size / 2,
                location.getY() + size / 2);
    }

    void setState(FireState fireState){ state = fireState; }

    // increase size without changing the center of the fire
    //
    public void grow() {
        Random rand = new Random();
        int increase = rand.nextInt(2);

        size += increase;
        setLocation(new Point(center.getX() - size/2, center.getY() - size/2));
    }

    void shrink(int reduce) {
        size -= reduce;
        if (size < 0) size = 0;

        setLocation(new Point(center.getX() - size/2, center.getY() - size/2));
    }

    Point getCenter() { return center; }

    public int size() { return size; }

    public void setSize(int size) { this.size = size; }

    public void start() {
        state.start(this);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_MEDIUM));
        g.setColor(ColorUtil.MAGENTA);

        if (size > 0) {
            g.fillArc(containerOrigin.getX() + getLocation().getX(),
                    containerOrigin.getY() + getLocation().getY(), size, size,
                    0, 360);
            g.drawString(Integer.toString(size),
                    containerOrigin.getX() + getLocation().getX() + size,
                    containerOrigin.getY() + getLocation().getY() + size);
        }
    }
}
