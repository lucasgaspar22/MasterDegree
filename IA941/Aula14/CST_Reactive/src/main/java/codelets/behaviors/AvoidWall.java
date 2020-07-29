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
                    message.put("ACTION", "FORAGE");
                    legsMO.setI(message.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }    
    }

    @Override
    public void calculateActivation() {
    }

}
