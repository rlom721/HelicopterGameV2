package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.Game;
import org.csc133.a2.interfaces.Steerable;

import static com.codename1.ui.CN.*;

// ----------------------------------------------------------------------------
//
public class Helicopter extends Movable implements Steerable {
    private Point location, center;
    final private int size;
//    final private int displayWidth, displayHeight;
    private int fuel, water;
    private int speed;
    private int heading;
    private final int headingRadius;
    final private int MAX_SPEED = 10;
    final private int MAX_WATER = 1000;
    private double angle;

    public Helicopter(Point helipadCenter, int initFuel) {
//        displayWidth = Game.DISP_W;
//        displayHeight = Game.DISP_H;
        fuel = initFuel;
        water = 0;
        speed = 0;
        heading = 0;
        angle = Math.toRadians(heading);
        size = Game.DISP_H / 40;
        headingRadius = size * 2;

        center = new Point(helipadCenter.getX(), helipadCenter.getY() + size);
        this.location = new Point(  center.getX() - size / 2,
                                    center.getY() - size / 2);
        this.color = ColorUtil.YELLOW;
//        this.dimension = new Dimension();
    }

    public void turnRight() {
        if (heading < 0 || heading > 360)
            heading = 360;
        heading -= 15;
    }

    public void turnLeft() {
        if (heading < 0 || heading > 360)
            heading = 0;
        heading += 15;
    }

    public void move() {
        center = new Point(center.getX() + (int) (speed * Math.cos(angle)),
                center.getY() - (int) (speed * Math.sin(angle)));
        location = new Point(center.getX() - size / 2, center.getY() - size / 2);
    }

    public void increaseSpeed() {
        if (speed != MAX_SPEED) speed++;
    }

    public void decreaseSpeed() {
        if (speed != 0) speed--;
    }

    public void drink() {
        if (speed < 3 && water < MAX_WATER)
            water += 100;
    }

    public void fight(Fire fire) {
        fire.shrink(water / 4);
    }

    public void dumpWater() {
        water = 0;
    }

    public void reduceFuel() {
        // fuel -= speed * speed + 5;
        fuel -= Math.sqrt(speed) + 5;
        if (fuel < 0) fuel = 0;
    }

    public int fuel() {
        return fuel;
    }

    public boolean isAboveRiver(River river) {
        int rX1 = river.getLocation().getX();
        int rX2 = river.getLocation().getX() + river.width();
        int rY1 = river.getLocation().getY();
        int rY2 = river.getLocation().getY() + river.height();

        return hasCollided(rX1, rX2, rY1, rY2);
    }

    public boolean isWithinRangeOfFire(Fire fire) {
        // fire bounding box values
        //
        int fX1 = fire.getLocation().getX() - 10;
        int fX2 = fire.getLocation().getX() + fire.size() + 10;
        int fY1 = fire.getLocation().getY() - 10;
        int fY2 = fire.getLocation().getY() + fire.size() + 10;

        // helicopter is within bounding box of fire
        //
        if (hasCollided(fX1, fX2, fY1, fY2)) {
            // small fire sizes only require heli to be within range of fire
            //
            if (fire.size() < 100)
                return true;
            else
                return isWithinCircle(fire.getCenter(), fire.size());
        }

        return false;
    }

    public boolean hasLandedOnHelipad(Helipad helipad) {
        return isWithinCircle(helipad.getCenter(), helipad.circleDiameter());
    }

    // checks for collision with other object (passes in bounding box values)
    // x1: left bound, x2: right bound, y1: upper bound, y2 lower bound
    //
    private boolean hasCollided(int x1, int x2, int y1, int y2) {
        // set helicopter bounding box values
        //
        int hX1 = location.getX();
        int hX2 = location.getX() + size;
        int hY1 = location.getY();
        int hY2 = location.getY() + size;

        // check bounding box conditions (these are true if NO collision)
        //
        return !(hX1 > x2) && !(hX2 < x1) && !(hY1 > y2) && !(hY2 < y1);
    }

    private boolean isWithinCircle(Point cCenter, int diameter) {
        int x = center.getX() - cCenter.getX();
        int y = center.getY() - cCenter.getY();

        // applies distance formula to see if helicopter is near a fire:
        // d = sqrt(a^2 + b^2)
        //
        return Math.sqrt(x * x + y * y) <= (double) (diameter / 2);
    }

    public void draw(Graphics g) {
        angle = Math.toRadians(heading) + Math.PI / 2;

        g.setColor(color);
        g.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_MEDIUM));

        // draw helicopter body and show stats
        //
        g.fillArc(location.getX(), location.getY(), size, size,
                0, 360);
        g.drawString("F:  " + fuel, location.getX() - size / 2,
                location.getY() + size * 2);
        g.drawString("W: " + water, location.getX() - size / 2,
                location.getY() + size * 3);

        // use polar to coordinate conversion for heading line position
        //
        int heX = center.getX() + (int) (headingRadius * Math.cos(angle));
        int heY = center.getY() - (int) (headingRadius * Math.sin(angle));

        g.drawLine(center.getX(), center.getY(), heX, heY);
    }

    @Override
    public void steerLeft() {
        this.turnLeft();
    }

    @Override
    public void steerRight() {
        this.turnRight();
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        draw(g);
    }
}
