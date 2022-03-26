package org.csc133.a2.gameObjects;

import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

import java.util.Random;

import static com.codename1.ui.CN.*;

public class Fire extends Fixed{
    private Point center;
    private int diameter;
    private FireState state;
    private Building building;

    public Fire(Building building) {
        state = new UnStarted();
        this.building = building;
        this.building.setFireInBuilding(this);
        setDimension(new Dimension(diameter, diameter));
        this.center = new Point(getLocation().getX() + diameter / 2,
                                getLocation().getY() + diameter / 2);
    }

    void setState(FireState fireState){ state = fireState; }

    public void grow() {
        Random rand = new Random();
        int increase = rand.nextInt(2);

        // increase diameter without changing the center of the fire
        //
        diameter += increase;
        setLocation(new Point(  center.getX() - diameter/2,
                                center.getY() - diameter/2));
    }

    void shrink(int reduce) {
        diameter -= reduce;
        if (diameter < 0) diameter = 0;
        setLocation(new Point(  center.getX() - diameter/2,
                                center.getY() - diameter/2));
    }

    public int getArea() {
        int radius = diameter/2;
        return (int)(Math.PI * radius * radius);
    }

    Point getCenter() { return center; }

    public int diameter() { return diameter; }

    public void setDiameter(int diameter) { this.diameter = diameter; }

    public void start() { state.start(this); }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_MEDIUM));
        g.setColor(getColor());

        if (diameter > 0) {
            g.fillArc(containerOrigin.getX() + getLocation().getX(),
                    containerOrigin.getY() + getLocation().getY(),
                    diameter, diameter, 0, 360);
            g.drawString(Integer.toString(diameter),
                    containerOrigin.getX() + getLocation().getX() + diameter,
                    containerOrigin.getY() + getLocation().getY() + diameter);
        }
    }
}