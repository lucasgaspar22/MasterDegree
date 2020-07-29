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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import memory.CreatureInnerSense;
import ws3dproxy.model.Thing;

/**
 *
 * @author lucas
 */
public class GetClosestDesiredJewel extends Codelet {

    private Memory closestJewelMO;
    private Memory innerSenseMO;
    private Memory knownJewelsMO;
    private Memory handsMO;
    private int reachDistance;
    private Thing closestDesiredJewel;
    private CreatureInnerSense creatureInnerSense;
    private List<Thing> knownJewels;

    public GetClosestDesiredJewel(int reachDistance) {
        setTimeStep(50);
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        closestJewelMO = (MemoryObject) this.getInput("CLOSEST_JEWEL");
        innerSenseMO = (MemoryObject) this.getInput("INNER");
        handsMO = (MemoryObject) this.getOutput("HANDS");
        knownJewelsMO = (MemoryObject) this.getOutput("KNOWN_JEWELS");
    }

    @Override
    public void proc() {
        String desiredJewelName = "";
        closestDesiredJewel = (Thing) closestJewelMO.getI();
        creatureInnerSense = (CreatureInnerSense) innerSenseMO.getI();
        knownJewels = (List<Thing>) knownJewelsMO.getI();

        if (closestDesiredJewel != null
          && creatureInnerSense.isJewelDesired(closestDesiredJewel.getMaterial().getColorName())) {
            double desiredJewelX = 0;
            double desiredJewelY = 0;

            try {
                desiredJewelX = closestDesiredJewel.getX1();
                desiredJewelY = closestDesiredJewel.getY1();
                desiredJewelName = closestDesiredJewel.getName();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());
            }

            double creatureInnerSenseX = creatureInnerSense.position.getX();
            double creatureInnerSenseY = creatureInnerSense.position.getY();

            Point2D desiredJewelPoint = new Point();
            desiredJewelPoint.setLocation(desiredJewelX, desiredJewelY);

            Point2D creatureInnerSensePoint = new Point();
            creatureInnerSensePoint.setLocation(creatureInnerSenseX, creatureInnerSenseY);

            double distance = creatureInnerSensePoint.distance(desiredJewelPoint);
            JSONObject message = new JSONObject();

            try {
                if (distance < reachDistance +20 ){//&& creatureInnerSense.isJewelDesired(closestDesiredJewel.getMaterial().getColorName())) {
                    message.put("OBJECT", desiredJewelName);
                    message.put("ACTION", "PICKUP");
                    //System.out.println(message.toString());
                    handsMO.setI(message.toString());
                    DestroyClosestDesiredJewel();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());

            }
        }
    }

    @Override
    public void calculateActivation() {
    }

    public void DestroyClosestDesiredJewel() {
        int auxIndex = -1;
        int index = 0;
        synchronized (knownJewels) {
            CopyOnWriteArrayList<Thing> myknown = new CopyOnWriteArrayList<>(knownJewels);
            for (Thing thing : knownJewels) {
                if (closestDesiredJewel != null) {
                    if (thing.equals(closestDesiredJewel)) {
                        auxIndex = index;
                    }
                }
                index++;
            }
            if (auxIndex != -1) {
                knownJewels.remove(auxIndex);
                //closestJewelMO.setI(null);
            }
            closestDesiredJewel = null;
        }
    }

}
