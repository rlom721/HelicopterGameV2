package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

import java.text.DecimalFormat;
import java.util.Random;

public class Building extends Fixed {
    private int value;
    final private int MAX_FIRE_DIAMETER = 26;
    final private int MIN_FIRE_DIAMETER = MAX_FIRE_DIAMETER-5;
    private int fireAreaBudget;
    private Fires fires;
    final private int area;

    public Building(Point location, Dimension dimension) {
        fires = new Fires();
        setColor(ColorUtil.rgb(255, 0, 0));
        setLocation(location);
        setDimension(dimension);
        value = (width() % 10) * 100;
        area = dimension.getHeight()*dimension.getWidth();
        fireAreaBudget = 1000;
    }

    public void setFireInBuilding(Fire fire){
        Random rand = new Random();
        fire.setDiameter(rand.nextInt(MAX_FIRE_DIAMETER-MIN_FIRE_DIAMETER)
                                        + MIN_FIRE_DIAMETER);
        fire.setLocation(new Point( this.getLocation().getX()
                                + rand.nextInt(width()) - fire.diameter()/2,
                                    this.getLocation().getY()
                                + rand.nextInt(height()) - fire.diameter()/2));
        fires.add(fire);
        fireAreaBudget -= fire.getArea();
        fire.start();
    }

    public double getTotalFireArea(){
        double totalFireArea = 0.0;
        for (Fire fire : fires.getGameObjects()) {
            if (fire.getState() instanceof Extinguished)
                totalFireArea += fire.getMaxSize();
            else
                totalFireArea += fire.getArea();
        }
        return totalFireArea;
    }

    public int getFireAreaBudget() { return fireAreaBudget; }

    public int width(){ return getDimension().getWidth(); }

    public int height(){ return getDimension().getHeight(); }

    public int getArea() { return area; }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        // damage is ratio of total fire area to building area
        //
        int damagePercent = (int)((getTotalFireArea()/area)*100);
        g.setColor(getColor());
        g.drawRect(containerOrigin.getX() + getLocation().getX(),
                   containerOrigin.getY() + getLocation().getY(),
                        width(), height(), 5);
        g.drawString("V:  " + value,
                    containerOrigin.getX() + getLocation().getX() + width(),
                    containerOrigin.getY() + getLocation().getY() + height());
        g.drawString("D: " + damagePercent + "%",
                containerOrigin.getX() + getLocation().getX() + width(),
                containerOrigin.getY() + getLocation().getY() + height() + 30);
    }
}
