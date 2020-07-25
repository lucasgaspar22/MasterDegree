package util;

public class Landmarks {
    
    private int[] xPos = {0,800};
    private int[] yPos = {0,600};
    private int curLandmark=0;
    private boolean up = true;
    
    
    public int getX(){
        return xPos[curLandmark];
    }
    
    public int getY(){
        return yPos[curLandmark];
    }
    
    public void incLandMark(){
        if(up){
            curLandmark++;
            if(curLandmark == 1) up = false;
        }else{
            curLandmark--;
            if(curLandmark == 0) up = true;
        }
    }
    
    public int getLandMarkDirection(){
        if (curLandmark == 0 ) return 0; // DOWN
        else return 1; // UP
    }
    
}