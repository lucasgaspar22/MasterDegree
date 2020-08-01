/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codelets.perception;


import java.awt.Point;
import java.awt.geom.Point2D;

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
public class GoalAchievedDetector extends Codelet{
    
    private Memory innerSenseMO;
    private Memory currentGoalMO;

    @Override
    public void accessMemoryObjects() {
        this.innerSenseMO = (MemoryObject) this.getInput("INNER");
        this.currentGoalMO = (MemoryObject) this.getInput("CURRENT_GOAL");
    }
    
    @Override
    public void proc() {
        WorldPoint currentGoal = (WorldPoint) currentGoalMO.getI();
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) innerSenseMO.getI();

       if(currentGoal != null){
            double curGoalX = 0;
            double curGoalY = 0;

            try{
                curGoalX = currentGoal.getX();
                curGoalY = currentGoal.getY();
            }catch(Exception e){
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());
            }



            double creatureInnerSenseX = creatureInnerSense.position.getX();
            double creatureInnerSenseY = creatureInnerSense.position.getY();

            Point2D curGoalPoint = new Point();
            curGoalPoint.setLocation(curGoalX , curGoalY);

            Point2D creatureInnerSensePoint = new Point();
            creatureInnerSensePoint.setLocation(creatureInnerSenseX, creatureInnerSenseY);

            double distance = creatureInnerSensePoint.distance(curGoalPoint);

            if( distance <= 20) creatureInnerSense.incGoal();
        }
        
    }

    @Override
    public void calculateActivation() {
    }


    
}
