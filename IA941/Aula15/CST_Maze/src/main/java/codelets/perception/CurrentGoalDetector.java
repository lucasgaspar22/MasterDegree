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
import memory.CreatureInnerSense;
import ws3dproxy.model.WorldPoint;

/**
 *
 * @author lucas
 */
public class CurrentGoalDetector extends Codelet{

    private Memory knownGoalsMO;
    private Memory currentGoalMO;
    private Memory innerSenseMO;

    private List<WorldPoint> knownGoals;

    @Override
    public void accessMemoryObjects() {
        this.knownGoalsMO = (MemoryObject) this.getInput("KNOWN_GOALS");
        this.innerSenseMO = (MemoryObject) this.getInput("INNER");
        this.currentGoalMO = (MemoryObject) this.getOutput("CURRENT_GOAL");
    }

    @Override
    public void proc() {
        WorldPoint currentGoal = null;
        knownGoals = Collections.synchronizedList((List<WorldPoint>) knownGoalsMO.getI());
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) innerSenseMO.getI();

        synchronized (knownGoals) {
            if (knownGoals.size() != 0) {
                currentGoal = creatureInnerSense.getCurrentGoal();
            }
            currentGoalMO.setI(currentGoal);
        }
    }
    
    @Override
    public void calculateActivation() {
    }
    
}
