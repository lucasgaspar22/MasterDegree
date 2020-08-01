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
package codelets.motor;

import org.json.JSONException;
import org.json.JSONObject;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.Random;
import java.util.logging.Logger;
import ws3dproxy.model.Creature;

/**
 * Hands Action Codelet monitors working storage for instructions and acts on
 * the World accordingly.
 *
 * @author klaus
 *
 */
public class HandsActionCodelet extends Codelet {

    private Memory handsMO;
    private String previousHandsAction = "";
    private Creature creature;
    private Random r = new Random();
    static Logger log = Logger.getLogger(HandsActionCodelet.class.getCanonicalName());

    public HandsActionCodelet(Creature nc) {
        creature = nc;
    }

    @Override
    public void accessMemoryObjects() {
        handsMO = (MemoryObject) this.getInput("HANDS");
    }

    public void proc() {

        String command = (String) handsMO.getI();

        if (!command.equals("") && (!command.equals(previousHandsAction))) {
            JSONObject jsonAction;
            try {
                jsonAction = new JSONObject(command);
                if (jsonAction.has("ACTION") && jsonAction.has("OBJECT")) {
                    String action = jsonAction.getString("ACTION");
                    String objectName = jsonAction.getString("OBJECT");
                    if (action.equals("PICKUP")) {
                        try {
                            creature.putInSack(objectName);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("Something went wrong\n" + e.getMessage());
                        }
                        log.info("Sending Put In Sack command to agent:****** " + objectName + "**********");
                    }
                    else if (action.equals("EATIT")) {
                        try {
                            creature.eatIt(objectName);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("Something went wrong\n" + e.getMessage());
                        }
                        log.info("Sending Eat command to agent:****** " + objectName + "**********");
                    }
                    else if (action.equals("BURY")) {
                        try {
                            creature.hideIt(objectName);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("Something went wrong\n" + e.getMessage());
                        }
                        log.info("Sending Bury command to agent:****** " + objectName + "**********");
                    }
                    else if(action.equals("DELIVER")){
                        try{
                            creature.deliverLeaflet(objectName);
                        }catch(Exception e){
                            
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println("Something went wrong\n" + e.getMessage());
            }

        }
        previousHandsAction = (String) handsMO.getI();
    }

    @Override
    public void calculateActivation() {

    }

}
