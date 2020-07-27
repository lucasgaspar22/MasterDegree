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
public class GoToClosestDesiredJewel extends Codelet{

    private Memory closestDesiredJewelMO;
    private Memory selfInfoMO;
    private Memory legsMO;
    private int creatureBasicSpeed;
    private double reachDistance;

    public GoToClosestDesiredJewel(int creatureBasicSpeed, int reachDistance){
        this.creatureBasicSpeed = creatureBasicSpeed;
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        closestDesiredJewelMO = (MemoryObject) this.getInput("CLOSEST_JEWEL");
        selfInfoMO = (MemoryObject) this.getInput("INNER");
        legsMO = (MemoryObject) this.getOutput("LEGS");
    }

    @Override
    public void proc() {
        Thing closestDesiredJewel = (Thing) closestDesiredJewelMO.getI();
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) selfInfoMO.getI();

        if(closestDesiredJewel != null){
            double desiredJewelX = 0;
            double desiredJewelY = 0;

            try{
                desiredJewelX = closestDesiredJewel.getX1();
                desiredJewelY = closestDesiredJewel.getY1();
            }catch (Exception e){
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());
            }

            double creatureInnerSenseX = creatureInnerSense.position.getX();
            double creatureInnerSenseY = creatureInnerSense.position.getY();

            Point2D desiredJewelPoint = new Point();
            desiredJewelPoint.setLocation(desiredJewelX,desiredJewelY);

            Point2D creatureInnerSensePoint = new Point();
            creatureInnerSensePoint.setLocation(creatureInnerSenseX,creatureInnerSenseY);

            double distance = creatureInnerSensePoint.distance(desiredJewelPoint);
            JSONObject message = new JSONObject();

            try{
                if (distance > reachDistance){
                    message.put("ACTION", "GOTO");
                    message.put("X", (int) desiredJewelX);
                    message.put("Y", (int) desiredJewelY);
                    message.put("SPEED", creatureBasicSpeed);
                }else{
                    message.put("ACTION", "GOTO");
                    message.put("X", (int) desiredJewelX);
                    message.put("Y", (int) desiredJewelY);
                    message.put("SPEED", 0.0);
                }
                legsMO.setI(message.toString());
            }catch (JSONException e){
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());
            }
        }
    }
    
    @Override
    public void calculateActivation() {
    }
    
}
