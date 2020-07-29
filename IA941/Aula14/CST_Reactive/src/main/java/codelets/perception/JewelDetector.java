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
public class JewelDetector extends Codelet {

    private Memory visionMO;
    private Memory knownJewelsMO;

    public JewelDetector() {
    }

    @Override
    public void accessMemoryObjects() {
        synchronized (this) {
            this.visionMO = (MemoryObject) this.getInput("VISION");
        }
        this.knownJewelsMO = (MemoryObject) this.getOutput("KNOWN_JEWELS");
    }

    @Override
    public void proc() {
        CopyOnWriteArrayList<Thing> vision;
        List<Thing> knownJewels;

        synchronized (visionMO) {
            vision = new CopyOnWriteArrayList((List<Thing>) visionMO.getI());
            knownJewels = Collections.synchronizedList((List<Thing>) knownJewelsMO.getI());
            synchronized (vision) {
                for (Thing thing : vision) {
                    // for each thing in vision, add it to memory
                    addToMemory(thing, knownJewels);
                }
            }
        }
    }

    @Override
    public void calculateActivation() {
    }

    private void addToMemory(Thing thing, List<Thing> knownJewels) {
        if (thing.getCategory() == Constants.categoryJEWEL) {
            boolean isInMemory = false;
            for (Thing t : knownJewels) {
                if (t.getName().equals(thing.getName())) {
                    isInMemory = true;
                    break;
                }
            }
            if (!isInMemory) {
                knownJewels.add(thing);
            }
        }
    }
}
