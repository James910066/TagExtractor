import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {

        JFileChooser fileChooser = new JFileChooser();
        File chosenFile = null;
        String readLine = "";
        ArrayList<String> stopWords = new ArrayList<>();
        Map<String, Integer> wordFrequency = new TreeMap<>();
        Set<String> keyWords = new TreeSet<>();
        Scanner in = new Scanner(System.in);

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(workingDirectory);

            // Choose and read the file with stop words
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                chosenFile = fileChooser.getSelectedFile();
                Path file = chosenFile.toPath();
                InputStream stream = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(stream));

                while (fileReader.ready()) {
                    readLine = fileReader.readLine();
                    stopWords.add(readLine);
                }

                System.out.println("Extracted stop words from: " + chosenFile.getName());
                fileReader.close();
                System.out.println("\n\nData file read!");
            } else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }

            // Choose and read the second file with keywords
            fileChooser.setCurrentDirectory(workingDirectory);
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                chosenFile = fileChooser.getSelectedFile();
                Path file = chosenFile.toPath();
                InputStream stream = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(stream));

                while (fileReader.ready()) {
                    readLine = fileReader.readLine();
                    String[] words = readLine.split("\\s+");
                    for (String word : words) {
                        word = word.toLowerCase(); // Convert word to lowercase
                        // Exclude words with digits or punctuation
                        if (!stopWords.contains(word) && word.matches("\\p{Alpha}+")) {
                            keyWords.add(word);
                        }
                    }
                }

                System.out.println("Extracted files from: " + chosenFile.getName());
                fileReader.close();
                System.out.println("\n\nData file read!");
            } else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }

            // Count frequency of keywords in the second file
            for (String keyword : keyWords) {
                int frequency = 0;
                for (String line : Files.readAllLines(chosenFile.toPath())) {
                    frequency += Arrays.asList(line.split("\\s+")).stream()
                            .filter(word -> word.toLowerCase().equals(keyword)).count();
                }
                wordFrequency.put(keyword, frequency);
            }

            // Display the tags and their frequency in a JTextArea
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                textArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
            }

            JScrollPane scrollPane = new JScrollPane(textArea);
            JFrame frame = new JFrame("Tag Frequencies");
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.setSize(400, 300);
            frame.setVisible(true);

            // Allow user to save the extracted tags and frequencies
            JButton saveButton = new JButton("Save to File");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser saveFileChooser = new JFileChooser();
                    if (saveFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File saveFile = saveFileChooser.getSelectedFile();
                        try (PrintWriter writer = new PrintWriter(saveFile)) {
                            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                                writer.println(entry.getKey() + ": " + entry.getValue());
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });

            JButton quitButton = new JButton("Quit");
            quitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(saveButton);
            buttonPanel.add(quitButton);

            frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
