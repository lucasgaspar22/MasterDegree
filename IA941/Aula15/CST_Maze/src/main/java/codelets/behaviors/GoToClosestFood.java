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
package codelets.behaviors;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.json.JSONException;
import org.json.JSONObject;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import memory.CreatureInnerSense;
import ws3dproxy.model.Thing;

public class GoToClosestFood extends Codelet {

    private Memory closestFoodMO;
    private Memory selfInfoMO;
    private Memory legsMO;
    private int creatureBasicSpeed;
    private double reachDistance;

    public GoToClosestFood(int creatureBasicSpeed, int reachDistance) {
        this.creatureBasicSpeed = creatureBasicSpeed;
        this.reachDistance = reachDistance;
    }

    @Override
    public void accessMemoryObjects() {
        closestFoodMO = (MemoryObject) this.getInput("CLOSEST_FOOD");
        selfInfoMO = (MemoryObject) this.getInput("INNER");
        legsMO = (MemoryObject) this.getOutput("LEGS");
    }

    @Override
    public void proc() {
        // Find distance between creature and closest food
        //If far, go towards it
        //If close, stops

        Thing closestFood = (Thing) closestFoodMO.getI();
        CreatureInnerSense cis = (CreatureInnerSense) selfInfoMO.getI();

        if (closestFood != null && cis.fuel < 400) {
            double foodX = 0; 
            double foodY = 0;
            try {
                foodX = closestFood.getX1();
                foodY = closestFood.getY1();

            } catch (Exception e) {
                e.printStackTrace();
            }

            double selfX = cis.position.getX();
            double selfY = cis.position.getY();

            Point2D pFood = new Point();
            pFood.setLocation(foodX, foodY);

            Point2D pSelf = new Point();
            pSelf.setLocation(selfX, selfY);

            double distance = pSelf.distance(pFood);
            JSONObject message = new JSONObject();
            try {
                if (distance > reachDistance) { //Go to it
                    message.put("ACTION", "GOTO");
                    message.put("X", (int) foodX);
                    message.put("Y", (int) foodY);
                    message.put("SPEED", creatureBasicSpeed);

                } else {//Stop
                    message.put("ACTION", "GOTO");
                    message.put("X", (int) foodX);
                    message.put("Y", (int) foodY);
                    message.put("SPEED", 0.0);
                }
                legsMO.setI(message.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }//end proc

    @Override
    public void calculateActivation() {

    }

}
