package org.csc133.a2;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a2.gameObjects.*;

import java.util.ArrayList;
import java.util.Random;

// ----------------------------------------------------------------------------
// Holds state of game, determines win/lose conditions and links Game objects.
//
public class GameWorld{
    private Dimension worldSize;
    private River river;
    private Helipad helipad;
    private Helicopter helicopter;
    private ArrayList<Fire> fires;
    private int displayWidth, displayHeight;
    final int INITIAL_FUEL;
    private ArrayList<GameObject> go;

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    private enum Result {LOST, WON};

    public GameWorld(){
        displayWidth = Game.DISP_W;
        displayHeight = Game.DISP_H;
        INITIAL_FUEL = 25000;

        init();
    }

    public void init(){
//        INITIAL_FUEL = 25000;
        river = new River();
        helipad = new Helipad();
        helicopter = new Helicopter(helipad.getCenter(), INITIAL_FUEL);
//        fires = new ArrayList<>();
        go = new ArrayList<>();
        go.add(river);
        go.add(helipad);

        go.add(addFireAboveLeftRiver());
        go.add(addFireAboveRightRiver());
        go.add(addFireBelowCenterRiver());
        go.add(helicopter);
    }

    public void tick(){
        helicopter.move();
        helicopter.reduceFuel();
        randomlyGrowFires();
        endGame();
    }

    void processKeyPress(int keyCode){
        switch(keyCode){
            case -93:
                helicopter.turnLeft();
                break;
            case -94:
                helicopter.turnRight();
                break;
            case -91:
                helicopter.increaseSpeed();
                break;
            case -92:
                helicopter.decreaseSpeed();
                break;
            case 'f':
                fightFiresIfHeliIsNear();
                break;
            case 'd':
                if(helicopter.isAboveRiver(river))  // move to drink method?
                    helicopter.drink();
                break;
        }
    }

    public ArrayList<GameObject> getGameObjectCollection() {
        return go;
    }

    private void endGame() {
        if(helicopter.fuel() <= 0)
            gameOver(Result.LOST);
        else if(helicopter.hasLandedOnHelipad(helipad) && allFiresAreOut())
            gameOver(Result.WON);
    }

    private void randomlyGrowFires() {
        for(GameObject go : getGameObjectCollection()) {
            if (go instanceof Fire) {
                if (getRand(0, 7) == 0) {
//                    int randomFire = getRand(0, fires.size());
//                    if (fires.get(randomFire).size() > 0)
                    ((Fire)go).grow();
                }
            }
        }
    }

    private void fightFiresIfHeliIsNear() {
        ArrayList<Fire> deadFires = new ArrayList<>();
        for(GameObject go : getGameObjectCollection()) {
            if (go instanceof Fire) {
                Fire fire = (Fire)go;
//        for(Fire fire : fires) {
            if (helicopter.isWithinRangeOfFire(fire))
                helicopter.fight(fire);

            if (fire.size() == 0)
                deadFires.add(fire);
//            }
            }
        }
        helicopter.dumpWater();
        go.removeAll(deadFires);
//        fires.removeAll(deadFires);
    }

    void gameOver(Result result){
        boolean replayGame = Dialog.show("Game Over", replayPrompt(result),
                "Heck Yeah!", "Some Other Time");

        if(replayGame)
            init(); //new Game();
        else
            quit();
    }

    private String replayPrompt(Result result) {
        String dialogMsg = "";

        if(result == Result.LOST){
            dialogMsg = "You ran out of fuel :(\nPlay Again?";
        }
        else if(result == Result.WON){
            dialogMsg = "You won!" + "\nScore: " + helicopter.fuel()
                    + "\nPlay Again?";
        }

        return dialogMsg;
    }

    void quit(){
        Display.getInstance().exitApplication();
    }

    boolean allFiresAreOut(){
        for (GameObject go : getGameObjectCollection()){
            if (go instanceof Fire)
                return false;
        }
//        return fires.size() == 0;
        return true;
    }

//    private void addFiresToRandomLocations(){
//        addFireAboveRightRiver();
//        addFireAboveLeftRiver();
//        addFireBelowCenterRiver();
//    }

    private Fire addFireAboveRightRiver(){
        int fSize = getRand(10, 500);
        Point fLocation = new Point(getRand(displayWidth/2,
                displayWidth-fSize),
                getRand(0, river.getLocation().getY()));
//        fires.add(new Fire(fSize, fLocation));
        return new Fire(fSize, fLocation);
    }

    private Fire addFireBelowCenterRiver() {
        int fSize = getRand(10, 500);
        int riverLowerBound = river.getLocation().getY() + river.height();
        int aboveHelipad = helipad.getLocation().getY() - fSize;
        Point fLocation = new Point(getRand(displayWidth/2 - fSize,
                displayWidth/2 + fSize),
                getRand(riverLowerBound, aboveHelipad));
//        fires.add(new Fire(fSize, fLocation));
        return new Fire(fSize, fLocation);
    }

    private Fire addFireAboveLeftRiver() {
        int fSize = getRand(10, 500);
        Point fLocation = new Point(getRand(displayWidth/4,
                displayWidth/2-fSize),
                getRand(0, river.getLocation().getY()));
//        fires.add(new Fire(fSize, fLocation));
        return new Fire(fSize, fLocation);
    }

    // generates random number, lower is inclusive, upper is exclusive
    //
    int getRand(int lower, int upper){
        Random rand = new Random();
        return rand.nextInt(upper-lower) + lower;
    }

    /*
    void draw(Graphics g){
        // call MapView here?
        g.clearRect(0, 0, displayWidth, displayHeight);
        river.draw(g);
        helipad.draw(g);
        for(Fire fire : fires)
            fire.draw(g);
        helicopter.draw(g);
    }
    */
}
