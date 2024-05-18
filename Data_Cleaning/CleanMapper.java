import java.io.*;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanMapper extends Mapper<Object, Text, Text, Text>{

    private final static IntWritable one = new IntWritable(1);

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        if (line.contains("DBN")) { // Skip the original header line
            context.write(new Text("DBN,Year,DaysAbsent,DaysPresent,Attendance,ChronicallyAbsentNum,ChronicallyAbsentPerct"), new Text());
            return;
        }

        String [] recd = line.split(",");
        int i = 2;
        String DBN = recd[0];
        String school = recd[1];
        String grade = recd[2];
        String year = recd[4];
        String total_day = recd[5];
        String num_abs = recd[6];
        String attendance = recd[8];
        String num_chro_abs = recd[10];
        String perc_chro_abs = recd[11];

        if(school.startsWith("\"")){
            school = school + "," + recd[i];
            while(!school.substring(school.length()-1, school.length()).equals( "\"")) {
                i++;
                school = school + "," + recd[i];
            }
            school = school.substring(1, school.length()-1);
            grade = recd[i+1];
            year = recd[i+3];
            total_day = recd[i+4];
            num_abs = recd[i+5];
            attendance = recd[i+7];
            num_chro_abs = recd[i+9];
            perc_chro_abs = recd[i+10]; 
        }
        
        if(!year.toString().equals("2016-17") && !num_abs.toString().equals("s") && grade.equals("All Grades")){
            context.write(new Text(DBN + "," + year + "," + total_day + "," + num_abs + "," + attendance + "," + num_chro_abs + "," + perc_chro_abs), new Text());
        }

    }
}