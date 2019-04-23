package fonts;

import java.awt.Font;

public class Fonts {
    public static Font getBitFont(int size)  {
        Font font;
         try {
            font = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/res/Endless Boss Battle.ttf"));
            font = font.deriveFont(Font.BOLD, size);
            return font;
        } catch (Exception ex) {
            
        }
        return new Font(Font.MONOSPACED, Font.BOLD, size);
    }
}
