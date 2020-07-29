
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

/**
 *
 * @author rgudwin
 */
public class AgentMind extends Mind {

    private static int creatureBasicSpeed = 2;
    private static int reachDistance = 50;

    public AgentMind(Environment env) {
        super();

        // Declare Memory Objects
        Memory legsMO;
        Memory handsMO;
        Memory visionMO;
        Memory innerSenseMO;
        Memory closestFoodMO;
        Memory knownFoodsMO;
        Memory knownJewelsMO;
        Memory closestDesiredJewelMO;
        //Memory closestUndesiredJewelMO;
        

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
        //closestUndesiredJewelMO = createMemoryObject("CLOSEST_UNDESIRED_JEWEL", closestJewel);
        List<Thing> knownJewels = Collections.synchronizedList(new ArrayList<Thing>());
        knownJewelsMO = createMemoryObject("KNOWN_JEWELS", knownJewels);

        // Create and Populate MindViewer
        MindView mv = new MindView("MindView");
        mv.addMO(knownFoodsMO);
        mv.addMO(knownJewelsMO);
        mv.addMO(visionMO);
        mv.addMO(closestFoodMO);
        mv.addMO(closestDesiredJewelMO);
        //mv.addMO(closestUndesiredJewelMO);
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
        Codelet foodDetector = new FoodDetector();
        foodDetector.addInput(visionMO);
        foodDetector.addOutput(knownFoodsMO);
        insertCodelet(foodDetector);

        Codelet closestFoodDetector = new ClosestFoodDetector();
        closestFoodDetector.addInput(knownFoodsMO);
        closestFoodDetector.addInput(innerSenseMO);
        closestFoodDetector.addOutput(closestFoodMO);
        insertCodelet(closestFoodDetector);

        Codelet jewelDetector = new JewelDetector();
        jewelDetector.addInput(visionMO);
        jewelDetector.addOutput(knownJewelsMO);
        insertCodelet(jewelDetector);

        Codelet closestJewelDetector = new ClosestJewelDetector();
        closestJewelDetector.addInput(knownJewelsMO);
        closestJewelDetector.addInput(innerSenseMO);
        closestJewelDetector.addOutput(closestDesiredJewelMO);
        insertCodelet(closestJewelDetector);

        // Create Behavior Codelets
        Codelet goToClosestFood = new GoToClosestFood(creatureBasicSpeed, reachDistance);
        goToClosestFood.addInput(closestFoodMO);
        goToClosestFood.addInput(innerSenseMO);
        goToClosestFood.addOutput(legsMO);
        insertCodelet(goToClosestFood);

        Codelet goToClosestDesiredJewel = new GoToClosestDesiredJewel(creatureBasicSpeed, reachDistance);
        goToClosestDesiredJewel.addInput(closestDesiredJewelMO);
        goToClosestDesiredJewel.addInput(innerSenseMO);
        goToClosestDesiredJewel.addOutput(legsMO);
        insertCodelet(goToClosestDesiredJewel);

        Codelet goToDeliverySpot = new GoToDeliverySpot(creatureBasicSpeed, reachDistance);
        //goToDeliverySpot.addInput(closestDesiredJewelMO);
        goToDeliverySpot.addInput(innerSenseMO);
        goToDeliverySpot.addOutput(legsMO);
        insertCodelet(goToDeliverySpot);

        Codelet eatFood = new EatClosestFood(reachDistance);
        eatFood.addInput(closestFoodMO);
        eatFood.addInput(innerSenseMO);
        eatFood.addOutput(handsMO);
        eatFood.addOutput(knownFoodsMO);
        insertCodelet(eatFood);

        Codelet getDesiredJewel = new GetClosestDesiredJewel(reachDistance);
        getDesiredJewel.addInput(closestDesiredJewelMO);
        getDesiredJewel.addInput(innerSenseMO);
        getDesiredJewel.addOutput(handsMO);
        getDesiredJewel.addOutput(knownJewelsMO);
        insertCodelet(getDesiredJewel);

        Codelet hideUndesiredJewel = new HideClosestUndesiredJewel(reachDistance);
        hideUndesiredJewel.addInput(closestDesiredJewelMO);
        hideUndesiredJewel.addInput(innerSenseMO);
        hideUndesiredJewel.addOutput(handsMO);
        hideUndesiredJewel.addOutput(knownJewelsMO);
        insertCodelet(hideUndesiredJewel);
        
        Codelet deliverLeaflet = new DeliverLeaflet(reachDistance);
        //goToDeliverySpot.addInput(closestDesiredJewelMO);
        deliverLeaflet.addInput(innerSenseMO);
        deliverLeaflet.addOutput(handsMO);
        insertCodelet(deliverLeaflet);


        Codelet forage = new Forage();
        forage.addInput(knownFoodsMO);
        forage.addInput(knownJewelsMO);
        forage.addInput(visionMO);
        forage.addOutput(legsMO);
        insertCodelet(forage);

        // sets a time step for running the codelets to avoid heating too much your machine
        for (Codelet codelet : this.getCodeRack().getAllCodelets()) {
            codelet.setTimeStep(200);
        }

        // Start Cognitive Cycle
        start();
    }

}
