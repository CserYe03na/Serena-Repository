import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class UniqueRecsReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        Integer count = 0;

        for (IntWritable v : values) {
            count += v.get();
        }
        if(key.toString().equals("Number of Recs")){
            context.write(key, new IntWritable(count));
        }
        else{
            context.write(key, new IntWritable(1));
        }

    }
}