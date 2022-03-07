package org.csc133.a2.views;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import org.csc133.a2.GameWorld;
import org.csc133.a2.commands.*;

import javax.swing.border.Border;

public class ControlCluster extends Container {
    GameWorld gw;

    public ControlCluster(GameWorld gw){
        this.gw = gw;
        this.setLayout(new BorderLayout());

        Button accelerate = new Button("Accelerate");
        Button brake = new Button("Brake");
        Button drink = new Button("Drink");
        Button exit = new Button("Exit");
        Button fight = new Button("Fight");
        Button left = new Button("Left");
        Button right = new Button("Right");

        accelerate.setCommand(new AccelerateCommand(gw));
        brake.setCommand(new BrakeCommand(gw));
        drink.setCommand(new DrinkCommand(gw));
        exit.setCommand(new ExitCommand(gw));
        fight.setCommand(new FightCommand(gw));
        left.setCommand(new TurnLeftCommand(gw));
        right.setCommand(new TurnRightCommand(gw));

        this.add(BorderLayout.WEST, left);
        this.add(BorderLayout.WEST, right);
        this.add(BorderLayout.WEST, fight);
        this.add(BorderLayout.CENTER, exit);
        this.add(BorderLayout.EAST, drink);
        this.add(BorderLayout.EAST, brake);
        this.add(BorderLayout.EAST, accelerate);
    }
}
