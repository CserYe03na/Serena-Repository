# Project Structure

- `README.md`: The markdown file containing instructions and a project overview.

- `Data_Profiling`: Profiling code directory.
  - `UniqueRecs.java` and `UniqueRecs.class`: Code and class for generating unique records.
  - `UniqueRecsMapper.java` and `UniqueRecsMapper.class`: Code and class for a mapper in genrating unique records of `school`, `grade`, `year`.
  - `UniqueRecsReducer.java` and `UniqueRecsReducer.class`: Code and class for a reducer in genrating unique records process.
  - `uniqueRecs.jar`: jar file for data profiling.

- `Data_Cleaning`: Data cleaning code directory, removing unnecessary attributes & preparing for table joining.
    - `Clean.java` and `Clean.class`: Code and class for cleaning data.
    - `CleanMapper.java` and `CleanMapper.class`: Code and class for a mapper in the cleaning process.
    - `Clean.jar`: jar file for data cleaning.

- `Data_Ingest`: Data ingestion instructions. **Implement after data cleaning and profiling
  - `data_ingest.txt`: Text file related to data ingestion process.
  - `attendence.csv`: Original dataset in csv format, downloading from NYC Open Data: https://data.cityofnewyork.us/Education/2016-17-2020-21-School-End-of-Year-Attendance-and-/gqq2-hgxd/about_data 
  - `attendence_cleaned.csv`: Cleaned dataset in csv format after processing all data cleaning procedure above.
  - `joined/part-00000-99dcff6d-94e0-4a67-904f-7f6c15b6e0f8-c000.csv`: Joined dataset in csv format after joining my `attendence_cleaned.csv` and Yikai's `snapshot_cleaned.csv`

- `Analytics_Code`: Analytics code directory.
  - `Correlation_Analysis.txt`: General correlation analysis of the relationship between poverty and attendence, including chronic abseenteeism
  - `Year__Analysis`: Correlation analysis between poverty and attendance, including chronic abseenteeism among different year.
  - `Race_Analysis`: Correlation analysis between poverty and attendance, including chronic abseenteeism among different student's racial group.

- `Test_Code`: Directory containing testing code.
  - `FirstCode.scala`: Scala code containing pre-analysis process including basic stats: mean, median, mode calculation, and joining process.

- `Screenshots`: Directory containing screenshots.
  - `Correlation_Analysis_screenshots`: Screenshots related to correlation analysis.
  - `Year_and_Race_Analysis_screenshots`: Screenshots about Year and Race analysis.
  - `Access_Permission_screenshots`: Screenshots about setting access permissions on NYU DataProc.


# Building and running the Code

For data cleaning and profiling process, I implemented MapReduce tasks. You can find the source datasets under directories `/Data_Ingest/attendence.csv`. You can upload the code to NYU-dataproc. To complie and run the code, you can follow the instructions below. Please change the name of the code/file accordingly.

```bash
hdfs dfs -put NAME_OF_YOUR_FILE
javac -classpath `yarn classpath` -d . MAPPER_CLASS
javac -classpath `yarn classpath` -d . REDUCER_CLASS
javac -classpath `yarn classpath`:. -d . MAIN_CLASS
jar -cvf clean.jar *.class
hadoop jar clean.jar Clean NAME_OF_YOUR_FILE NAME_OF_YOUR_DIR
hdfs dfs -ls NAME_OF_YOUR_DIR
hdfs dfs -cat NAME_OF_YOUR_RESULT_DIR
```

For analysis part, we implemented MapReduce and Spark. You can follow the instructions in `/Data_Ingest/data_ingest.txt` to load the datasets. Then, you can run the correlation analysis under `/Analytics_Code`. This analysis includes MapReduce tasks. You can follow the above instructions.

# Where to find the data
- All dataset (original, cleaned, joined) can be found in the repository `/Data_Ingest`.