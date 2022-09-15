import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import sun.print.PSPrinterJob.PluginPrinter;

public class IOTextFile {
	
	private static Formatter output; // outputs text to a file 
	private static Scanner input;

	private static void openFile(String filepath,boolean is_read)
	   {
	      try
	      {
	    	 if(is_read==false)
	    		 output = new Formatter(filepath); // open the file
	    	 else
	    	 	input = new Scanner(Paths.get(filepath)); 
	      }
	      catch (SecurityException securityException)
	      {
	         System.err.println("Write permission denied. Terminating.");
	         System.exit(1); // terminate the program
	      } 
	      catch (FileNotFoundException fileNotFoundException)
	      {
	         System.err.println("Error opening file. Terminating.");
	         System.exit(1); // terminate the program
	      } 
	      catch (IOException ioException)
	      {
	          System.err.println("Error opening file. Terminating.");
	          System.exit(1);
	       } 
	   } 
	
	 private static void closeFile()
	   {
	      if (output != null)
	         output.close();
	      if (input != null)
	          input.close();
	   } 
	 
	// read record from file
	   public static  Map<String, Integer> readRecords(String filepath)
	   {
		  openFile(filepath,true);
		  Map<String, Integer> gamedata=new LinkedHashMap<String, Integer>();
		  try 
	      {
	         while (input.hasNext()) // while there is more to read
	         {
	        	String str = input.nextLine();
	        	String tmp[]=str.split(" ");
	        	if(tmp.length==1)
	        		gamedata.put(tmp[0], 0);
	        	else {
	        		gamedata.put(tmp[0], Integer.parseInt(tmp[1]));
				}
	         }
	      } 
	      catch (NoSuchElementException elementException)
	      {
	         System.err.println("File improperly formed. Terminating.");
	      } 
	      catch (IllegalStateException stateException)
	      {
	         System.err.println("Error reading from file. Terminating.");
	      } 
		  closeFile();
		  return gamedata;
	   } // end method readRecords
	   
	   
	// add records to file
	   public static void addRecords(String str,String filepath)
	   {
		 openFile(filepath,false);
         try
         {
            output.format("%s",str);                             
         } 
         catch (FormatterClosedException formatterClosedException)
         {
            System.err.println("Error writing to file. Terminating.");
         } 
         catch (NoSuchElementException elementException)
         {
            System.err.println("Invalid input. Please try again.");
         } 
         closeFile();
	   }
}
