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
public class DeliverLeaflet extends Codelet {

    //private Memory closestDeliverySpotMO;
    private Memory selfInfoMO;
    private Memory handsMO;
    private double reachDistance;

    public DeliverLeaflet( int reachDistance) {
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        //closestDeliverySpotMO = (MemoryObject) this.getInput("CLOSEST_DELIVERYSPOT");
        selfInfoMO = (MemoryObject) this.getInput("INNER");
        handsMO = (MemoryObject) this.getOutput("HANDS");
    }

    @Override
    public void proc() {

        String leafletId="";
       //Thing closestDeliverySpot = (Thing) closestDeliverySpotMO.getI();
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) selfInfoMO.getI();

        if(creatureInnerSense.isLeafletReady()){ // && closestDeliverySpot!=null

            double deliverySpotX = 0;
            double deliverySpotY = 0;
            leafletId = creatureInnerSense.leaflet.getID().toString();

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
                if (distance < reachDistance ){
                    message.put("OBJECT", leafletId);
                    message.put("ACTION", "DELIVER");
                    //System.out.println(message.toString());
                    handsMO.setI(message.toString());
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
