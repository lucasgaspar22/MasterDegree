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
public class GoToDeliverySpot extends Codelet {

    //private Memory closestDeliverySpotMO;
    private Memory selfInfoMO;
    private Memory legsMO;
    private int creatureBasicSpeed;
    private double reachDistance;

    public GoToDeliverySpot(int creatureBasicSpeed, int reachDistance) {
        this.creatureBasicSpeed = creatureBasicSpeed;
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        //closestDeliverySpotMO = (MemoryObject) this.getInput("CLOSEST_DELIVERYSPOT");
        selfInfoMO = (MemoryObject) this.getInput("INNER");
        legsMO = (MemoryObject) this.getOutput("LEGS");
    }

    @Override
    public void proc() {
        
        //Thing closestDeliverySpot = (Thing) closestDeliverySpotMO.getI();
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) selfInfoMO.getI();

        if(creatureInnerSense.isLeafletReady()){ // && closestDeliverySpot!=null

            double deliverySpotX = 0;
            double deliverySpotY = 0;

            /*try {
                deliverySpotX = closestDeliverySpot.getX1();
                deliverySpotY = closestDeliverySpot.getY1();
            } catch (Exception e) {
                e.printStackTrace();
            }*/


            double selfX = creatureInnerSense.position.getX();
            double selfY = creatureInnerSense.position.getY();

            Point2D pDeliverySpot = new Point();
            pDeliverySpot.setLocation(deliverySpotX, deliverySpotY);

            Point2D pSelf = new Point();
            pSelf.setLocation(selfX, selfY);

            double distance = pSelf.distance(pDeliverySpot);
            JSONObject message = new JSONObject();
            try {
                if (distance > reachDistance) { 
                    message.put("ACTION", "GOTO");
                    message.put("X", (int) deliverySpotX);
                    message.put("Y", (int) deliverySpotY);
                    message.put("SPEED", creatureBasicSpeed);

                } else {//Stop
                    message.put("ACTION", "GOTO");
                    message.put("X", (int) deliverySpotX);
                    message.put("Y", (int) deliverySpotY);
                    message.put("SPEED", 0.0);
                }
                legsMO.setI(message.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void calculateActivation() {

    }
}
