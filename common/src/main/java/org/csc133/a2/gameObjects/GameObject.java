package org.csc133.a2.gameObjects;

import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.interfaces.Drawable;

public abstract class GameObject implements Drawable {
    private Point location;
    private Dimension dimension;
    private int color;

    public void setLocation(Point location){ this.location = location; }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setColor(int color) { this.color = color; }

    public Point getLocation(){ return this.location; }

    public Dimension getDimension() { return this.dimension; }

    public int getColor() { return this.color; }
}
