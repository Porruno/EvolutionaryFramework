package CSP.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the basic functions for reading and writing to a text file.
 * ----------------------------------------------------------------------------
*/

public abstract class Files {

    /**
     * Reads a file and returns an instance of String with its contents
     * @return An instance of String with the contents of the file.
    */
    public static String loadFromFile(String fileName) {
        File file = new File(fileName);
        char[] data;
        int size = (int) file.length(), chars_read = 0, counter = 0;        
        FileReader in;                
        try {
            in = new FileReader(file);
            data = new char[size];
            while (in.ready()) {
                chars_read += in.read(data, chars_read, size - chars_read);
            }
            in.close();
            return (new String(data, 0, chars_read));
        } catch (Exception e) {
            System.out.println("An error occurred while attempting to read the file \'" + fileName + "\'.");            
            System.out.println("Exception text: " + e.toString());
            System.out.println("The system will halt.");
            System.exit(1);
        }
        return null;
    }
    
    /**
     * Saves an array of String to a file.
     * @param lines An array of String with the lines to be saved.
     * @param fileName The name of the file where the data will be saved.
    */
    public static void saveToFile(String lines[], String fileName) {
        int i;
        File f;
        FileWriter fw;
        try {
            f = new File(fileName);
            fw = new FileWriter(f);
            for (i = 0; i < lines.length; i++) {
                fw.write(lines[i] + "\r\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to save the file \'" + fileName + "\'.");            
            System.out.println("Exception text: " + e.toString());
            System.out.println("The system will halt.");
            System.exit(1);
        }
    }
    
    public static void saveToFile(String text, String fileName, boolean append) {
        File f;
        FileWriter fw;
        try {
            f = new File(fileName);            
            fw = new FileWriter(f, append);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to save the file \'" + fileName + "\'.");            
            System.out.println("Exception text: " + e.toString());
            System.out.println("The system will halt.");
            System.exit(1);
        }                
    }        
    
    /**
     * Lists all the files within a folder.
     * @param folderName The name of the folder to explore.
     * @return 
    */
    public static String[] listAllFilesInFolder(String folderName) {
        File folder = new File(folderName);        
        if (!folder.isDirectory()) {
            System.out.println("\'" + folderName + "\' is not a directory.\n The system will halt.");
            System.exit(1);
        }
        return folder.list();        
    }

}
