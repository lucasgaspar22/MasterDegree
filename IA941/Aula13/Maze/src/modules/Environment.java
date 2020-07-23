package modules;

import edu.memphis.ccrg.lida.environment.EnvironmentImpl;
import edu.memphis.ccrg.lida.framework.tasks.FrameworkTaskImpl;
import edu.memphis.ccrg.lida.framework.tasks.TaskManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ws3dproxy.WS3DProxy;
import ws3dproxy.CommandUtility;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Leaflet;
import ws3dproxy.model.Thing;
import ws3dproxy.model.World;
import ws3dproxy.util.Constants;
import java.util.Random; 


public class Environment extends EnvironmentImpl {

    private static final int DEFAULT_TICKS_PER_RUN = 100;
    private int ticksPerRun;
    private WS3DProxy proxy;
    private Creature creature;
    private Thing food;
    private Thing jewel;
    private Thing wall;
    private Thing wallAway;
    private List<Thing> thingAhead;
    private String currentAction;   
    private BackgroundTask backgroundTask;
    private int lastPressedButton =0;
    
    public Environment() {
        this.ticksPerRun = DEFAULT_TICKS_PER_RUN;
        this.proxy = new WS3DProxy();
        this.creature = null;
        this.food = null;
        this.jewel = null;
        this.wall = null;
        this.wallAway = null;
        this.thingAhead = new ArrayList<>();
        this.currentAction = "rotate";
    }

    @Override
    public void init() {
        super.init();
        ticksPerRun = (Integer) getParam("environment.ticksPerRun", DEFAULT_TICKS_PER_RUN);
        taskSpawner.addTask(new BackgroundTask(ticksPerRun));
        
        try {
            System.out.println("Reseting the WS3D World ...");
            World world = proxy.getWorld();
            world.reset();
            setupEnvironment();
            creature = proxy.createCreature(100, 100, 0);
            creature.start();
            System.out.println("Starting the WS3D Resource Generator ... ");
            //World.grow(1);
            Thread.sleep(4000);
            creature.updateState();
            System.out.println("DemoLIDA has started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupEnvironment(){
        try {
            // SETUP BORDERS

            World.createBrick(0, 798, 0, 800, 600);
            World.createBrick(0, 0, 0, 800, 2);
            World.createBrick(0, 0, 598, 800, 600);
            World.createBrick(0, 0, 0, 2, 600);
            
            //GENERATE MAZE
            World.createBrick(0, 400, 120, 402, 250);
            World.createBrick(0, 180, 250, 550, 252);
            World.createBrick(0, 120, 500, 400, 502);
            World.createBrick(0,   0, 400, 180, 402);
            World.createBrick(0, 400, 400, 402, 510);
            World.createBrick(0, 500, 500, 502, 600);
            World.createBrick(0, 550, 400, 700, 402);
            World.createBrick(0, 700, 500, 800, 502);
            World.createBrick(0, 640, 150, 642, 300);
            World.createBrick(0, 550, 150, 650, 152);
            
            generateJewel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateJewel(){
        try {
            Random random = new Random();
            int x = random.nextInt(600) + 100;
            int y = random.nextInt(400) + 100;
            World.createJewel(2, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class BackgroundTask extends FrameworkTaskImpl {

        public BackgroundTask(int ticksPerRun) {
            super(ticksPerRun);
        }

        @Override
        protected void runThisFrameworkTask() {
            updateEnvironment();
            processAction(currentAction);
            performAction(currentAction);   
        }
    }

    @Override
    public void resetState() {
        currentAction = "rotate";
    }
    
    public int getLastPressedButton() {
        return lastPressedButton;
    }

    @Override
    public Object getState(Map<String, ?> params) {
        Object requestedObject = null;
        String mode = (String) params.get("mode");
        switch (mode) {
            case "food":
                requestedObject = food;
                break;
            case "jewel":
                requestedObject = jewel;
                break;
            case "wall":
                requestedObject = wall;
                break;
            case "wallAway":
                requestedObject = wallAway;
                break;
            case "thingAhead":
                requestedObject = thingAhead;
                break;
            default:
                break;
        }
        return requestedObject;
    }

    
    public void updateEnvironment() {
        creature.updateState();
        food = null;
        jewel = null;
        wall = null;
        wallAway = null;
        thingAhead.clear();
                
        for (Thing thing : creature.getThingsInVision()) {
            if(creature.calculateDistanceTo(thing) <=60 && thing.getCategory() != Constants.categoryBRICK){
                // Identifica o objeto proximo que não seja parede
                thingAhead.add(thing);
                break;
            }
            else if (thing.getCategory() == Constants.categoryBRICK){
                if(creature.calculateDistanceTo(thing) <= 95){
                    if (wall == null ) {
                        wall = thing;
                        thingAhead.add(wall);
                        break;
                    }

                }else {
                    if(wallAway == null) wallAway = thing; 
                }      
            }
            else if (thing.getCategory() == Constants.categoryJEWEL) {
                if (jewel == null) {
                    // Identifica joia
                    jewel = thing;
                } 
            }
        }
    }
    
    
    
    @Override
    public void processAction(Object action) {
        String actionName = (String) action;
        currentAction = actionName.substring(actionName.indexOf(".") + 1);
        switch (currentAction) {
            case "forward":
                lastPressedButton=1;
                break;
            case "rotate":
                lastPressedButton=2;
                break;
            case "gotoJewel":
                lastPressedButton=3;
                break;
            case "get":
                lastPressedButton=4;
                break;
        }
                    
    }

    private void performAction(String currentAction) {
        try {
            System.out.println("Action: "+currentAction);
            switch (currentAction) {
                case "rotate":
                        creature.rotate(2.0);
                        //CommandUtility.sendSetTurn(creature.getIndex(), 2, -2, 2);
                    break;
                case "forward":
                    if(wall ==  null ){
                        creature.move(3.0 , 3.0, 0);
                    }
                    break;
                case "gotoFood":
                    if (food != null) 
                        creature.moveto(3.0, food.getX1(), food.getY1());
                        //CommandUtility.sendGoTo(creature.getIndex(), 3.0, 3.0, food.getX1(), food.getY1());
                    break;
                case "gotoJewel":
                    if (jewel != null)
                        creature.moveto(3.0, jewel.getX1(), jewel.getY1());
                        //CommandUtility.sendGoTo(creature.getIndex(), 3.0, 3.0, leafletJewel.getX1(), leafletJewel.getY1());
                    break;                    
                case "get":
                    creature.move(0.0, 0.0, 0.0);
                    //CommandUtility.sendSetTurn(creature.getIndex(), 0.0, 0.0, 0.0);
                    if (thingAhead != null) {
                        for (Thing thing : thingAhead) {
                            if (thing.getCategory() == Constants.categoryJEWEL) {
                                creature.putInSack(thing.getName());
                                generateJewel();
                            } else if (thing.getCategory() == Constants.categoryFOOD || thing.getCategory() == Constants.categoryNPFOOD || thing.getCategory() == Constants.categoryPFOOD) {
                                creature.eatIt(thing.getName());
                            }
                        }
                    }
                    this.resetState();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
