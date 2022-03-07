package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

import java.util.Random;

public class Building extends Fixed {
    private int value;
    private int damage;

    public Building(Point location, Dimension dimension) {
        setColor(ColorUtil.rgb(255, 0, 0));
        setLocation(location);
        setDimension(dimension);
        damage = 0;
        value = getDimension().getWidth() * getDimension().getHeight();
    }

    public void setFireInBuilding(Fire fire){
        Random rand = new Random();
//        fire.setLocation(new Point( getLocation().getX() + rand.nextInt(dim),
//                                    getLocation().getY() ));
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
        g.drawString("D: " + damage,
                containerOrigin.getX() + getLocation().getX() + width(),
                containerOrigin.getY() + getLocation().getY() + height() + 30);
    }
}
