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
import memory.CreatureInnerSense;
import ws3dproxy.model.WorldPoint;

/**
 *
 * @author lucas
 */
public class GoToGoal extends Codelet {

    private Memory curGoalMO;
    private Memory selfInfoMO;
    private Memory legsMO;
    private int creatureBasicSpeed;
    private double reachDistance;
    WorldPoint curGoal;

    public GoToGoal(int creatureBasicSpeed, int reachDistance) {
        this.creatureBasicSpeed = creatureBasicSpeed;
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        curGoalMO = (MemoryObject) this.getInput("CURRENT_GOAL");
        selfInfoMO = (MemoryObject) this.getInput("INNER");
        legsMO = (MemoryObject) this.getOutput("LEGS");
    }

     @Override
    public void proc() {
        curGoal = (WorldPoint) curGoalMO.getI();
        CreatureInnerSense creatureInnerSense = (CreatureInnerSense) selfInfoMO.getI();

        if(curGoal != null){
            double curGoalX = 0;
            double curGoalY = 0;

            try{
                curGoalX = curGoal.getX();
                curGoalY = curGoal.getY();
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
            JSONObject message = new JSONObject();

            try {
                if (distance > reachDistance && !creatureInnerSense.isDodging) {
                    message.put("ACTION", "GOTO");
                    message.put("X",  curGoalX);
                    message.put("Y",  curGoalY);
                    message.put("SPEED", creatureBasicSpeed);
                    legsMO.setI(message.toString());
                    
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
    
}
