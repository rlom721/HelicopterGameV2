package org.csc133.a2.gameObjects;

import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public class GameObject {
    private Point location;
    private Dimension dimension;
    private int color;

    public GameObject(){

    }

    public Point getLocation(){
        return location;
    }

    public Dimension getDimension(){
        return dimension;
    }



    // change size for SOME classes
    void adjustSize(){

    }
}
