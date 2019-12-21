package main.UI;

import java.awt.*;

public class ColorScheme extends Color {

    public static final ColorScheme gravel = new ColorScheme(200, 150, 100);
    public static final ColorScheme gravel2 = new ColorScheme(194, 140, 86);
    public static final ColorScheme jungle1 = new ColorScheme(136, 161, 73);
    public static final ColorScheme jungle2 = new ColorScheme(130, 156, 71);
    public static final ColorScheme grassColor = new ColorScheme(2, 172, 24);
    public static final ColorScheme chosenAnimalColor = new ColorScheme(227, 34, 36);
    public static final ColorScheme mostFrequentGenome = new ColorScheme(255, 108, 0);

    public ColorScheme(int r, int g, int b) {
        super(r, g, b);
    }

}
