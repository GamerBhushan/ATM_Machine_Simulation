package software.developer.bhushan.font;

import java.awt.*;

public class FontUtils {
    private static final String fontFamily = "Consolas";

    private static final int h1 = 30;
    private static int h2 = 24;
    private static int h3 = 18;


    public static final Font Heading_1_Plain = new Font(fontFamily,Font.PLAIN,h1);
    public static final Font Heading_1_Bold = new Font(fontFamily,Font.BOLD,h1);

    public static final Font Heading_2_Plain = new Font(fontFamily,Font.PLAIN,h2);
    public static final Font Heading_2_Bold = new Font(fontFamily,Font.BOLD,h2);

    public static final Font Heading_3_Plain = new Font(fontFamily,Font.PLAIN,h3);
    public static final Font Heading_3_Bold = new Font(fontFamily,Font.BOLD,h3);


}
