package input;

import enums.Keys;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Input implements KeyListener {

    public static List<Key> keys = new ArrayList<>();

    public Key up = new Key(Keys.UP);
    public Key down = new Key(Keys.DOWN);
    public Key left = new Key(Keys.LEFT);
    public Key right = new Key(Keys.RIGHT);
    public Key x = new Key(Keys.X);
    public Key c = new Key(Keys.C);

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    private void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if(down.down) {
                down.toggle(false);
            }
            up.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(up.down) {
                up.toggle(false);
            }
            down.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(right.down) {
                right.toggle(false);
            }
            left.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(left.down) {
                left.toggle(false);
            }
            right.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_X) x.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_C) c.toggle(pressed);
    }

    public void releaseAll() {
        for (Key key : keys) key.down = false;
    }

    public class Key {
        public Keys name;
        public boolean down;

        public Key(Keys name) {
            this.name = name;
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != down) down = pressed;
        }

        @Override
        public String toString() {
            return down + " ";
        }
    }
}