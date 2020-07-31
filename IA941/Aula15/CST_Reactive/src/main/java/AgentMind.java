
/** ***************************************************************************
 * Copyright 2007-2015 DCA-FEEC-UNICAMP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *    Klaus Raizer, Andre Paraense, Ricardo Ribeiro Gudwin
 **************************************************************************** */
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.Mind;
import codelets.behaviors.*;
import codelets.motor.*;
import codelets.perception.*;
import codelets.sensors.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import memory.CreatureInnerSense;
import support.MindView;
import ws3dproxy.model.Thing;
import ws3dproxy.model.WorldPoint;

/**
 *
 * @author rgudwin
 */
public class AgentMind extends Mind {

    private static int creatureBasicSpeed = 1;
    private static int reachDistance = 60;

    public AgentMind(Environment env) {
        super();

        // Declare Memory Objects
        Memory legsMO;
        Memory handsMO;
        Memory visionMO;
        Memory innerSenseMO;
        Memory goalListMO;
        Memory knownFoodsMO;
        Memory knownJewelsMO;
        Memory knownWallsMO;
        Memory knownGoalsMO;
        Memory closestDesiredJewelMO;
        Memory closestFoodMO;
        Memory closestWallMO;
        Memory currentGoalMO;
        

        //Initialize Memory Objects
        legsMO = createMemoryObject("LEGS", "");
        handsMO = createMemoryObject("HANDS", "");
        List<Thing> vision_list = Collections.synchronizedList(new ArrayList<Thing>());
        visionMO = createMemoryObject("VISION", vision_list);

        CreatureInnerSense creatureInnerSense = new CreatureInnerSense();
        innerSenseMO = createMemoryObject("INNER", creatureInnerSense);

        Thing closestFood = null;
        closestFoodMO = createMemoryObject("CLOSEST_FOOD", closestFood);
        List<Thing> knownFoods = Collections.synchronizedList(new ArrayList<Thing>());
        knownFoodsMO = createMemoryObject("KNOWN_FOODS", knownFoods);

        Thing closestJewel = null;
        closestDesiredJewelMO = createMemoryObject("CLOSEST_JEWEL", closestJewel);
        List<Thing> knownJewels = Collections.synchronizedList(new ArrayList<Thing>());
        knownJewelsMO = createMemoryObject("KNOWN_JEWELS", knownJewels);

        Thing closestWall = null;
        closestWallMO = createMemoryObject("CLOSEST_WALL", closestWall);
        List<Thing> knownWalls = Collections.synchronizedList(new ArrayList<Thing>());
        knownWallsMO = createMemoryObject("KNOWN_WALLS", knownWalls);

        WorldPoint currentGoal = creatureInnerSense.getCurrentGoal();
        currentGoalMO = createMemoryObject("CURRENT_GOAL", currentGoal);
        List<WorldPoint> knownGoals = Collections.synchronizedList(new ArrayList<WorldPoint>());
        knownGoalsMO = createMemoryObject("KNOWN_GOALS", knownGoals);
        goalListMO = createMemoryObject("GOAL_LIST", creatureInnerSense.goals);

        // Create and Populate MindViewer
        MindView mv = new MindView("MindView");
        mv.addMO(visionMO);
        mv.addMO(knownGoalsMO);
        mv.addMO(currentGoalMO);
        mv.addMO(closestWallMO);
        mv.addMO(innerSenseMO);
        mv.addMO(handsMO);
        mv.addMO(legsMO);
        mv.StartTimer();
        mv.setVisible(true);

        // Create Sensor Codelets	
        Codelet vision = new Vision(env.creature);
        vision.addOutput(visionMO);
        insertCodelet(vision); //Creates a vision sensor

        Codelet innerSense = new InnerSense(env.creature);
        innerSense.addOutput(innerSenseMO);
        insertCodelet(innerSense); //A sensor for the inner state of the creature

        // Create Actuator Codelets
        Codelet legs = new LegsActionCodelet(env.creature);
        legs.addInput(legsMO);
        insertCodelet(legs);

        Codelet hands = new HandsActionCodelet(env.creature);
        hands.addInput(handsMO);
        insertCodelet(hands);

        // Create Perception Codelets
        Codelet goalDetector = new GoalDetector();
        goalDetector.addInput(goalListMO);
        goalDetector.addOutput(knownGoalsMO);
        insertCodelet(goalDetector);

        Codelet currentGoalDetector = new CurrentGoalDetector();
        currentGoalDetector.addInput(knownGoalsMO);
        currentGoalDetector.addInput(innerSenseMO);
        currentGoalDetector.addOutput(currentGoalMO);
        insertCodelet(currentGoalDetector);
        
        Codelet goalAchievedDetector = new GoalAchievedDetector();
        goalAchievedDetector.addInput(innerSenseMO);
        goalAchievedDetector.addInput(currentGoalMO);
        insertCodelet(goalAchievedDetector);


        Codelet wallDetector = new WallDetector();
        wallDetector.addInput(visionMO);
        wallDetector.addOutput(knownWallsMO);
        insertCodelet(wallDetector);

        Codelet closestWallDetector = new ClosestWallDetector();
        closestWallDetector.addInput(knownWallsMO);
        closestWallDetector.addInput(innerSenseMO);
        closestWallDetector.addOutput(closestWallMO);
        insertCodelet(closestWallDetector);

        // Create Behavior Codelets
        Codelet goToGoal = new GoToGoal(creatureBasicSpeed,20);
        goToGoal.addInput(currentGoalMO);
        goToGoal.addInput(innerSenseMO);
        goToGoal.addOutput(legsMO);
        insertCodelet(goToGoal);

        Codelet avoidWall = new AvoidWall(creatureBasicSpeed,reachDistance);
        avoidWall.addInput(closestWallMO);
        avoidWall.addInput(innerSenseMO);
        avoidWall.addOutput(legsMO);
        insertCodelet(avoidWall);


//        Codelet forage = new Forage();
//        forage.addInput(knownFoodsMO);
//        forage.addInput(knownJewelsMO);
//        forage.addInput(visionMO);
//        forage.addOutput(legsMO);
//        insertCodelet(forage);

        // sets a time step for running the codelets to avoid heating too much your machine
        for (Codelet codelet : this.getCodeRack().getAllCodelets()) {
            codelet.setTimeStep(500);
        }

        // Start Cognitive Cycle
        start();
    }

}
