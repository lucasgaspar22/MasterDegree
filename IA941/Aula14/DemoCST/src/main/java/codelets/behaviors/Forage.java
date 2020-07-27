/*****************************************************************************
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
 *****************************************************************************/

package codelets.behaviors;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import ws3dproxy.model.Thing;

/** 
 * 
 * @author klaus
 * 
 * 
 */

public class Forage extends Codelet {
    
        private Memory knownApplesMO;
        private Memory knownJewelsMO;
        private List<Thing> knownApples;
        private List<Thing> knownJewels;
        private Memory legsMO;


	/**
	 * Default constructor
	 */
	public Forage(){       
	}

	@Override
	public void proc() {
        knownApples = (List<Thing>) knownApplesMO.getI();
        knownJewels = (List<Thing>) knownJewelsMO.getI();
        
		if (knownJewels.size() == 0 || knownApples.size() == 0) {
			JSONObject message=new JSONObject();
			try {
				message.put("ACTION", "FORAGE");
				legsMO.setI(message.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }            
	}

	@Override
	public void accessMemoryObjects() {
            knownJewelsMO = (MemoryObject)this.getInput("KNOWN_JEWELS");
            knownApplesMO = (MemoryObject)this.getInput("KNOWN_APPLES");
            legsMO=(MemoryObject)this.getOutput("LEGS");

		// TODO Auto-generated method stub
		
	}
        
        @Override
        public void calculateActivation() {
            
        }


}
