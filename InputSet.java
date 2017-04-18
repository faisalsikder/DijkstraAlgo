/**
*  Created by Faisal Sikder
*  University of Miami
*  Simple dijkstra algo solution of the gas fill in problem
*/

public class InputSet {
    
    private final byte tank;
    private final byte mpg;
    private int numOfGasoline;
    private int milesOneWay;
    private float[] price;
    private int[] milage;
    private int initIndex = -1;
    
    public InputSet(String line){
        String[] args = line.split(" ");
        tank = Byte.parseByte(args[0]);
        mpg = Byte.parseByte(args[1]);
    }
    
    public boolean appendLast(String line){
        String[] args = line.split(" ");
        if ( initIndex == -1 ){
            numOfGasoline = Integer.parseInt(args[0]);
            milesOneWay = Integer.parseInt(args[1]);
            price = new float[numOfGasoline];
            milage = new int[numOfGasoline];
            initIndex = 0;
            return false;
        }
        price[initIndex] = Float.parseFloat(args[1]);
        milage[initIndex] = Integer.parseInt(args[0]);
        initIndex++;
        return initIndex == numOfGasoline;
    }

    public byte getTank() {
        return tank;
    }

    public byte getMpg() {
        return mpg;
    }

    public int getNumOfGasoline() {
        return numOfGasoline;
    }

    public int getMilesOneWay() {
        return milesOneWay;
    }

    public float[] getPrice() {
        return price;
    }

    public int[] getMilage() {
        return milage;
    }
}
