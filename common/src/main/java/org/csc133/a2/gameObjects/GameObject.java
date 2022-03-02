package org.csc133.a2.gameObjects;

import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.interfaces.Drawable;

public abstract class GameObject implements Drawable {
    private Point location;
    private Dimension dimension;
    private int color;

    // public GameObject(){    }

    public GameObject(Point location, Dimension dimension, int color){
        this.location = location;
        this.dimension = dimension;
        this.color = color;
    }

    // TEMP FIX
    public GameObject() {

    }

    public Point getLocation(){
        return location;
    }

    public Dimension getDimension(){
        return dimension;
    }

    public int getColor() { return color; }

    // change size for SOME classes
    public void adjustSize(){

    }
}
