/**
*  Created by Faisal Sikder
*  University of Miami
*  Simple dijkstra algo solution of the gas fill in problem
*/

import java.math.BigDecimal;
import java.util.*;

public class DijkstraAlgo {

    //list of gasoline stations on the way. modified from initial set as we organize as roundtrip and exclude start point
    List<GasStation> gasolinePath = new ArrayList<GasStation>();

    //cache of visited gasoline stations
    Set<PathTravelled> visitedGasolines = new HashSet<PathTravelled>();

    //priority queue of stations on the way by cost
    PriorityQueue<PathTravelled> pending = new PriorityQueue<PathTravelled>();

    //queue with final states
    PriorityQueue<PathTravelled> goalStates = new PriorityQueue<PathTravelled>();

    //tank*mpg
    private int milesTillEmptyTank = 0;

    //mpg
    private int milesPerGallon = 0;
    
    public DijkstraAlgo(InputSet is){
        milesTillEmptyTank = is.getTank()*is.getMpg();
        milesPerGallon = is.getMpg();
        //iterate from first gasoline up to goal point
        for(int i=0;i<is.getNumOfGasoline();i++){
            GasStation gs = new GasStation(is.getMilage()[i], is.getPrice()[i]);
            gasolinePath.add(gs);
        }

        //iterate from last gasoline (exclude last gasoline if it is on goal state)
        for(int i=is.getNumOfGasoline()-1;i>=0;i--){
            if ( is.getMilage()[i] == is.getMilesOneWay() ) continue; //no need to include twice
            GasStation gs = new GasStation(2*is.getMilesOneWay()-is.getMilage()[i], is.getPrice()[i]);
            gasolinePath.add(gs);
            //mark last gasoline as a goal state
            if ( i == 0 ) gs.goalState = true;
        }
    }

    public double findCheapestPath(){
        //add to pending queue start point
        PathTravelled pt = new PathTravelled(0, 0, 0);
        pending.add(pt);
        //while we have sth in pending queue
        while(!pending.isEmpty()){
            pt = pending.poll();
            if ( gasolinePath.get(pt.index).goalState ){
                //the state we took from pending queue is goal state, adding to results queue
                goalStates.add(pt);
            }
            //we already visited that gasoline
            if ( visitedGasolines.contains(pt) ) continue;

            //mark gasoline as visited
            visitedGasolines.add(pt);
            //adding to pending queue all gasolines that reachable from current
            for(int i=pt.index+1;i<gasolinePath.size();i++){
                GasStation station = gasolinePath.get(i);
                if ( station.absMilage - pt.milesTravelled > milesTillEmptyTank ) break; //no need to iterate further, as stations not reachable due to tank limit
                //as we round to 2 decimals...

                double d = ((double)(station.absMilage-pt.milesTravelled))/milesPerGallon*station.cost;

                PathTravelled nextPt = new PathTravelled(i, pt.cost+d, station.absMilage);
                pending.add(nextPt);
            }
        }
        //getting from priority queue very first (with smallest cost
        if ( goalStates.isEmpty() ) return 0;
        return Math.round(goalStates.poll().cost*100.)/100.;
    }
    
    
    private class GasStation{
        public final int absMilage;
        public final float cost;
        public boolean goalState = false;
        
        public GasStation(int milage, float cost){
            absMilage = milage;
            this.cost = cost;
        }
    }

    //class to keep cost traveled to the gasoline, index refers to gasolinePath list
    // hashCode, equals for visited Gasolines
    //compareTo for pending & goalStates priority queues
    private class PathTravelled implements Comparable{
        public final int index;
        public final double cost;
        public final int milesTravelled;

        private PathTravelled(int index, double cost, int milesTravelled) {
            this.index = index;
            this.cost = cost;
            this.milesTravelled = milesTravelled;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PathTravelled that = (PathTravelled) o;

            if (index != that.index) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return index;
        }

        @Override
        public int compareTo(Object o) {
            double c2 = ((PathTravelled)o).cost; 
            if ( cost < c2 ) return -1;
            if ( cost > c2 ) return 1;
            return 0;
        }
    }
}
