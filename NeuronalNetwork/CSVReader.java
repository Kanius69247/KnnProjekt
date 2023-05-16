package NeuronalNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    public static double[][] readDataCsv(String filePath)
    {
        double[][] result = new double[0][];

        String line;
        String csvSplitBy = ";";
        List<double[]> dataList = new ArrayList<>(); // initialize a list to store the data

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                line = line.replaceAll("\\s+","");

                String[] splits = line.split(csvSplitBy);
                double[] values = new double[splits.length];

                for (int i = 0; i < splits.length; i++){
                    values[i] = Double.parseDouble(splits[i]);
                }
                dataList.add(values); // add the values to the list

            }
        } catch (IOException ex) {
            System.err.println("failed to read data csv, "+ex);
        }

        result = dataList.toArray(result);
        return result;
    }

    public static int[] readStructureFromCSV(String filePath)
    {
        int[] result = new int[0];

        String csvSplitBy = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            //just read first line because there is the structure defined
            String line = br.readLine();
            line = line.replaceAll("\\s+", ""); //remove all whitespaces
            line = line.replace("layers;", ""); //remove "layers;" prefix

            String[] splits = line.split(csvSplitBy);
            result = new int[splits.length];

            for (int i = 0; i < splits.length; i++) {
                result[i] = Integer.parseInt(splits[i]);
            }

        }
        catch (Exception ex)
        {
            System.err.println("failed to read structure from csv, " +ex);
        }

        return result;
    }

    public static double[][] readInputValuesFromDataArray(double[][] data, int[] structure)
    {
        int inputNeuronCount = structure[0];

        double[][] input = new double[data.length][inputNeuronCount];

        for(int i = 0; i < data.length; i++)
        {
            for(int j = 0; j < data[i].length; j++)
            {
                if(j < inputNeuronCount)
                    input[i][j] = data[i][j];
            }
        }

        return input;
    }

    public static double[][] readOutputValuesFromDataArray(double[][] data, int[] structure)
    {
        int inputNeuronCount = structure[0];
        int outputNeuronCount = structure[structure.length-1];
        double[][] output = new double[data.length][outputNeuronCount];

        for(int i = 0; i < data.length; i++)
        {
            for(int j = inputNeuronCount; j < data[i].length; j++)
            {
                output[i][j-inputNeuronCount] = data[i][j];
            }
        }

        return output;
    }

    public static double[][][] readWeightsFromCSV(String filePath)
    {
        //Skip first line because of structure array
        double[][][] result;
        String line;
        String csvSplitBy = ";";
        List<List<double[]>> dataList = new ArrayList<>();
        List<double[]> subList = new ArrayList<>(); // initialize a list to store the data

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();//Ignore first line beacuse it is the layers; line
            while ((line = br.readLine()) != null) {
                List<double[]> weightsOfNeurons = new ArrayList<>();

                line = line.replaceAll("\\s+","");

                String[] splits = line.split(csvSplitBy);
                double[] values = new double[splits.length];

                for (int i = 0; i < splits.length; i++){
                    values[i] = Double.parseDouble(splits[i]);
                }

                if(values.length == 0)//Wenn leerzeile neue zwischen Liste für neu array dimension
                {
                    dataList.add(subList); // add the values to the list
                    subList = new ArrayList<>();
                }
                else
                    subList.add(values);
            }
            dataList.add(subList);
        } catch (IOException ex) {
            System.err.println("failed to read weights csv, "+ex);
        }

        //Transform List<List<double[]>> to double[][][]
        result = new double[dataList.size()][][];

        for(int i = 0; i < dataList.size(); i++) {
            result[i] = new double[dataList.get(i).size()][];
            result[i] = dataList.get(i).toArray(result[i]);
        }

        return result;
    }

    /**
     * reading csv file and save it at dataSet
     * @param filePath path of csv file to import
     */
    public static double[][] read(String filePath){
        String line = "";
        String csvSplitBy = ";";
        List<double[]> dataList = new ArrayList<>(); // initialize a list to store the data

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                line = line.replaceAll("\\s+","");
                line = line.replace("layers;", "");
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

    /**
     * formats a dataset (type double[][]) to a dataset (type double[][][]), which equals the weights dataset used in the NeuronalNetwork class
     * the input (data) has to be extracted out of a csv file which has the special neural net format given in our lecture
     * @param data dataset which was read in by the read() method
     * @return dataset that equals the weights dataset used in the NeuronalNetwork class
     */
    public static double[][][] formatWeightMatrix(double[][] data) {
        int number_of_weight_arrays = 0;
        for(int i = 0; i < data[0].length - 1; i++) {
            number_of_weight_arrays += (int)data[0][i];
        }

        double[][] csv_weights = new double[number_of_weight_arrays][];
        double[][] csv_biases = new double[data[0].length - 1][];

        int bias_index = 0;
        for(int i = 0; i < data[0].length - 1; i++) {
            bias_index += (data[0][i] + 2);
            csv_biases[i] = data[bias_index - 1];
        }

        int csv_weight_index = 0;
        int weight_index = 0;
        for(int i = 0; i < data[0].length - 1; i++) {
            weight_index++;
            for(int j = 0; j < data[0][i]; j++) {
                csv_weights[csv_weight_index] = data[weight_index];
                csv_weight_index++;
                weight_index++;
            }
            weight_index++;
        }

        double[][][] formatted_weights = new double[data[0].length - 1][][];
        double[][] tmp;
        bias_index = 0;
        weight_index = 0;
        for(int i = 0; i < formatted_weights.length; i++) {
            tmp = new double[(int)data[0][i] + 1][];
            for(int j = 0; j < tmp.length; j++) {
                if(i == formatted_weights.length - 1 && j < tmp.length - 1) {
                    tmp[j] = csv_weights[weight_index];
                    weight_index++;
                    continue;
                }
                else if((i == formatted_weights.length - 1 && j == tmp.length - 1)) {
                    tmp[j] = csv_biases[bias_index];
                    bias_index++;
                    break;
                }
                else if(j == tmp.length - 1) {
                    tmp[j] = getPlusOneValueArray(csv_biases[bias_index]);
                    bias_index++;
                    break;
                }
                tmp[j] = getPlusOneValueArray(csv_weights[weight_index]);
                weight_index++;
            }
            formatted_weights[i] = tmp;
        }
        return formatted_weights;
    }

    /**
     * formats a dataset (type double[][]) to a dataset (type double[][][])
     * the input (data) has to be extracted out of a csv file which has the special result format given in our lecture
     * @param results dataset which was read in by the read() method
     * @param structure dataset containing the neural nets structure
     * @return dataset that contains all input output pairs in a three-dimensional array
     */
    public static double[][][] formatResultMatrix(int[] structure, double[][] results) {
        int in = structure[0];
        double[][][] formatted_results = new double[results.length][2][];
        double[][] tmp;

        for(int i = 0; i < formatted_results.length; i++) {
            tmp = split(in, results[i]);
            formatted_results[i][0] = tmp[0];
            formatted_results[i][1] = tmp[1];
        }
        return formatted_results;
    }

    private static double[][] split(int index, double[] array) {
        double[][] splittedArray = new double[2][];
        double[] input_array = new double[index];
        double[] output_array = new double[array.length - index];
        for(int i = 0; i < input_array.length; i++) {
            input_array[i] = array[i];
        }
        for(int i = 0; i < output_array.length; i++) {
            output_array[i] = array[i + index];
        }
        splittedArray[0] = input_array;
        splittedArray[1] = output_array;
        return splittedArray;
    }

    private static double[] getPlusOneValueArray(double[] data) {
        double[] newData = new double[data.length + 1];
        for(int i = 0; i < newData.length; i++) {
            if(i == newData.length - 1) {
                newData[i] = 1.0;
                break;
            }
            newData[i] = data[i];
        }
        return newData;
    }

}
