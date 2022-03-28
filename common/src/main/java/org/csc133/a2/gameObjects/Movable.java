package org.csc133.a2.gameObjects;

public abstract class Movable extends GameObject{
    private int speed;
    private int heading;

    int speed() { return speed; }

    int heading() { return heading; }

    void setSpeed(int speed) { this.speed = speed; }

    void setHeading(int heading) { this.heading = heading; }

    public void move() {}
}
