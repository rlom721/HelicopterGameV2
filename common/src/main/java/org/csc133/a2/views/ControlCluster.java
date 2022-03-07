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

        Container westCommands = new Container(new BorderLayout());
        westCommands.add(BorderLayout.WEST, left);
        westCommands.add(BorderLayout.CENTER, right);
        westCommands.add(BorderLayout.EAST, fight);
        this.add(BorderLayout.WEST, westCommands);

        this.add(BorderLayout.CENTER, exit);

        Container eastCommands = new Container(new BorderLayout());
        eastCommands.add(BorderLayout.WEST, drink);
        eastCommands.add(BorderLayout.CENTER, brake);
        eastCommands.add(BorderLayout.EAST, accelerate);
        this.add(BorderLayout.EAST, eastCommands);
    }
}
