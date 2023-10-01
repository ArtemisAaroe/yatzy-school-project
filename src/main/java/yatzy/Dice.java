package yatzy;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Dice implements Comparable<Dice>{
    Random random = new Random();
    private int face = 0;

    private Rectangle rectangle;

    private Button button;

    private boolean markedForRole = true;

    public Dice(Rectangle rectangle, Button button) {
        this.rectangle = rectangle;
        this.button = button;
    }

    public void roleDie() {
        face = random.nextInt(6) + 1; // (0, 5) + 1
        button.setText(toString());
        markedForRoleFalse();
    }

    public void zeroDie() {
        face = 0;
        button.setText(toString());
    }

    public void markedForRoleTrue() {
        markedForRole = true;
        rectangle.setOpacity(1);
    }

    public void markedForRoleFalse() {
        markedForRole = false;
        rectangle.setOpacity(0);
    }

    public void markedForRoleSwitch() {
        if (markedForRole) {
            markedForRoleFalse();
        } else {
            markedForRoleTrue();
        }
    }

    public int getFace() {
        return face;
    }

    public boolean getMarkedForRole() {
        return markedForRole;
    }

    @Override
    public String toString() {
        if (face == 0) {
            return "";
        }
        return "" + getFace();
    }

    @Override
    public int compareTo(Dice die) {
        if (this.face == 0 || die.face == 0) {
            throw new IllegalStateException("Can not compare dice without value");
        }
        return die.face - this.face;
    }
}
