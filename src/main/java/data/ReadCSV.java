package data;

import org.json.JSONObject;

import javax.jms.JMSException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ReadCSV {
    private Scanner scanner = null;

    void createCSV(ArrayList<String> arrList) {
        String pipe = "|";
        String separator = "\t";
        String newLine = "\n";
        String path = "csv/";
        String myFile = "";

        FileWriter fileWriter = null;
        try {
            scanner = new Scanner(System.in);
            System.out.println("Create a csv file must end with .csv extension");
            System.out.println("Enter filename");
            System.out.println();
            String fileName = scanner.nextLine();
            if (fileName.equals("")) {
                System.out.println("Please enter a file name");
                return;
            } else {
                fileWriter = new FileWriter(path.concat(fileName));
                myFile = path.concat(fileName);
                System.out.println(myFile);
                scanner.close();
            }

            Collections.shuffle(arrList);
            for (int a = 0; a <= 99; a++) {
                for (String aList : arrList) {
                    fileWriter.append(separator);
                    fileWriter.append(aList);
                    fileWriter.append(pipe);
                }
                fileWriter.append(newLine);
            }
            fileWriter.flush();
            fileWriter.close();
            readCSV(myFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readCSV(String filepath) throws FileNotFoundException {
        scanner = new Scanner(new File(filepath));
        while (scanner.hasNext()) {
            String data = scanner.nextLine();
            String s = data.replaceAll("\\|", "").trim();

            Pattern whitespace = Pattern.compile("\\s");
            Matcher matcher = whitespace.matcher(s);
            String result = "";
            if (matcher.find()) {
                result = matcher.replaceAll("");
            }

            Character lettr = result.charAt(4);
            if (result.length() == 12 && (lettr == 'C' || lettr == 'G')) {
                System.out.println("Saving data to the database....");
                JDBC jdbc = new JDBC();
                jdbc.writeJDBC(result.charAt(0), result.charAt(1), result.charAt(2), result.charAt(3), result.charAt(4), result.charAt(5),
                        result.charAt(6), result.charAt(7), result.charAt(8), result.charAt(9), result.charAt(10), result.charAt(11));

            } else {

                System.out.println("Logging data to ActiveMQ...");
                ActiveMQ activeMQ = new ActiveMQ();
                System.out.println(result.charAt(2));
                try {
                    JSONObject jsonObject = new JSONObject()
                            .put("character1", Character.valueOf(result.charAt(0)))
                            .put("character2", Character.valueOf(result.charAt(1)))
                            .put("character3", Character.valueOf(result.charAt(2)))
                            .put("character4", Character.valueOf(result.charAt(3)))
                            .put("character5", Character.valueOf(result.charAt(4)))
                            .put("character6", Character.valueOf(result.charAt(5)))
                            .put("character7", Character.valueOf(result.charAt(6)))
                            .put("character8", Character.valueOf(result.charAt(7)))
                            .put("character9", Character.valueOf(result.charAt(8)))
                            .put("character10", Character.valueOf(result.charAt(9)))
                            .put("character11", Character.valueOf(result.charAt(10)))
                            .put("character12", Character.valueOf(result.charAt(11)));

                    activeMQ.submit(jsonObject.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        ActiveMQ activeMQ = new ActiveMQ();
        activeMQ.usingListener();
    }

    void processedCSV(String pList) {
        String pipe = "|";
        String separator = "\t";
        String newLine = "\n";
        String path = "csv/processed.csv";

        FileWriter fileWriter = null;

        System.out.println("Writing to a csv file(processed.csv)");
        try {
            fileWriter = new FileWriter(path, true);
            fileWriter.append(separator);
            fileWriter.append(pList);

            fileWriter.append(newLine);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
