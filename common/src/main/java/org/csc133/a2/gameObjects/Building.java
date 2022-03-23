package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import java.util.Random;

public class Building extends Fixed {
    private int value;
    private double damage;
    final private int MAX_FIRE_SIZE = 100;
    private Fires fires;

    public Building(Point location, Dimension dimension) {
        fires = new Fires();
        setColor(ColorUtil.rgb(255, 0, 0));
        setLocation(location);
        setDimension(dimension);
        damage = 0.0;
        value = (width() % 10) * 100;
    }

    public void setFireInBuilding(Fire fire){
        Random rand = new Random();
        fire.setSize(rand.nextInt(MAX_FIRE_SIZE));
        fire.setLocation(new Point( this.getLocation().getX()
                                        + rand.nextInt(width())-fire.size()/2,
                                    this.getLocation().getY()
                                     + rand.nextInt(height())-fire.size()/2));
        fires.add(fire);
        fire.start();
    }

    public int width(){ return getDimension().getWidth(); }

    public int height(){ return getDimension().getHeight(); }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.drawRect(containerOrigin.getX() + getLocation().getX(),
                   containerOrigin.getY() + getLocation().getY(),
                        width(), height(), 5);
        g.drawString("V:  " + value,
                    containerOrigin.getX() + getLocation().getX() + width(),
                    containerOrigin.getY() + getLocation().getY() + height());
        g.drawString("D: " + damage + "%",
                containerOrigin.getX() + getLocation().getX() + width(),
                containerOrigin.getY() + getLocation().getY() + height() + 30);
    }
}
