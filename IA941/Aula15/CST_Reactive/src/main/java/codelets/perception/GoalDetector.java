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
import ws3dproxy.model.WorldPoint;
/**
 *
 * @author lucas
 */
public class GoalDetector extends Codelet {

    private Memory goalsListMO;
    private Memory knownGoalsMO;

    public GoalDetector() {

    }

    @Override
    public void accessMemoryObjects() {
        synchronized (this) {
            this.goalsListMO = (MemoryObject) this.getInput("GOAL_LIST");
        }
        this.knownGoalsMO = (MemoryObject) this.getOutput("KNOWN_GOALS");
    }

    @Override
    public void proc() {

        CopyOnWriteArrayList<WorldPoint> goalList;
        List<WorldPoint> knownGoals;
        
        synchronized (goalsListMO) {
            goalList = new CopyOnWriteArrayList((List<WorldPoint>) goalsListMO.getI());
            knownGoals = Collections.synchronizedList((List<WorldPoint>) knownGoalsMO.getI());
            synchronized (goalList) {
                for (WorldPoint goal : goalList) {
                    addToMemory(goal, knownGoals);
                }
            }
        }
    }

    @Override
    public void calculateActivation() {}

    private void addToMemory(WorldPoint goal, List<WorldPoint> knownGoals) {
        boolean isInMemory = false;
        for (WorldPoint g : knownGoals) {
            if (!g.isOther(goal)) {
                isInMemory = true;
                break;
            }
        }
        if (!isInMemory) {
            knownGoals.add(goal);
        }
        
    }   
}
