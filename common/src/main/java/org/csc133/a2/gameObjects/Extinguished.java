package org.csc133.a2.gameObjects;

public class Extinguished extends FireState {
    private static Extinguished extinguished;

    private Extinguished() { }

    public static FireState getState(){
        if (extinguished == null)
            extinguished = new Extinguished();
        return extinguished;
    }

    @Override
    void setNextState(Fire fire) { }
}
