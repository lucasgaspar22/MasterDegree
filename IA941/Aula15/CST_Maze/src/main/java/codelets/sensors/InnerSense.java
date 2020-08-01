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
package codelets.sensors;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.List;
import memory.CreatureInnerSense;
import ws3dproxy.model.Bag;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Leaflet;

/**
 * This class reads information from this agent's state and writes it to an
 * inner sense sensory buffer.
 *
 * @author klaus
 *
 */
public class InnerSense extends Codelet {

    private Memory innerSenseMO;
    private Creature creature;
    private CreatureInnerSense creatureInnerSense;

    public InnerSense(Creature nc) {
        this.creature = nc;
    }

    @Override
    public void accessMemoryObjects() {
        innerSenseMO = (MemoryObject) this.getOutput("INNER");
        creatureInnerSense = (CreatureInnerSense) innerSenseMO.getI();
    }

    public void proc() {
        creatureInnerSense.position = creature.getPosition();
        creatureInnerSense.pitch = creature.getPitch();
        creatureInnerSense.fuel = creature.getFuel();
        creatureInnerSense.score = creature.getAttributes().getScore();
        creatureInnerSense.FOV = creature.getFOV();
        creatureInnerSense.leaflet = getMostValuableLeaflet(creature.getLeaflets());
        creatureInnerSense.creature = this.creature;
    }

    @Override
    public void calculateActivation() {

    }

    private Leaflet getMostValuableLeaflet(List<Leaflet> leaflets) {
        Leaflet leaflet = null;
        int price = -1;
        for (Leaflet l : leaflets) {
            if (l.getPayment() > price) {
                leaflet = l;
                price = l.getPayment();
            }
        }
        return leaflet;
    }
}
