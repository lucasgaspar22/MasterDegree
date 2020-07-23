package util;

public class Landmarks {
    
    private int[] xPos = {300,100,100,500,550,700,550,750,750, 450};
    private int[] yPos = {100,100,320,320,480,550,480,420, 50, 100};
    private int curLandmark=0;
    private boolean up = true;
    
    
    public int getNextX(){
        return xPos[curLandmark];
    }
    
    public int getNextY(){
        return yPos[curLandmark];
    }
    
    public void incLandMark(){
        if(up){
            curLandmark++;
            if(curLandmark == 9) up = false;
        }else{
            curLandmark--;
            if(curLandmark == 0) up = true;
        }
    }
    
}