package input;

import enums.Keys;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Input implements KeyListener {

    public static List<Key> keys = new ArrayList<>();
    public static Stack<Character> alphaBet = new Stack<>();
    public Key up = new Key(Keys.UP);
    public Key down = new Key(Keys.DOWN);
    public Key left = new Key(Keys.LEFT);
    public Key right = new Key(Keys.RIGHT);
    public Key x = new Key(Keys.X);
    public Key c = new Key(Keys.C);
    public Key r = new Key(Keys.R);
    public Key enter = new Key(Keys.ENTER);
    public Key esc = new Key(Keys.ESC);
    public Key g = new Key(Keys.G);
    public Key b = new Key(Keys.B);

    @Override
    public void keyPressed(KeyEvent e) {
        if((e.getKeyCode() >= 65 && e.getKeyCode() <= 90) || ((e.getKeyCode() >= 97 && e.getKeyCode() <= 122))) {
            if(alphaBet.size() < 6) {
                alphaBet.push(e.getKeyChar());
            }
        }
        if(e.getKeyCode() == 8) {
            alphaBet.pop();
        }
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
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if(r.down) {
                r.toggle(false);
            }
            r.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_X) x.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_C) {
            if(c.down) {
                c.toggle(false);
            }
            c.toggle(pressed);
        }
        if(e.getKeyCode() == KeyEvent.VK_R) r.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_ENTER) enter.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) esc.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_G) g.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_B) b.toggle(pressed);
    }

    public void releaseAll() {
        for (Key key : keys) key.down = false;
    }

    public static boolean isAllReleased() {
        for (Key key : keys) {
            if(key.down) {
                return false;
            }
        }
        return true;
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