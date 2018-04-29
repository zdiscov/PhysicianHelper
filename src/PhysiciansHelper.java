import java.util.*;
import java.io.*;

public class PhysiciansHelper
{
	// symptom to illnesses map 
	private Map<String, List<String>> symptomChecker;


	
	/* Constructor symptomChecker map using TreeMap */
	public PhysiciansHelper()
	{ 
		// use TreeMap, i.e. sorted order keys
		symptomChecker = new TreeMap<String,List<String>>();
		
		
	} // end default constructor


	/* Reads a text file of illnesses and their symptoms.
	   Each line in the file has the form
		Illness: Symptom, Symptom, Symptom, ...  
	   Store data into symptomChecker map */

	private void printPossibleSymptoms()
	{
		System.out.println("The possible symptoms are:");
		Set<String>symptomSet = symptomChecker.keySet();
		Set<String> sortedSet = new TreeSet<String>();
		sortedSet.addAll(symptomSet);
		for(String s : sortedSet){
			System.out.println(s);
		}
	}
	public void processDatafile()
	{
		// Step 1: read in a data filename from keybaord
		//         create a scanner for the file
		try {
            System.out.print("Enter the file name with extension : ");
            Scanner input = new Scanner(System.in);
            File file = new File(input.nextLine());
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine().trim().toLowerCase();
                String[] sympDisArr= line.split(":");
                String[] symptomArray = sympDisArr[1].split(",");
                String condition = sympDisArr[0].trim().toLowerCase();
                
                for(String s : symptomArray){
                	s = s.trim().toLowerCase();
                	if(!this.symptomChecker.containsKey(s)){
                		List<String> temp = new ArrayList<String>();
                		temp.add(condition);
                		symptomChecker.put(s, temp);
                	}else{
                		List<String> contList = symptomChecker.get(s);
                		List<String> newContList = new ArrayList<String>();
                		newContList.add(condition);                		
                		newContList.addAll(contList);                		
                		symptomChecker.put(s, newContList);
                	}              		
                }
            }
            input.close();
            
            printMap(symptomChecker);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
		// Step 2: process data lines in file scanner
		// 2.1 for each line, split the line into a disease and symptoms
		//     make sure to trim() spaces and use toLowercase()
		// 2.2 for symptoms, split into individual symptom
		//     create a scanner for symptoms 
		//     useDelimeter(",") to split into individual symptoms 
		//     make sure to trim() spaces and use toLowercase()
		//     for each symptom
		//        if it is already in the map, insert disease into related list
		//        if it is not already in the map, create a new list with the disease
		// Step 3: display symptomChecker map

		// implement here.....

	} // end processDatafile



	/*  Read patient's symptoms in a line and adds them to the list.
		Input format: Symptom, Symptom, Symptom,...
	    Shows diseases that match a given number of the symptoms. */

	private List<String> processPatientSymptoms()
	{
		   List<String> symptomList = new ArrayList<>();
		   Scanner scanner = new Scanner(System.in);
		   //while (scanner.hasNext()) {
			  String input = scanner.nextLine();
			  String [] splitted = input.split(",");
			  for(String inputSympStr : splitted){
				  inputSympStr=inputSympStr.trim().toLowerCase();
				  if(!symptomChecker.containsKey(inputSympStr)){
					  System.out.println(" => invalid symptom:" + inputSympStr);
					  continue;
				  }					  
				  if(!symptomList.contains(inputSympStr)){
					  symptomList.add(inputSympStr);
				  }else{
					  System.out.println(" => duplicate symptom:" + inputSympStr);
				  }
			  
			  }
			  scanner.close();
			  return symptomList;
			  	
	}
	
	private void displayPatientSymptoms(List<String> patientSymptoms)
	{
	       //List<String> patientSymptoms = patientSymptoms;
	        
	        System.out.println("PatientSymptoms List:");
			// Step 3: display patientSymptoms list
	        for(int i = 0; i < patientSymptoms.size(); i++){
	        	if(i==0){
	        		System.out.print("[" + patientSymptoms.get(i) );
	        	}else if(i < patientSymptoms.size()-1){
	        		System.out.print(", " + patientSymptoms.get(i));
	        	}else{
	        		System.out.print(", " + patientSymptoms.get(i));
	        	}
	        }
	        System.out.print("]");
	}
	
	public void processSymptoms()
	{

		// Step 1: get all possible symptoms from assciatedIllnesses map
		//         and display them
        printPossibleSymptoms();

		// Step 2: process patient symptoms, add to patientSymptoms list 
		//         read patient's symptoms in a line, separated by ','
		//         create a scanner for symptoms 
		//         UseDelimeter(",") to split into individual symptoms 
		//         make sure to trim() spaces and use toLowercase()
		//         display invalid/duplicate symptoms
		//         add valid symptoms to patientSymptoms list
        List<String> patientSymptomList = processPatientSymptoms();
        displayPatientSymptoms(patientSymptomList);
   	        // Step 4: process illnesses to frequency count
		//         create a map of disease name and frequency count
		//         for each symptom in patientSymptoms list
		//              get list of illnesses from symptomChecker map
		//              for each illness in the list
		// 	            if it is already in the map, increase counter by 1
	        //	            if it is not already in the map, create a new counter 1
		//         ** need to keep track of maximum counter numbers
       Map<String,Integer> diseaseFreqMap = generateDiseaseToFreqMap(patientSymptomList);     		   
       Map<Integer, List<String>> freqDiseaseMap = generateFreqToDiseaseMap(diseaseFreqMap);
		// Step 5: display result
		printFreqDiseaseMap(freqDiseaseMap);

	} // end processSymptoms 

	private void printFreqDiseaseMap(Map<Integer, List<String>>freqDiseaseMap)
	{
		System.out.println("");
		System.out.println("Possible illnesses that match any symptom are:");
		for(int i = 0; i < freqDiseaseMap.size(); i++){
			List<String> diseaseList = freqDiseaseMap.get(i+1);
			System.out.println(" ==> Disease(s) match " + (i+1) + " symptom(s)");
			for(String diseaseStr : diseaseList){
				System.out.println(diseaseStr);
			}			
		}
	}
	
	private void printMap(Map<String,List<String>> map)
	{
		for(String s : map.keySet()){
			List<String> values = map.get(s);
			System.out.print(s + " --->   ");
			for(int i = 0; i < values.size(); i++){
				if(i==0)
					System.out.print("[");
				if(i != values.size()-1)
					System.out.print(values.get(i) + ", ");
				else{
					System.out.print(values.get(i));
					System.out.print("]");
				}
			}
			System.out.println(" ");
		}
	}
	
	private Map<String,Integer> generateDiseaseToFreqMap(List<String> patientSymptomList)
	{
		 Map<String,Integer> diseaseFreqMap = new HashMap<String,Integer>();
	       for(String symptom : patientSymptomList){
	    	   if(this.symptomChecker.containsKey(symptom)){
	    		   List<String> diseaseList = symptomChecker.get(symptom);
	    		   
	    		   for(String diseaseStr : diseaseList){
		    		   if(diseaseFreqMap.containsKey(diseaseStr)){
		    			   int freqCtr = diseaseFreqMap.get(diseaseStr);
		    			   freqCtr++;
		    			   diseaseFreqMap.put(diseaseStr, freqCtr);    			   
		    		   }else{
		    			   diseaseFreqMap.put(diseaseStr, 1);
		    		   }    		   
	    		   }
	    	   }
	       }
	       return diseaseFreqMap;
	}
	
	private Map<Integer,List<String>> generateFreqToDiseaseMap(Map<String,Integer> diseaseFreqMap)
	{
	    Map<Integer, List<String>> freqDiseaseMap = new TreeMap<Integer, List<String>>();
		Set<String> diseaseSet = diseaseFreqMap.keySet();
		for(String diseaseStr : diseaseSet){
			int freq = diseaseFreqMap.get(diseaseStr);
			if(freqDiseaseMap.containsKey(freq)){
				List<String> diseaseList = freqDiseaseMap.get(freq);
				diseaseList.add(diseaseStr);
				freqDiseaseMap.put(freq, diseaseList);
			}else{
				List<String> diseaseList = new ArrayList<String>();
				diseaseList.add(diseaseStr);
				freqDiseaseMap.put(freq, diseaseList);
			}			
		}
		return freqDiseaseMap;		
	}


	public static void main(String[] args)
	{

		PhysiciansHelper lookup = new PhysiciansHelper();
		lookup.processDatafile();
		lookup.processSymptoms();
	} // end main
} // end PhysiciansHelper
