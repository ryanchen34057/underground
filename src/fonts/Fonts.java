package fonts;

import java.awt.Font;

public class Fonts {
    public static Font getBitFont(int size, String path)  {
        Font font;
        if(path!= null){
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream(path));
            font = font.deriveFont(Font.BOLD, size);
            return font;
        } catch (Exception ex) {
            
        }
        }
        return new Font(Font.MONOSPACED, Font.BOLD, size);
    }
}
