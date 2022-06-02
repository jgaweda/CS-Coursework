/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author gaweda
 */
public class Inhouse extends Part
{
    private int machineId;

    public void setMachineId(int machineId) 
    {
        this.machineId = machineId;
    }    

    public int getMachineId() 
    {
        return machineId;
    }
}
