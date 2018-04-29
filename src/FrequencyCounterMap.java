import java.util.*;
import java.io.*;

class FrequencyCounter
{
    private Map< String, Integer > wordTable;

    public FrequencyCounter ()
    {
        wordTable = new HashMap< String, Integer > ();
    } // end default constructor


    /** Task: Reads a text file of words and counts their frequencies
     *of occurrence.
     *@paramdataatext scanner for the text file of data */
    public void readFile (Scanner data)
    {
        data.useDelimiter ("\\W+"); // skip non letter/digit/underscore chars
        while (data.hasNext ())
        {
           String nextWord = data.next ();
           nextWord = nextWord.toLowerCase ();
           Integer frequency = wordTable.get(nextWord);
           if (frequency == null)
           { // add new word to table
             wordTable.put(nextWord, new Integer (1));
           } else { 
	     // increment count of existing word; replace wordTable entry
             frequency++;
             wordTable.put(nextWord, frequency);
           } // end if
        } // end while
      } // end readFile

      /** Task: Displays words and their frequencies of occurrence. */
      public void display ()
      {
          Set<Map.Entry<String, Integer>> entrySet = wordTable.entrySet();

          for (Map.Entry<String, Integer> entry : entrySet) 
	      System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
    
      } // end display


    public static void main (String [] args)
    {
        FrequencyCounter wordCounter = new FrequencyCounter ();
        Scanner s = new Scanner(System.in); // for keyboard
        Scanner data = null; // for data file
        System.out.print("Input data filename:");
        String fileName = s.next(); // read data filename
        try
        {
            data = new Scanner (new File (fileName));
            wordCounter.readFile (data);
        }
        catch (FileNotFoundException e)
        {
          System.out.println ("File not found: " + e.getMessage ());
        }
	finally 
        {
	  s.close();
          data.close();
        }
        wordCounter.display ();
    } // end main
} // end FrequencyCounter
