/**
*  Created by Faisal Sikder
*  University of Miami
*  Simple dijkstra algo solution of the gas fill in problem
*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class gasoline {

    public static void main(String[] args) {
        try{
            String inputFile = "gas.in";  //reading files with input sets
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            List<InputSet> inputSets = new ArrayList<InputSet>();
            InputSet inputSet = null;
            String line = null;
            while ( !(line = br.readLine()).trim().equals("0 0") ){
                if ( inputSet == null ){  // init new set
                    inputSet = new InputSet(line);
                    inputSets.add(inputSet);
                }else if ( inputSet.appendLast(line) ){  //append gasstation info to set
                    inputSet = null; //move to next set
                }
            }
            br.close();
            int i =1;
            for(InputSet is : inputSets){ //iterating on input sets and find solution
                DijkstraAlgo algo = new DijkstraAlgo(is);
                System.out.println("Trip "+(i++)+": " + algo.findCheapestPath());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
