package org.csc133.a2;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
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
//    private Fires fires;
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
        placeFiresInBuilding();
        go.add(helicopter);
    }

    void placeFiresInBuilding(){
        ArrayList<Fire> tempFires = new ArrayList<>();
        for (GameObject go : getGameObjectCollection()){
            if (go instanceof Building){
                Building currentBuilding = (Building)go;
                while (currentBuilding.getFireAreaBudget() > 0) {
                    Fire fire = new Fire(currentBuilding);
                    tempFires.add(fire);
                    // REFACTOR: access fires thru building class ? allowed ?
                }
            }
        }

        for (Fire fire : tempFires) {
            getGameObjectCollection().add(fire);
        }
    }

    public int getTotalFireSize(){
        int sizeFires = 0;
        for (GameObject go : getGameObjectCollection()){
            if (go instanceof Fire)
                sizeFires++;
        }
        return sizeFires;
    }

    public void tick(){
        helicopter.move();
        helicopter.reduceFuel();
        randomlyGrowFires();
        endGame();
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
        Random rand = new Random();
        for(GameObject go : getGameObjectCollection()) {
            if (go instanceof Fire) {
                if (rand.nextInt(15) == 0)
                    ((Fire)go).grow();
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

            if (fire.diameter() == 0)
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
}
