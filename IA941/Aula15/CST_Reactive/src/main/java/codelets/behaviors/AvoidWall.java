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
    
        if( closestWall != null ){
            double distanceToWall = creatureInnerSense.creature.calculateDistanceTo(closestWall);
            JSONObject message = new JSONObject();

            try{
                if(distanceToWall <= reachDistance){
                    if (closestWall.getY1() < 10)  legsMO.setI(avoidWallFromLeft(closestWall, creatureInnerSense)); 
                    else legsMO.setI(avoidWallFromRight(closestWall, creatureInnerSense));
                }else{
                    message.put("ACTION", "GOTO");
                    message.put("X",  creatureInnerSense.position.getX());
                    message.put("Y",  creatureInnerSense.position.getY());
                    message.put("SPEED", 0.0);
                    legsMO.setI(message.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String avoidWallFromLeft(Thing wall, CreatureInnerSense creatureInnerSense){
        double wallX,wallY;
        JSONObject message = new JSONObject();

        if(!creatureInnerSense.isGoingUp){
            wallX = (creatureInnerSense.position.getY() > wall.getY2() + 30) ? wall.getX1()-50 : wall.getX2() + 60;
            wallY = wall.getY2() + 60;
        }else{
            wallX = (creatureInnerSense.position.getY() > wall.getY2() + 30) ?  wall.getX2()+ 60 : wall.getX1() - 50;
            wallY = wall.getY2() + 60;
        }

        message.put("ACTION", "GOTO");
        message.put("X",  wallX);
        message.put("Y",  wallY);
        message.put("SPEED", creatureBasicSpeed);
        return message.toString();
    }

    private String avoidWallFromRight(Thing wall,CreatureInnerSense creatureInnerSense){
        double wallX,wallY;
        JSONObject message = new JSONObject();

        if(!creatureInnerSense.isGoingUp){
            wallX = (creatureInnerSense.position.getY() < wall.getY1() - 50) ? wall.getX1()-50 : wall.getX2() + 60;
            wallY = wall.getY1() - 60;
        }else{
            wallX = (creatureInnerSense.position.getY() < wall.getY1() - 50) ?  wall.getX2()+ 60 : wall.getX1() - 50 ;
            wallY = wall.getY1() - 60;
        }

        message.put("ACTION", "GOTO");
        message.put("X",  wallX);
        message.put("Y",  wallY);
        message.put("SPEED", creatureBasicSpeed);
        return message.toString();
    }

    @Override
    public void calculateActivation() {
    }

}
