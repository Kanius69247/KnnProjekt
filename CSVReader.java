import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public String csvFile = "";
    private double[][] dataSet = new double[0][];


    /**
     * reading csv file and save it at dataSet
     * @param filePath path of csv file to import
     */
    public void read(String filePath){

        this.csvFile = filePath;

        String line = "";
        String csvSplitBy = ",";
        List<double[]> dataList = new ArrayList<>(); // initialize a list to store the data

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

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

        double[][] data = new double[dataList.size()-1][]; // initialize the 2D array with the size of the list

        for (int i = 0; i < dataList.size()-1; i++) {
            data[i] = dataList.get(i+1); // populate the 2D array with values from the list
        }

        this.dataSet = data;

    }

    /**
     * return 2D array of csv
     * @return dataSet
     */
    public double[][] getCSV(){
        return this.dataSet;
    }
}
