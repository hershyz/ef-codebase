import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Neuron {

    public static List<double[]> inputs = new ArrayList<>();
    public static List<Double> outputs = new ArrayList<>();
    public static double[] weights;
    public static double intercept = 0;
    private static long startTime;

    //Constructor:
    public Neuron(List<double[]> passedInputs, List<Double> passedOutputs) {

        //Fills inputs:
        for (int i = 0; i < passedInputs.size(); i++) {
            inputs.add(passedInputs.get(i));
        }

        //Fills outputs:
        for (int i = 0; i < passedOutputs.size(); i++) {
            outputs.add(passedOutputs.get(i));
        }

        //Finds baseline intercept through average of outputs:
        for (int i = 0; i < outputs.size(); i++) {
            intercept = intercept + outputs.get(i);
        }
        intercept = intercept / outputs.size();
        intercept = intercept * 0;

        //Fills weights:
        weights = new double[passedInputs.get(0).length];
        Arrays.fill(weights, 0);
        for (int i = 0; i < passedInputs.size(); i++) {
            for (int j = 0; j < passedInputs.get(i).length; j++) {
                if (passedInputs.get(i)[j] == 0) {
                    weights[j] = weights[j] + (0.5 / passedInputs.get(i).length);
                }
                if (passedInputs.get(i)[j] != 0) {
                    weights[j] = weights[j] + ((outputs.get(i) - intercept) / inputs.get(i)[j]) / inputs.get(0).length;
                }
            }
        }
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] / passedInputs.size();
        }

        //Displays intercept and starting weights:
        System.out.println("");
        System.out.println("Intercept: " + intercept);
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Starting weights[" + i + "]: " + weights[i]);
        }
        System.out.println("");
    }

    //Train:
    public static void train(double errorMargin) {

        startTime = System.nanoTime();
        int passedCycles = 0;

        //Trains until the program self determines that it is finished training:
        boolean trainingFinished = false;
        while (!trainingFinished) {

            int row = 0;
            int column = 0;
            for (row = 0; row < inputs.size(); row++) {

                //Calculates test value:
                double test = intercept;
                double real = outputs.get(row);
                for (column = 0; column < inputs.get(row).length; column++) {
                    test = test + (inputs.get(row)[column] * weights[column]);
                }

                //Terminates if value is within error margin:
                double min = real - errorMargin;
                double max = real + errorMargin;
                if (test >= min) {
                    if (test <= max) {
                        System.out.print("(Line " + (row + 1) + "): ");
                        System.out.println("Prediction: " + test + ", Real: " + real);
                        trainingFinished = true;
                        break;
                    }
                }

                //Adjusts weights according to error:
                double error = test - real;
                for (int i = 0; i < weights.length; i++) {
                    error = (error * inputs.get(row)[i]) / 100000000;
                    weights[i] = weights[i] - error;
                }

                passedCycles++;
            }
        }

        long elapsedTime = System.nanoTime() - startTime;
        elapsedTime = elapsedTime / 1000000;

        //Displays final weights:
        System.out.println("Training finished according to error margin. Epochs completed: " + passedCycles);
        System.out.println("Time elapsed: " + elapsedTime + " ms");
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Final Weights[" + i + "]: " + weights[i]);
        }
        System.out.println("");
    }

    //Return weights:
    public static double[] getWeights() {
        return weights;
    }
}
