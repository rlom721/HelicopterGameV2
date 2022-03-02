package org.csc133.a2.views;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a2.GameWorld;
import org.csc133.a2.gameObjects.GameObject;

public class MapView extends Container {
    GameWorld gw;

    public MapView(GameWorld gw){

    }

    // draw all objects in gameworld ralative to container object
    //
    public void draw(Graphics g) {
        for (GameObject go: gw.getGameObjectCollection())
            go.draw(g, new Point(this.getX(), this.getY()));
    }
}
