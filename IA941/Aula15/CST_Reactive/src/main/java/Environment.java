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

import java.util.logging.Level;
import java.util.logging.Logger;
import ws3dproxy.CommandExecException;
import ws3dproxy.WS3DProxy;
import ws3dproxy.model.Creature;
import ws3dproxy.model.World;

/**
 *
 * @author rgudwin
 */
public class Environment {
    
    public String host="localhost";
    public int port = 4011;
    public String robotID="r0";
    public Creature creature = null;
    
    public Environment() {
        WS3DProxy proxy = new WS3DProxy();
        try {   
            World w = World.getInstance();
            w.reset();
            generateMaze();
            //World.grow(1);
            creature = proxy.createCreature(800,600,0);
            System.out.println("Robot "+creature.getName()+" is ready to go.");
            Thread.sleep(4000);
            creature.genLeaflet();
            creature.updateBag();
            creature.start();   
        } catch (Exception e) {
              System.err.println("Something went wrong\n"+e.getMessage());
        }
        
    }

    private void generateMaze(){
        try {
            //GENERATE MAZE
            World.createBrick(0, 100, 0, 150, 450);
            World.createBrick(1, 250, 150, 300, 600);
            World.createBrick(0, 400, 0, 450, 450); 
            World.createBrick(1, 550, 150, 600, 600); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
