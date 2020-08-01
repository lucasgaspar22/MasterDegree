/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codelets.behaviors;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.json.JSONException;
import org.json.JSONObject;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import memory.CreatureInnerSense;
import ws3dproxy.model.Thing;
import ws3dproxy.model.WorldPoint;

/**
 *
 * @author lucas
 */
public class AvoidWall extends Codelet {

    private Memory closestWallMO;
    private Memory selfInfoMO;
    private Memory legsMO;
    private int creatureBasicSpeed;
    private double reachDistance;
    Thing closestWall;
    double wallX,wallY;

    public AvoidWall(int creatureBasicSpeed, int reachDistance) {
        this.creatureBasicSpeed = creatureBasicSpeed;
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        closestWallMO = (MemoryObject) this.getInput("CLOSEST_WALL");
        selfInfoMO = (MemoryObject) this.getInput("INNER");
        legsMO = (MemoryObject) this.getOutput("LEGS");
    }

    @Override
    public void proc() {
        closestWall = (Thing) closestWallMO.getI();
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) selfInfoMO.getI();
    
        if( closestWall != null){
            double distanceToWall = creatureInnerSense.creature.calculateDistanceTo(closestWall);
            JSONObject message = new JSONObject();

            try{
                if(distanceToWall <= reachDistance+30){
                    
                    creatureInnerSense.isDodging = true;
                    if (closestWall.getY1() < 10)  avoidWallFromLeft(closestWall, creatureInnerSense); 
                    else avoidWallFromRight(closestWall, creatureInnerSense);
                    
                    System.out.println("X> "+wallX +" Y> "+wallY);
                    message.put("ACTION", "GOTO");
                    message.put("X",  wallX);
                    message.put("Y",  wallY);
                    message.put("SPEED", creatureBasicSpeed);
                    legsMO.setI(message.toString());
                    
                }else{
                    checkIfAlreadyPassedWall(closestWall, creatureInnerSense);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void checkIfAlreadyPassedWall(Thing wall, CreatureInnerSense creatureInnerSense){
        if (wall.getY1() < 10){ //LEFT
            if(creatureInnerSense.position.getY() > wall.getY2() + 60) creatureInnerSense.isDodging=false;
        }else{ //RIGHT
            if(creatureInnerSense.position.getY() < wall.getY1() - 60) creatureInnerSense.isDodging=false;
        }
    }

    private void avoidWallFromLeft(Thing wall, CreatureInnerSense creatureInnerSense){
        if(!creatureInnerSense.isGoingUp){
            wallX = (creatureInnerSense.position.getY() > wall.getY2() + 30) ? wall.getX1()-50 : wall.getX2() + 60;
            wallY = wall.getY2() + 60;
        }else{
            wallX = (creatureInnerSense.position.getY() > wall.getY2() + 30) ?  wall.getX2()+ 60 : wall.getX1() - 50;
            wallY = wall.getY2() + 60;
        }            
    }

    private void avoidWallFromRight(Thing wall,CreatureInnerSense creatureInnerSense){
                    
        if(!creatureInnerSense.isGoingUp){
            wallX = (creatureInnerSense.position.getY() < wall.getY1() - 50) ? wall.getX1()-50 : wall.getX2() + 60;
            wallY = wall.getY1() - 60;
        }else{
            wallX = (creatureInnerSense.position.getY() < wall.getY1() - 50) ?  wall.getX2()+ 60 : wall.getX1() - 50 ;
            wallY = wall.getY1() - 60;
        }
    }

    @Override
    public void calculateActivation() {
    }

}
