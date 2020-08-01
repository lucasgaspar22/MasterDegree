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
public class ClosestWallDetector extends Codelet {

    private Memory knownWallsMO;
    private Memory closestWallMO;
    private Memory innerSenseMO;

    private List<Thing> knownWalls;

    public ClosestWallDetector() {
    }

    @Override
    public void accessMemoryObjects() {
        this.knownWallsMO = (MemoryObject) this.getInput("KNOWN_WALLS");
        this.innerSenseMO = (MemoryObject) this.getInput("INNER");
        this.closestWallMO = (MemoryObject) this.getOutput("CLOSEST_WALL");
    }
    
    @Override
    public void proc() {
        Thing closestWall = null;
        knownWalls = Collections.synchronizedList((List<Thing>) knownWallsMO.getI());
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) innerSenseMO.getI();

        synchronized(knownWalls){
            if(knownWalls.size() != 0){
                CopyOnWriteArrayList<Thing> myKnownWalls = new CopyOnWriteArrayList <>(knownWalls);
                for( Thing thing : myKnownWalls) {
                    if(thing.getCategory() == Constants.categoryBRICK){
                        if(closestWall == null){
                            closestWall = thing;
                        }else{
                            double curClosestDistance = creatureInnerSense.creature.calculateDistanceTo(closestWall);
                            double newClosestDistance = creatureInnerSense.creature.calculateDistanceTo(thing);

                            if(newClosestDistance < curClosestDistance) closestWall = thing;
                        }
                    }
                }

                if(closestWall != null){
                    if(closestWallMO.getI() == null || !closestWallMO.getI().equals(closestWall)){
                        closestWallMO.setI(closestWall);
                    }
                }else{
                    closestWall = null;
                    closestWallMO.setI(closestWall);
                }

            }else{
                closestWall = null;
                closestWallMO.setI(closestWall);
            }
        }
    
    }

    @Override
    public void calculateActivation() {
    }
    
}
