package org.csc133.a2.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.GameWorld;
import org.csc133.a2.gameObjects.GameObject;

public class MapView extends Container {
    GameWorld gw;

    public MapView(GameWorld gw){
        this.gw = gw;

        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.getAllStyles().setBgTransparency(255);
    }

    @Override
    public void laidOut(){
        gw.setDimension(new Dimension(this.getWidth(), this.getHeight()));
        gw.init();
    }

    @Override
    public void paint(Graphics g) {
        for (GameObject go: gw.getGameObjectCollection())
            go.draw(g, new Point(this.getX(), this.getY()));
    }
}
