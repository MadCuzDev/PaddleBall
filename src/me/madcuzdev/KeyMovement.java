package me.madcuzdev;

import java.util.Timer;
import java.util.TimerTask;

public class KeyMovement {

    public static void goLeftKey() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (PaddleBall.isKeyPressed())
                    PaddleBall.setPaddleX(PaddleBall.getPaddleX() - 5);
                else
                    timer.cancel();
            }
        }, 0, 10);
    }

    public static void goRightKey() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (PaddleBall.isKeyPressed())
                    PaddleBall.setPaddleX(PaddleBall.getPaddleX() + 5);
                else
                    timer.cancel();
            }
        }, 0, 10);
    }

}
