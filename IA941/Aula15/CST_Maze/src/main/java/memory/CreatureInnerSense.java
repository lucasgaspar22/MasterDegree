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
import java.util.ArrayList;
import java.util.List;
import ws3dproxy.model.Bag;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Environment;
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
    public Creature creature;
    public List<WorldPoint> goals = new ArrayList<> ();
    public boolean isGoingUp=false;
    private int goalIndex=0;
    public boolean isDodging = false;
    
    public CreatureInnerSense(){
        goals.add(new WorldPoint(0,0));
        goals.add(new WorldPoint(800,600));

    }
    
    public String toString() {
        if (position != null) {
            return ("Position: (" + (int) position.getX() + "," + (int) position.getY() + ")\nPitch: " + (int) pitch + " \nFuel: " + fuel + "\nScore: "+ score +"\nLeaflet: " + leaflet);
        } else {
            return ("Position: null,null" + " Pitch: " + pitch + " Fuel: " + fuel);
        }
    }

    public WorldPoint getCurrentGoal(){
        return goals.get(goalIndex);
    }

    public void incGoal(){
        if(!isGoingUp){
            goalIndex++;
            if(goalIndex == 1) isGoingUp = true;
        }else{
            goalIndex--;
            if(goalIndex == 0) isGoingUp = false;
        }
    }
    
    public boolean getGoalDirection(){
        if (goalIndex == 0 ) return true; // DOWN
        else return false; // UP
    }

    public boolean isJewelDesired(String color){
        int leafletQuantity = leaflet.getTotalNumberOfType(color);
        int bagQuantity = bag.getNumberCrystalPerType(color);
        
        if(leafletQuantity > 0) return true;
        else return false;
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
