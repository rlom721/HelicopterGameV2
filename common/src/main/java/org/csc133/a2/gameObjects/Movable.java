package org.csc133.a2.gameObjects;

import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public abstract class Movable extends GameObject{

    public Movable(Point location, Dimension dimension, int color) {
        super(location, dimension, color);
    }

    public Movable() {

    }
}
