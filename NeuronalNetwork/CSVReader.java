package NeuronalNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /**
     * reading csv file and save it at dataSet
     * @param filePath path of csv file to import
     */
    public static double[][] read(String filePath){
        String line = "";
        String csvSplitBy = ",";
        List<double[]> dataList = new ArrayList<>(); // initialize a list to store the data

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                String[] pre_values = line.split(csvSplitBy);
                double[] values = new double[pre_values.length];
                for (int i = 0; i < pre_values.length; i++){
                    values[i] = Double.parseDouble(pre_values[i]);
                }
                dataList.add(values); // add the values to the list

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        double[][] data = new double[dataList.size()][]; // initialize the 2D array with the size of the list

        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i); // populate the 2D array with values from the list
        }

        return data;
    }

    public static void write(String filePath, double[][] data)
    {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (int i = 0; i < data.length; i++) {
                for(int j = 0; j < data[i].length; j++)
                    writer.append(data[i][j] + ",");

                writer.append("\n");
            }
            writer.close();
        }
        catch (IOException ioEx)
        {
            System.err.println("Failed to write CSV file to "+filePath);
        }
    }
}
