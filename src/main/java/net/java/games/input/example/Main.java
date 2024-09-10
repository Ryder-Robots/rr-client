/*
 * PS4 controller functions
 *
 */

package net.java.games.input.example;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Controller gamepad = null;

        /* Get the available controllers */
        Controller[] controllers = ControllerEnvironment
                .getDefaultEnvironment().getControllers();
        if (controllers.length == 0) {
            System.out.println("Found no controllers.");
            System.exit(0);
        }

        for (int i = 0; i < controllers.length; i++) {
            if ("Wireless Controller".equals(controllers[i].getName()) &&
                    "Gamepad".equals(controllers[i].getType().toString())
            ) {
                System.out.print("Found gamepad!\n");
                gamepad = controllers[i];
                break;
            }
        }

        //boolean run = true;
        for (int i = 0;i < 300; i++) {
            gamepad.poll();
            EventQueue queue = gamepad.getEventQueue();
            Event event = new Event();
            while(queue.getNextEvent(event)) {
                System.out.println(event.getValue());
            }

            for (Component component : gamepad.getComponents()) {
                if (component.isAnalog()) {
                    System.out.println("component:" + component.getIdentifier() +
                        " current value:" + component.getPollData()
                    );
                }
            }
            //run = false;
        }
    }
}