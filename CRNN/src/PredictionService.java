import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PredictionService {

    public static void predict(String filepath, String modelpath) {

        Path file = Paths.get(filepath);
        Path model = Paths.get(modelpath);

        try {

            List<String> rawInputs = Files.readAllLines(file);
            List<String> rawWeights = Files.readAllLines(model);

            List<List<Double>> inputs = new ArrayList<>();
            for (int i = 0; i < rawInputs.size(); i++) {
                String[] split = rawInputs.get(i).split(",");
                List<Double> line = new ArrayList<>();
                for (String s : split) {
                    double val = Double.valueOf(s);
                    line.add(val);
                }
                inputs.add(line);
            }

            String[] splitWeights = rawWeights.get(0).split(",");
            double[] weights = new double[splitWeights.length];
            for (int i = 0; i < weights.length; i++) {
                double val = Double.valueOf(splitWeights[i]);
                weights[i] = val;
            }

            for (int i = 0; i < inputs.size(); i++) {
                double current = 0;
                for (int j = 0; j < weights.length; j++) {
                    current = current + (inputs.get(i).get(j) * weights[j]);
                }
                System.out.println("[line " + (i + 1) + "]: " + current);
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}