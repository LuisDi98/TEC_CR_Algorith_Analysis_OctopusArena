package provider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Player;

import java.util.ArrayList;

public class DataProvider {
    
    public static void serialize(String filePath, Player obj){
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath+ obj.getUserName()+".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved usersData/"+ obj.getUserName()+ ".ser\n");
         } catch (IOException i) {
            System.out.println("Something went wrong in serializable");
            i.printStackTrace();
         }
    }
    
    public static Player deserialize(String filePath, String userName){
            Player obj = null;
            try {
                FileInputStream fileIn = new FileInputStream(filePath + userName + ".ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                obj = (Player) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
                System.out.println("IoException in deserialize");
            } catch (ClassNotFoundException c) {
                System.out.println("User class not found in deserialize ");
                c.printStackTrace();
            }

            System.out.println("Deserialized User...");
            return obj;
    }
    
    // Get specific file line
    public static String getLine(String filePath, int lineNumber){
        try {
            String line = (Files.lines(Paths.get(filePath))).skip(lineNumber).findFirst().get();
            return line;
        } catch (IOException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("File Not found.");
            return null;
        }
    }
    
    // Write the content in file 
    public static void writeFile(String filePath, String fileText){ //Path, username.txt
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            String fileContent = fileText;
            bufferedWriter.write(fileContent);
        } catch (IOException e) {
            // Exception handling
            System.out.println("Something went wrong with the Buffer Reader.");
        }
    }
    
    // Read the content from file
    public static ArrayList<String> readFile(String filePath){
        ArrayList<String> readed = new ArrayList<String>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                readed.add(line);
                System.out.println(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
            System.out.println("File Not found.");

        } catch (IOException e) {
            // Exception handling
            System.out.println("Something went wrong with the Buffer Reader.");
        }
        return readed;
    }
    
    public static ArrayList<String> getFileNames(String filePath){
        ArrayList<String> files = new ArrayList<String>();
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {
              files.add(listOfFiles[i].getName());
            //System.out.println("File " + listOfFiles[i].getName());
          } else if (listOfFiles[i].isDirectory()) {
            //System.out.println("Directory " + listOfFiles[i].getName());
          }
        }
        return files;
    }
    

}

