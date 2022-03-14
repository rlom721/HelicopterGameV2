package org.csc133.a2.gameObjects;

public class Burning extends FireState {
    private static Burning burning;

    private Burning() { }

    public static FireState getState(){
        if (burning == null)
            burning = new Burning();
        return burning;
    }
}
