import java.io.*;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class YearMapper extends Mapper<Object, Text, Text, Text>{

    private final static IntWritable one = new IntWritable(1);

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        if (line.contains("DBN")) {
            return;
        }

        String [] recd = line.split(",");
        String year = recd[1];
        double poverty = Double.parseDouble(recd[28]); 
        double attendance = Double.parseDouble(recd[32]);
        double chronAbsent = Double.parseDouble(recd[34]);
        
        context.write(new Text(year), new Text(poverty + "," + attendance + "," + chronAbsent));

    }
}