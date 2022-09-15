import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class IOPostSerializable {
	
	private static ObjectOutputStream output; // outputs data to file
	private static ObjectInputStream input;

	private static void openFile(String filepath,boolean is_read)
	   {
	      try 
	      {
	    	 if(is_read==true)
	    		input= new ObjectInputStream(
	        		 Files.newInputStream(Paths.get(filepath)));
	    	 else {
				output=new ObjectOutputStream(
						Files.newOutputStream(Paths.get(filepath)));
			}
	         
	      }
	      catch (IOException ioException)
	      {
	         System.err.println("Error opening file. Terminating.");
	         System.exit(1); // terminate the program
	      } 
	   } 
	
	private static void closeFile() 
	   {
	      try 
	      {
	         if (output != null)
	            output.close();
	         if(input!=null)
	        	 input.close();
	      } 
	      catch (IOException ioException)
	      {
	         System.err.println("Error closing file. Terminating.");
	      } 
	   } 
	
	public static PokeSerializable readRecords(String filepath)
	   {
		  PokeSerializable record=null;
		  openFile(filepath,true);
	      try 
	      {
	             record= (PokeSerializable) input.readObject();
	         
	      }
	      catch (ClassNotFoundException classNotFoundException)
	      {
	         System.err.println("Invalid object type. Terminating.");
	      } 
	      catch (IOException ioException)
	      {
	         System.err.println("Error reading from file. Terminating.");
	      } 
	      closeFile();
	      return record;
	      
	   } // end method readRecords
	
	public static void writeRecords(PokeSerializable p,String filepath)
	   {
		  openFile(filepath,false);
	      try 
	      {
	            output.writeObject(p);
	      }
	      catch (NoSuchElementException elementException)
         {
            System.err.println("Invalid input. Please try again.");
         } 
         catch (IOException ioException)
         {
            System.err.println("Error writing to file. Terminating.");
         } 
	      closeFile();
	   }
}
