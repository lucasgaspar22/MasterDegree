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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import ws3dproxy.model.Thing;
import ws3dproxy.util.Constants;

/**
 *
 * @author lucas
 */
public class WallDetector extends Codelet {

    private Memory visionMO;
    private Memory knownWallsMO;

    public WallDetector() {
    }

    @Override
    public void accessMemoryObjects() {
        synchronized (this) {
            this.visionMO = (MemoryObject) this.getInput("VISION");
        }
        this.knownWallsMO = (MemoryObject) this.getOutput("KNOWN_WALLS");
    }

    @Override
    public void proc() {
        CopyOnWriteArrayList<Thing> vision;
        List<Thing> knownWalls;

        synchronized (visionMO) {
            vision = new CopyOnWriteArrayList((List<Thing>) visionMO.getI());
            knownWalls = Collections.synchronizedList((List<Thing>) knownWallsMO.getI());
            synchronized (vision) {
                for (Thing thing : vision) {
                    addToMemory(thing, knownWalls);
                }
            }
        }
    }

    @Override
    public void calculateActivation() {
    }

    private void addToMemory(Thing thing, List<Thing> knownWalls) {
        if (thing.getCategory() == Constants.categoryBRICK) {
            boolean isInMemory = false;
            for (Thing t : knownWalls) {
                if (t.getName().equals(thing.getName())) {
                    isInMemory = true;
                    break;
                }
            }
            if (!isInMemory) {
                knownWalls.add(thing);
            }
        }
    }
}
