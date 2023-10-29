import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files; //Imports file. Uses built in methods for working the actual files and directories and creating input and output streams
import java.nio.file.Path; //Imports the path. Needed for creation of hierarchy of file path. Provides info about file location, size, type and permissions etc.
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.*;
import javax.swing.JFileChooser;
public class Main
{
    public static void main(String[] args) {

        JFileChooser fileChooser = new JFileChooser();
        File chosenFile;
        String readLine = "";
        ArrayList<String> stopWords = new ArrayList<>();
        Map<String, Integer> wordFrequency = new TreeMap<>();
        int lineCounter = 0;
        Set<String> keyWords = new TreeSet<>();
        boolean done = false;
        boolean finalConfirm = false;
        Scanner in = new Scanner(System.in);



            try //Start of Try block with program in it
            {
                File workingDirectory = new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(workingDirectory);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    chosenFile = fileChooser.getSelectedFile();
                    Path file = chosenFile.toPath();
                    InputStream stream = new BufferedInputStream(Files.newInputStream(file, CREATE));
                    BufferedReader fileReader = new BufferedReader(new InputStreamReader(stream));
                    final String fileName = chosenFile.getName();

                    //Read the Stop Words file into an array to check against
                    while (fileReader.ready()) {
                        readLine = fileReader.readLine();
                        stopWords.add(readLine);
                    }
                    for (String w : stopWords) {
                        System.out.println(w);
                    }

                    System.out.println("Extracted files from: " + fileName);
                    fileReader.close();
                    System.out.println("\n\nData file read!");
                }
                else
                {
                    System.out.println("Failed to choose a file to process");
                    System.out.println("Run the program again!");
                    System.exit(0);
                }

                //Choose and read the article to check for keyWords into an array
                new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(workingDirectory);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    chosenFile = fileChooser.getSelectedFile();
                    Path file = chosenFile.toPath();
                    InputStream stream = new BufferedInputStream(Files.newInputStream(file, CREATE));
                    BufferedReader fileReader = new BufferedReader(new InputStreamReader(stream));
                    final String fileName = chosenFile.getName();

                    //Read the Stop Words file into an array to check against
                    while (fileReader.ready()) {
                        readLine = fileReader.readLine();
                        keyWords.add(readLine);
                    }
                    //Remove stop words and add keywords into array
                    for (String w : keyWords)
                    {
                        
                        System.out.println(w);
                    }

                    System.out.println("Extracted files from: " + fileName);
                    fileReader.close();
                    System.out.println("\n\nData file read!");
                }
                else
                {
                    System.out.println("Failed to choose a file to process");
                    System.out.println("Run the program again!");
                    System.exit(0);
                }
            }

            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("Do you want to quit?");
            finalConfirm = SafeInput.getYNConfirm(in, "Are you sure?");
            if (finalConfirm)
            {
                System.exit(0);
            }

    }
}
