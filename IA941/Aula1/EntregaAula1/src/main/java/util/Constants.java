/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class Constants {
    public static final int ZERO = 0;
    public static final double MINIMUM_DISTANCE = 30.0;
    public static final double CREATURE_VELOCITY = 2.00;
    public static final double CREATURE_MAX_FUEL = 1000.0;
    public static final double PFOOD_ENERGY = CREATURE_MAX_FUEL * 0.30;
    public static final double NPFOOD_ENERGY = CREATURE_MAX_FUEL * 0.15;
    public static final double PI = Math.PI;
    public static final double PI_2 = PI / 2.0;
    public static final String COLOR_RED = "Red";
    public static final String COLOR_GREEN = "Green";
    public static final String COLOR_BLUE = "Blue";
    public static final String COLOR_YELLOW = "Yellow";
    public static final String COLOR_MAGENTA = "Magenta";
    public static final String COLOR_WHITE = "White";
    public static final String COLOR_DARKGRAY_SPOILED = "DarkGray_Spoiled";
    public static final String COLOR_ORANGE = "Orange";
    public static final String UNDERSROCE = "_";
    public static final String PFOOD = "PFood";
    public static final String NPFOOD = "NPFood";
    public static final String JEWEL = "Jewel";
    
    public static final ArrayList<String> COLORS = new ArrayList(){
        {
            add(COLOR_RED);
            add(COLOR_GREEN);
            add(COLOR_BLUE);
            add(COLOR_YELLOW);
            add(COLOR_MAGENTA);
            add(COLOR_WHITE);
            add(COLOR_DARKGRAY_SPOILED);
            add(COLOR_ORANGE);
        }
    };
    
    public static int getJewelPayment(String color){
        
        int index = COLORS.indexOf(color);
        int payment = 0;
        switch (index) {
            case 0:
                payment = 10;       //colorRED
                break;
            case 1:
                payment = 8;        //colorGREEN
                break;
            case 2:
                payment = 6;        //colorBLUE
                break;
            case 3:
                payment = 4;        //colorYELLOW
                break;
            case 4:
                payment = 2;        //colorMAGENTA
                break;
            case 5:
                payment = 1;        //colorWHITE
                break;
        }
        return payment;
        
    }
}
