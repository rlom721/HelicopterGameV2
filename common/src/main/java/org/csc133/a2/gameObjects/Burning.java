package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;

public class Burning extends FireState {
    private static Burning burning;

    Burning() { }

    public static FireState getState(){
        if (burning == null)
            burning = new Burning();
        return burning;
    }

    @Override
    void start(Fire fire) {
        fire.setColor(ColorUtil.MAGENTA);
        fire.setState(new Burning());
    }
}
