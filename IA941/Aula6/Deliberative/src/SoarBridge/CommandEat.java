/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SoarBridge;

/**
 *
 * @author Danilo
 */
public class CommandEat
{
    
    private String thingName = null;

    public CommandEat()
    {

    }

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
       builder.append("Things Name: ").append(getThingName());
       return builder.append(" }").toString();
    }

}
