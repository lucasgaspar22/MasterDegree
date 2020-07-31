/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codelets.perception;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.Collections;
import memory.CreatureInnerSense;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import ws3dproxy.model.Thing;
import ws3dproxy.util.Constants;

/**
 *
 * @author lucas
 */
public class ClosestJewelDetector extends Codelet {

    private Memory knownJewelsMO;
    private Memory closestDesiredJewelMO;
    private Memory innerSenseMO;

    private List<Thing> knownJewels;

    public ClosestJewelDetector() {
    }

    @Override
    public void accessMemoryObjects() {
        this.knownJewelsMO = (MemoryObject) this.getInput("KNOWN_JEWELS");
        this.innerSenseMO = (MemoryObject) this.getInput("INNER");
        this.closestDesiredJewelMO = (MemoryObject) this.getOutput("CLOSEST_JEWEL");
    }

    @Override
    public void proc() {
        Thing closestJewel = null;
        knownJewels = Collections.synchronizedList((List<Thing>) knownJewelsMO.getI());
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) innerSenseMO.getI();

        synchronized (knownJewels) {
            if (knownJewels.size() != 0) {
                CopyOnWriteArrayList<Thing> myKnownJewels = new CopyOnWriteArrayList<>(knownJewels);
                for (Thing thing : myKnownJewels) {
                    if (thing.getCategory() == Constants.categoryJEWEL) {
                        
                        if (closestJewel == null) {
                            closestJewel = thing;
                        } else {
                            double curClosestDistance = calculateDistance(closestJewel.getX1(), closestJewel.getY1(), creatureInnerSense.position.getX(), creatureInnerSense.position.getY());
                            double newClosestDistance = calculateDistance(thing.getX1(), thing.getY1(), creatureInnerSense.position.getX(), creatureInnerSense.position.getY());
                            if (newClosestDistance < curClosestDistance) {
                                closestJewel = thing;
                            }
                        }
                    }
                }
                
                if (closestJewel != null) {
                    if (closestDesiredJewelMO.getI() == null || !closestDesiredJewelMO.getI().equals(closestJewel)) {
                        closestDesiredJewelMO.setI(closestJewel);
                    }
                } else {
                    closestJewel = null;
                    closestDesiredJewelMO.setI(closestJewel);                        
                }
            } else {
                closestJewel = null;
                closestDesiredJewelMO.setI(closestJewel);
            }
        }
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }

    @Override
    public void calculateActivation() {
    }
}
