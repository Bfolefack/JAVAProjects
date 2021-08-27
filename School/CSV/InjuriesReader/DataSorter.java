import java.io.*;
import java.util.*;

public class DataSorter{
    public static void main(String[] args) throws FileNotFoundException{
        int count = 0;
        File f = new File("Injuries.csv");
        Scanner sc = new Scanner(f);
        while(sc.hasNextLine()){
            sc.nextLine();
            count++;
        }
        sc.close();
        sc = new Scanner(f);
        count--;
        sc.nextLine();
        String[] raw = new String[count];
        for(int i = 0; i  < raw.length; i++){
            raw[i] = sc.nextLine();
        }
        sc.close();
        String[][] preprocessed = new String[count][6];
        for(int i = 0; i  < raw.length; i++){
            preprocessed[i][0] = raw[i].substring(0, raw[i].indexOf(","));
            raw[i] = raw[i].substring(raw[i].indexOf(",") + 1);
            for(int j = 1; j < 6; j++){
                if(raw[i].indexOf("\"") == 0){
                    raw[i] = raw[i].substring(raw[i].indexOf("\"") + 1);
                    preprocessed[i][j] = raw[i].substring(0, raw[i].indexOf("\""));
                    raw[i] = raw[i].substring(raw[i].indexOf("\"") + 1);
                }else {
                    if(raw[i].indexOf(",") == -1){
                        preprocessed[i][j] = raw[i];
                        break;
                    } else {
                        preprocessed[i][j] = raw[i].substring(0, raw[i].indexOf(","));
                    }
                }
                raw[i] = raw[i].substring(raw[i].indexOf(",") + 1);
            }
        }
        Injury[] injuries = new Injury[count];
        for(int i = 0; i  < count; i++){
            injuries[i] = new Injury(preprocessed[i]);
        }
        // System.out.println(count);

        ArrayList<Integer> nums = new ArrayList<Integer>();
        ArrayList<String> industries = new ArrayList<String>();
        for(int i = 0; i < count; i++){
            String field = injuries[i].industryField;
            if(industries.contains(field)){
                nums.set(industries.indexOf(field), nums.get(industries.indexOf(field)) + 1);
            } else {
                industries.add(field);
                nums.add(1);
            }
        }

        ArrayList<Integer> stateNums = new ArrayList<Integer>();
        ArrayList<String> states = new ArrayList<String>();
        for(int i = 0; i < count; i++){
            String state = injuries[i].state;
            if(states.contains(state)){
                stateNums.set(states.indexOf(state), stateNums.get(states.indexOf(state)) + 1);
            } else {
                states.add(state);
                stateNums.add(1);
            }
        }

        ArrayList<Integer> cityNums = new ArrayList<Integer>();
        ArrayList<String> cities = new ArrayList<String>();
        for(int i = 0; i < count; i++){
            String city = injuries[i].city;
            if(cities.contains(city)){
                cityNums.set(cities.indexOf(city), cityNums.get(cities.indexOf(city)) + 1);
            } else {
                cities.add(city);
                cityNums.add(1);
            }
        }



        int biggestIndex = biggest(nums, industries);
        int smallestIndex = smallest(nums, industries);
        int biggestIndex2 = biggest(stateNums, states);
        int smallestIndex2 = smallest(stateNums, states);
        int biggestIndex3 = biggest(cityNums, cities);
        int smallestIndex3 = smallest(cityNums, cities);
        
        float stateSum = 0;
        for(int i : stateNums){
            stateSum += i;
        }
        stateSum/=stateNums.size();

        float citySum = 0;
        for(int i : cityNums){
            citySum += i;
        }
        citySum/=cityNums.size();
        float indusSum = 0;
        for(int i : nums){
            indusSum += i;
        }
        
        indusSum/=nums.size();

        

        System.out.println(industries.get(smallestIndex) + " is the industry field with the least injuries at " + nums.get(smallestIndex));
        System.out.println(industries.get(biggestIndex) + " is the industry field with the most injuries at " + nums.get(biggestIndex));
        System.out.println("The average industry will have approximately " + (int) (indusSum/9) + " injuries per year");
        System.out.println();
        System.out.println(states.get(smallestIndex2) + " is the state with the least injuries at " + stateNums.get(smallestIndex2));
        System.out.println(states.get(biggestIndex2) + " is the state with the most injuries at " + stateNums.get(biggestIndex2));
        System.out.println("The average state will have approximately " + (int) (stateSum/9) + " injuries per year");
        System.out.println();
        System.out.println(cities.get(smallestIndex3) + " is the city with the least injuries at " + cityNums.get(smallestIndex3));
        System.out.println(cities.get(biggestIndex3) + " is the city with the most injuries at " + cityNums.get(biggestIndex3));
        if((int) (citySum/9)  < 1){
            System.out.println("The average city will have approximately <1 injuries per year");
        } else {
            System.out.println("The average city will have approximately " + (int) (citySum/9) + " injuries per year");
        }
        System.out.println();
        

    }

    static int biggest(ArrayList<Integer> in, ArrayList<String> st){
        int indusCount = 0;
        int bigIndusCount = 0;
        int greatest = Integer.MIN_VALUE;
        for(Integer i : in){
            if(i > greatest){
                greatest = i;
                bigIndusCount = indusCount;
            }
            indusCount++;
        }
        return bigIndusCount;
    }

    static int smallest(ArrayList<Integer> in, ArrayList<String> st){

        int indusCount = 0;
        int smallIndusCount = 0;
        int smallest = Integer.MAX_VALUE;
        for(Integer i : in){
            if(i < smallest){
                smallest = i;
                smallIndusCount = indusCount;
            }
            indusCount++;
        }
        return smallIndusCount;
    }
}