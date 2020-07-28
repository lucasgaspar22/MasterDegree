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
public class HideClosestUndesiredJewel extends Codelet {

    private Memory closestUndesiredJewelMO;
    private Memory innerSenseMO;
    private Memory knownJewelsMO;
    private Memory handsMO;
    private int reachDistance;
    private Thing closestUndesiredJewel;
    private CreatureInnerSense creatureInnerSense;
    private List<Thing> knownJewels;

    public HideClosestUndesiredJewel(int reachDistance) {
        setTimeStep(100);
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        closestUndesiredJewelMO = (MemoryObject) this.getInput("CLOSEST_JEWEL");
        innerSenseMO = (MemoryObject) this.getInput("INNER");
        handsMO = (MemoryObject) this.getOutput("HANDS");
        knownJewelsMO = (MemoryObject) this.getOutput("KNOWN_JEWELS");
    }

    @Override
    public void proc() {
        String undesiredJewelName = "";
        closestUndesiredJewel = (Thing) closestUndesiredJewelMO.getI();
        creatureInnerSense = (CreatureInnerSense) innerSenseMO.getI();
        knownJewels = (List<Thing>) knownJewelsMO.getI();

        if (closestUndesiredJewel != null
            && !creatureInnerSense.isJewelDesired(closestUndesiredJewel.getMaterial().getColorName())) {
            double undesiredJewelX = 0;
            double undesiredJewelY = 0;

            try {
                undesiredJewelX = closestUndesiredJewel.getX1();
                undesiredJewelY = closestUndesiredJewel.getY1();
                undesiredJewelName = closestUndesiredJewel.getName();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());
            }

            double creatureInnerSenseX = creatureInnerSense.position.getX();
            double creatureInnerSenseY = creatureInnerSense.position.getY();

            Point2D undesiredJewelPoint = new Point();
            undesiredJewelPoint.setLocation(undesiredJewelX, undesiredJewelY);

            Point2D creatureInnerSensePoint = new Point();
            creatureInnerSensePoint.setLocation(creatureInnerSenseX, creatureInnerSenseY);

            double distance = creatureInnerSensePoint.distance(undesiredJewelPoint);
            JSONObject message = new JSONObject();

            try {
                if (distance < reachDistance + 20) {
                    message.put("OBJECT", undesiredJewelName);
                    message.put("ACTION", "BURY");
                    handsMO.setI(message.toString());
                    DestroyClosestUndesiredJewel();
                } else {
                    handsMO.setI("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());

            }
        }
        else {
            handsMO.setI("");
        }
    }

    @Override
    public void calculateActivation() {
    }

    public void DestroyClosestUndesiredJewel() {
        int curIndex = -1;
        int jewelIndex = 0;
        synchronized (knownJewels) {
            CopyOnWriteArrayList<Thing> myKnownJewels = new CopyOnWriteArrayList<>(knownJewels);
            for (Thing thing : knownJewels) {
                if (closestUndesiredJewel != null) {
                    if (thing.getName().equals(closestUndesiredJewel.getName())) {
                        curIndex = jewelIndex;
                    }
                }
                jewelIndex++;
            }
            if (curIndex != -1) knownJewels.remove(curIndex);
            closestUndesiredJewel = null;
            
        }
    }
}
