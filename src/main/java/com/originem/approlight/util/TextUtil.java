package com.originem.approlight.util;

import java.awt.*;

public class TextUtil {
    public static Color getAlphaColor(Color sourceColor,float alpha){
        return new Color(sourceColor.getRed(),sourceColor.getGreen(),sourceColor.getBlue(),(int)(alpha*255+0.5));
    }
}
