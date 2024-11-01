package org.csc133.a2.gameObjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Fires extends GameObjectCollection<Fire> {

    public Fires(){
        super();
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        for (Fire fire : getGameObjects())
            fire.draw(g, containerOrigin);
    }
}
