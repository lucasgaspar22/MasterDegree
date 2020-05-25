/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SoarBridge;

/**
 *
 * @author lucas
 */
public class CommandDeliver {
    
    private String thingName = null;

    public CommandDeliver(){}

    /**
     * @return the thingName
     */
    public String getThingName()
    {
        return thingName;
    }

    /**
     * @param thingName the thingName to set
     */
    public void setThingName(String thingName)
    {
        this.thingName = thingName;
    }
    
      public String getParams(){
       StringBuilder builder = new StringBuilder("Parameters: { ");
       builder.append("Leaflet ID: ").append(getThingName());
       return builder.append(" }").toString();
    }
}
