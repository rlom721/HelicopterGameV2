package org.csc133.a2.gameObjects;

import com.codename1.charts.util.ColorUtil;

public class UnStarted extends FireState {
    private static UnStarted unStarted;

    UnStarted() { }

    public static FireState getState(){
        if (unStarted == null)
            unStarted = new UnStarted();
        return unStarted;
    }

    @Override
    void setNextState(Fire fire) {
        fire.setColor(ColorUtil.MAGENTA);
        fire.setFireState(Burning.getState());
    }
}
