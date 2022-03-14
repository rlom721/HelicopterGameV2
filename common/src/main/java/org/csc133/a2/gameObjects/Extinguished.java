package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;

public class Extinguished extends FireState {
    private static Extinguished extinguished;

    private Extinguished() { }

    public static FireState getState(){
        if (extinguished == null)
            extinguished = new Extinguished();
        return extinguished;
    }

    @Override
    void start(Fire fire) {
        fire.setColor(ColorUtil.BLACK);
        fire.setState(new Extinguished());
    }
}
