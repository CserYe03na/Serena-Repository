import java.io.*;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UniqueRecsMapper extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String [] recd = line.split(",");
        if(recd[0].toString().equals("DBN")){
            return;
        }
        int i = 2;
        String DBN = recd[0];
        String school = recd[1];
        String grade = recd[2];
        String year = recd[4];

        if(school.startsWith("\"")){
            school = school + "," + recd[i];
            while(!school.substring(school.length()-1, school.length()).equals( "\"")) {
                i++;
                school = school + "," + recd[i];
            }
            school = school.substring(1, school.length()-1);
            grade = recd[i+1];
            year = recd[i+3]; 
        }

        context.write(new Text("Number of Recs"), one);
        context.write(new Text(DBN), new IntWritable());
        context.write(new Text(school), new IntWritable());
        context.write(new Text(grade), new IntWritable());
        context.write(new Text(year), new IntWritable());

    }
}