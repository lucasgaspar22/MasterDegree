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


/**
 *
 * @author lucas
 */
public class JewelDetector extends Codelet {

    private Memory visionMO;
    private Memory knownJewelsMO;

    public JewelDetector(){
    }

    @Override
    public void accessMemoryObjects(){
        synchronized(this){
            this.visionMO = (MemoryObject)this.getInput("VISION");
        }
        this.knownJewelsMO = (MemoryObject)this.getOutput("KNOWN_JEWELS");
    }

    @Override
    public void proc(){
        CopyOnWriteArrayList<Thing> vision;
        List<Thing> knownJewels;

        synchronized(visionMO){
            vision = new CopyOnWriteArrayList((List<Thing>) visionMO.getI());    
            knownJewels = Collections.synchronizedList((List<Thing>) knownJewelsMO.getI());
            synchronized(vision){
                for(Thing thing : vision){
                    boolean found = false;
                    synchronized(knownJewels){
                        CopyOnWriteArrayList<Thing> myKnownJewels = new CopyOnWriteArrayList<>(knownJewels);
                        for (Thing auxThing : myKnownJewels){
                            if(thing.getName().equals(auxThing.getName())){
                                found = true;
                                break;
                            }
                            else if (!found && thing.getName().contains("Jewel")) knownJewels.add(thing);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void calculateActivation(){
    }
}