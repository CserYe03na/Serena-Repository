import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*;

public class YearReducer extends Reducer<Text, Text, Text, Text>{

    List<Double> povertyList = new ArrayList<>();
    List<Double> attendanceList = new ArrayList<>();
    List<Double> chronAbsentList = new ArrayList<>();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text val : values) {
            String[] parts = val.toString().split(",");
            double poverty = Double.parseDouble(parts[0]);
            double attendance = Double.parseDouble(parts[1]);
            double chronAbsent = Double.parseDouble(parts[2]);
            povertyList.add(poverty);
            attendanceList.add(attendance);
            chronAbsentList.add(chronAbsent);
        }

        double cor1 = correlation(povertyList, attendanceList);
        double cor2 = correlation(povertyList, chronAbsentList);
        context.write(key, new Text(String.valueOf(cor1) + "," + String.valueOf(cor2)));

    }

    private double correlation(List<Double> x, List<Double> y) {
        double meanX = mean(x);
        double meanY = mean(y);
        double covariance = covariance(x, y, meanX, meanY);
        double stdDevX = standardDeviation(x, meanX);
        double stdDevY = standardDeviation(y, meanY);
        return covariance / (stdDevX * stdDevY);
    }

    private double mean(List<Double> data) {
        double sum = 0.0;
        for (double a : data) {
            sum += a;
        }
        return sum / data.size();
    }

    private double covariance(List<Double> x, List<Double> y, double meanX, double meanY) {
        double covariance = 0.0;
        for (int i = 0; i < x.size(); i++) {
            covariance += (x.get(i) - meanX) * (y.get(i) - meanY);
        }
        return covariance / x.size();
    }

    private double standardDeviation(List<Double> data, double mean) {
        double sum = 0.0;
        for (double a : data) {
            sum += (a - mean) * (a - mean);
        }
        return Math.sqrt(sum / data.size());
    }

}