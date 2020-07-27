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
package memory;

import java.awt.Polygon;
import java.util.List;
import ws3dproxy.model.Bag;
import ws3dproxy.model.Leaflet;
import ws3dproxy.model.WorldPoint;

/**
 *
 * @author rgudwin
 */
public class CreatureInnerSense {

    public WorldPoint position;
    public double pitch;
    public double fuel;
    public double score;
    public Polygon FOV;
    public List<Leaflet> leaflets;
    public Leaflet leaflet;
    public Bag bag;

    public String toString() {
        if (position != null) {
            return ("Position: (" + (int) position.getX() + "," + (int) position.getY() + ")\nPitch: " + (int) pitch + " \nFuel: " + fuel + "\nScore: "+ score +"\nLeaflet: " + leaflet);
        } else {
            return ("Position: null,null" + " Pitch: " + pitch + " Fuel: " + fuel);
        }
    }

    public boolean isJewelDesired(String color){
        int bagQuantity = bag.getNumberCrystalPerType(color);
        int leafletQuantity = leaflet.getTotalNumberOfType(color);

        if(bagQuantity >= leafletQuantity) return false;
        else return true;
    }

    public boolean isLeafletReady(){
        int redBag = bag.getNumberCrystalPerType("Red");
        int greenBag =  bag.getNumberCrystalPerType("Green");
        int blueBag = bag.getNumberCrystalPerType("Blue");
        int yellowBag = bag.getNumberCrystalPerType("Yellow");
        int magentaBag = bag.getNumberCrystalPerType("Magenta");
        int whiteBag = bag.getNumberCrystalPerType("White");
        
        int redLeaflet = leaflet.getTotalNumberOfType("Red") == -1 ? 0 : leaflet.getTotalNumberOfType("Red");
        int greenLeaflet = leaflet.getTotalNumberOfType("Green") == -1 ? 0 : leaflet.getTotalNumberOfType("Green");
        int blueLeaflet = leaflet.getTotalNumberOfType("Blue") == -1 ? 0 : leaflet.getTotalNumberOfType("Blue");
        int yellowLeaflet = leaflet.getTotalNumberOfType("Yellow") == -1 ? 0 : leaflet.getTotalNumberOfType("Yellow");
        int magentaLeaflet = leaflet.getTotalNumberOfType("Magenta") == -1 ? 0 : leaflet.getTotalNumberOfType("Magenta");
        int whiteLeaflet = leaflet.getTotalNumberOfType("White") == -1 ? 0 : leaflet.getTotalNumberOfType("White");
        
        if ( redBag >= redLeaflet && greenBag >= greenLeaflet && blueBag >= blueLeaflet &&
             yellowBag >= yellowLeaflet && magentaBag >= magentaLeaflet && whiteBag >= whiteLeaflet ) return true;
        else return false;
    }

}
