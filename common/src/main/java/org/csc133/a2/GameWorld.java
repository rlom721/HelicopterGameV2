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
    final int INITIAL_FUEL;
    private ArrayList<GameObject> go;

    private enum Result {LOST, WON};

    public GameWorld(){
        INITIAL_FUEL = 25000;
        init();
    }

    public void init(){
        river = new River();
        helipad = new Helipad();
        helicopter = new Helicopter(helipad.getCenter(), INITIAL_FUEL);
        go = new ArrayList<>();
        go.add(river);
        go.add(helipad);

        go.add(addBuildingAboveRiver());
        go.add(addBuildingBelowLeftRiver());
        go.add(addBuildingBelowRightRiver());


        /*
        go.add(addFireAboveLeftRiver());
        go.add(addFireAboveRightRiver());
        go.add(addFireBelowCenterRiver());
        */

        go.add(helicopter);
    }

    void placeFiresInBuilding(){
        Fire f1 = new Fire(0, new Point(0, 0));
        Fire f2 = new Fire(0, new Point(0, 0));
        Fire f3 = new Fire(0, new Point(0, 0));

        for (GameObject go : getGameObjectCollection()){
            if (go instanceof Building){
                Building currentBuilding = (Building)go;
                (currentBuilding).setFireInBuilding(f1);
                (currentBuilding).setFireInBuilding(f2);
                (currentBuilding).setFireInBuilding(f3);
            }
        }
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
                turnLeft();
                break;
            case -94:
                turnRight();
                break;
            case -91:
                accelerate();
                break;
            case -92:
                brake();
                break;
            case 'f':
                fight();
                break;
            case 'd':
                drink();
                break;
        }
    }

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public void accelerate() {
        helicopter.increaseSpeed();
    }

    public void brake() {
        helicopter.decreaseSpeed();
    }

    public void drink() {
        if(helicopter.isAboveRiver(river))  // move to drink method?
            helicopter.drink();
    }

    public void exit() {
        quit();
    }

    public void fight() {
        fightFiresIfHeliIsNear();
    }

    public void turnLeft() {
        helicopter.steerLeft();
    }

    public void turnRight() {
        helicopter.steerRight();
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
            if (helicopter.isWithinRangeOfFire(fire))
                helicopter.fight(fire);

            if (fire.size() == 0)
                deadFires.add(fire);
            }
        }
        helicopter.dumpWater();
        go.removeAll(deadFires);
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
        return true;
    }

    public String getHeading() {
        return Integer.toString(helicopter.getHeading());
    }

    public String getSpeed() {
        return Integer.toString(helicopter.getSpeed());
    }

    public String getFuel() {
        return Integer.toString(helicopter.fuel());
    }

    private Building addBuildingAboveRiver(){
        Point bLocation = new Point(Game.DISP_W/6, Game.DISP_H/20);
        Dimension bDimension = new Dimension((int)(Game.DISP_W/1.5),
                Game.DISP_H/10);
        return new Building(bLocation, bDimension);
    }

    private Building addBuildingBelowLeftRiver(){
        int riverLowerBound = river.getLocation().getY() + river.height();
        Point bLocation = new Point(  Game.DISP_W/12,
                riverLowerBound + Game.DISP_H/10);
        Dimension bDimension = new Dimension(Game.DISP_W/8,
                                    (int)(Game.DISP_H/3));
        return new Building(bLocation, bDimension);
    }

    private Building addBuildingBelowRightRiver(){
        int riverLowerBound = river.getLocation().getY() + river.height();
        Point bLocation = new Point( (int)(Game.DISP_W/1.25),
                riverLowerBound + Game.DISP_H/6);
        Dimension bDimension = new Dimension(Game.DISP_W/10, Game.DISP_H/4);
        return new Building(bLocation, bDimension);
    }

    private Fire addFireAboveRightRiver(){
        int fSize = getRand(10, 500);
        Point fLocation = new Point(getRand(Game.DISP_W/2,
                Game.DISP_W-fSize),
                getRand(0, river.getLocation().getY()));
        return new Fire(fSize, fLocation);
    }

    private Fire addFireBelowCenterRiver() {
        int fSize = getRand(10, 500);
        int riverLowerBound = river.getLocation().getY() + river.height();
        int aboveHelipad = helipad.getLocation().getY() - fSize;
        Point fLocation = new Point(getRand(Game.DISP_W/2 - fSize,
                Game.DISP_W/2 + fSize),
                getRand(riverLowerBound, aboveHelipad));
        return new Fire(fSize, fLocation);
    }

    private Fire addFireAboveLeftRiver() {
        int fSize = getRand(10, 500);
        Point fLocation = new Point(getRand(Game.DISP_W/4,
                Game.DISP_W/2-fSize),
                getRand(0, river.getLocation().getY()));
        return new Fire(fSize, fLocation);
    }

    // generates random number, lower is inclusive, upper is exclusive
    //
    int getRand(int lower, int upper){
        Random rand = new Random();
        return rand.nextInt(upper-lower) + lower;
    }
}
