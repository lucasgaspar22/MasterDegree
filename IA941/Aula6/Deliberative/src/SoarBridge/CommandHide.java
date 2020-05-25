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
public class CommandHide {
    
    private String thingName = null;
    
    public CommandHide(){}
    
    public String getThingName()
    {
        return thingName;
    }
    
    public void setThingName(String thingName)
    {
        this.thingName = thingName;
    }
    
    public String getParams(){
       StringBuilder builder = new StringBuilder("Parameters: { ");
       builder.append("Things Name: ").append(getThingName());
       return builder.append(" }").toString();
    }
}
