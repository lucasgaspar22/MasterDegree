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
import util.Landmarks;


public class Environment extends EnvironmentImpl {

    private static final int DEFAULT_TICKS_PER_RUN = 100;
    private int ticksPerRun;
    private WS3DProxy proxy;
    private Creature creature;
    private Thing wall;
    private List<Thing> thingAhead;
    private String currentAction;
    private int lastPressedButton = 0;
    private Landmarks landmarks = new Landmarks();
        
    private double wallX;
    private double wallY;
    
    
    
    public Environment() {
        this.ticksPerRun = DEFAULT_TICKS_PER_RUN;
        this.proxy = new WS3DProxy();
        this.creature = null;
        this.wall = null;
        this.thingAhead = new ArrayList<>();
        this.currentAction = "goToLandMark";
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
            creature = proxy.createCreature(800, 600, 0);
            creature.start();
            System.out.println("Starting the WS3D Resource Generator ... ");
            Thread.sleep(4000);
            creature.updateState();
            System.out.println("DemoLIDA has started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupEnvironment(){
        try {
            //GENERATE MAZE
            World.createBrick(0, 100, 0, 150, 450);
            World.createBrick(0, 250, 150, 300, 600);
            World.createBrick(0, 400, 0, 450, 450);
            World.createBrick(0, 550, 150, 600, 600);
            World.createBrick(0, 750, 0, 800, 450);
            generateJewel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateJewel(){
        try {
            World.createJewel(2, landmarks.getX(), landmarks.getY());
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
        currentAction = "goToLandMark";
    }
    
    public int getLastPressedButton() {
        return lastPressedButton;
    }

    @Override
    public Object getState(Map<String, ?> params) {
        Object requestedObject = null;
        String mode = (String) params.get("mode");
        switch (mode) {
            case "wall":
                requestedObject = wall;
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
        wall = null;
        thingAhead.clear();        
        double xRef = 99999;
        
        for (Thing thing : creature.getThingsInVision()) {
            if(creature.calculateDistanceTo(thing) <= 150 ){
                if(thing.getCategory() == Constants.categoryBRICK){
                    if(thing.getX2() < xRef){
                        if(wall == null || (thing.getX1() > wall.getX1() && thing.getX1() < creature.getAttributes().getX1()) ){
                            wall = thing;
                            xRef = thing.getX2();
                        }
                    }
                }
                else{
                    thingAhead.add(thing);
                }
            }  
        }
        
        if(wall != null){
            if (wall.getY1() < 10)  leftAvoid(wall);
            else rightAvoid(wall);
            
            
        }
        
        if(wall != null && wall.getX1()-40 > creature.getAttributes().getX1()) wall = null;
 
    }
        
    private void leftAvoid(Thing wall){
        wallX = (creature.getAttributes().getY1() > wall.getY2() + 30) ? wall.getX1()-50 : wall.getX2() + 60;
        wallY = wall.getY2() + 60;
    }
    
    private void rightAvoid(Thing wall){
        wallX = (creature.getAttributes().getY1() < wall.getY1() - 50) ? wall.getX1()-50 : wall.getX2() + 60;
        wallY = wall.getY1() - 60;
    }
    
    
    
    @Override
    public void processAction(Object action) {
        String actionName = (String) action;
        currentAction = actionName.substring(actionName.indexOf(".") + 1);
        switch (currentAction) {
            case "goToLandMark":
                lastPressedButton=1;
                break;
            case "avoidWall":
                lastPressedButton=2;
                break;
            case "get":
                lastPressedButton=3;
                break;
        }
    }

    
    private void performAction(String currentAction) {
        try {
            switch (currentAction) {
                case "avoidWall":
                    if(wall != null)
                        creature.moveto(3.0, wallX, wallY);
                    break;
                case "goToLandMark":
                    creature.moveto(6.0, landmarks.getX(), landmarks.getY());
                    break;
                case "get":
                    creature.move(0.0, 0.0, 0.0);
                    if (thingAhead != null) {
                        for (Thing thing : thingAhead) {
                            if (thing.getCategory() == Constants.categoryJEWEL) {
                                creature.putInSack(thing.getName());
                                landmarks.incLandMark();
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