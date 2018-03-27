	import java.io.*;
	import java.util.*;
	
	import org.apache.hadoop.fs.Path;
	import org.apache.hadoop.filecache.DistributedCache;
	import org.apache.hadoop.conf.*;
	import org.apache.hadoop.io.*;
	import org.apache.hadoop.mapred.*;
	import org.apache.hadoop.util.*;
	
	public class WordCount extends Configured implements Tool {
	
	   public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	
	     static enum Counters { INPUT_WORDS }
	
	     private final static IntWritable one = new IntWritable(1);
	     private Text word = new Text();
	
	     private boolean caseSensitive = true;
	     private Set<String> patternsToSkip = new HashSet<String>();
	
	     private long numRecords = 0;
	     private String inputFile;
	
	     public void configure(JobConf job) {
	       caseSensitive = job.getBoolean("wordcount.case.sensitive", true);
	       inputFile = job.get("map.input.file");
	
	       if (job.getBoolean("wordcount.skip.patterns", false)) {
	         Path[] patternsFiles = new Path[0];
	         try {
	           patternsFiles = DistributedCache.getLocalCacheFiles(job);
	         } catch (IOException ioe) {
	           System.err.println("Caught exception while getting cached files: " + StringUtils.stringifyException(ioe));
	         }
	         for (Path patternsFile : patternsFiles) {
	           parseSkipFile(patternsFile);
	         }
	       }
	     }
	
	     private void parseSkipFile(Path patternsFile) {
	       try {
	         BufferedReader fis = new BufferedReader(new FileReader(patternsFile.toString()));
	         String pattern = null;
	         while ((pattern = fis.readLine()) != null) {
	           patternsToSkip.add(pattern);
	         }
	       } catch (IOException ioe) {
	         System.err.println("Caught exception while parsing the cached file '" + patternsFile + "' : " + StringUtils.stringifyException(ioe));
	       }
	     }
	
	     public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	       	String line = (caseSensitive) ? value.toString() : value.toString().toLowerCase();
					String[] wordPatterns = {"is", "of", "on", "in", "to", "an", "with", "without", 
					"we", "they", "how", "what", "when", "it", "who", "ok", "okay", "do", "does",
					"can", "cant", "dont", "doesnt", "want", "need", "from", "that", "these", "this"};

					 for(int i = 33; i < 256; i++){
						 	if((i > 64 && i < 91) || (i > 96 && i < 123)){
								char newChar = (char)i;
					 			String newPattern = " " + newChar + " ";

								line = line.replaceAll(newPattern, " ");
							 }else if(i > 47 && i < 58){
								 	char newChar = (char)i;
					 				String newPattern = "" + newChar;

									line = line.replaceAll(newPattern, "");
							 }else{
								char newChar = (char)i;
								String newPattern = "\\" + newChar;

							 	line = line.replaceAll(newPattern, "");
							 }

							 for(int j = 0; j < wordPatterns.length - 1; j++){ 
								 int posicionPalabra = 0;
								 boolean replaceWord = true;

								 if(line.indexOf(wordPatterns[j]) != -1){
									 posicionPalabra = line.indexOf(wordPatterns[j]);

									 for(int k = 0; k < wordPatterns[j].length(); k++){
										 System.out.println("Primera palabra: " + wordPatterns[j].charAt(k));
										 System.out.println("Segunda palabra: " + line.charAt(posicionPalabra + k));
										 System.out.println();
										 if(wordPatterns[j].charAt(k) != line.charAt(posicionPalabra + k)){
											 replaceWord = false;

											 System.out.println("Entre al falso");
											 System.out.println("Primera palabra: " + wordPatterns[j].charAt(k));
										 	 System.out.println("Segunda palabra: " + line.charAt(posicionPalabra + k));
										 	 System.out.println();
										 }
									 }

									 //revisar que la palabra no tenga nada antes y nada despues
									 //puede estar al inicio como "is ";
									 //al final como " on";
									 //o en medio como " of ";

									 if(replaceWord){
										 System.out.println();
										 System.out.println("Entre a reemplazar: " + wordPatterns[j]);
										 line = line.replaceAll(wordPatterns[j], "PEREZA");
										 System.out.println("La linea es: " + line);
									 }
								 }
							 }
					 }

	       StringTokenizer tokenizer = new StringTokenizer(line);
				 String tempToken = "";
	       while (tokenizer.hasMoreTokens()) {
					 String nextToken = tokenizer.nextToken();
					 
					 //primera palabra
	         word.set(nextToken);
	         output.collect(word, one);
	         reporter.incrCounter(Counters.INPUT_WORDS, 1);

					 if(tokenizer.hasMoreTokens()){
							tempToken = nextToken;
						 	nextToken = tokenizer.nextToken();

							//dos palabras
					 		word.set(tempToken + " " + nextToken);
					 		output.collect(word, one);
						 	reporter.incrCounter(Counters.INPUT_WORDS, 1);

							if(tokenizer.hasMoreTokens()){
								String tempToken2 = tempToken;
								tempToken = nextToken;
								nextToken = tokenizer.nextToken();

								//tres palabras
								word.set(tempToken2 + " " + tempToken + " " + nextToken);
					 			output.collect(word, one);
						 		reporter.incrCounter(Counters.INPUT_WORDS, 1);
							}
					 }
	       }
	
	       if ((++numRecords % 100) == 0) {
	         reporter.setStatus("Finished processing " + numRecords + " records " + "from the input file: " + inputFile);
	       }
	     }
	   }
	
	   public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
	     public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	       int sum = 0;
	       while (values.hasNext()) {
	         sum += values.next().get();
	       }
	       output.collect(key, new IntWritable(sum));
	     }
	   }
	
	   public int run(String[] args) throws Exception {
	     JobConf conf = new JobConf(getConf(), WordCount.class);
	     conf.setJobName("wordcount");
	
	     conf.setOutputKeyClass(Text.class);
	     conf.setOutputValueClass(IntWritable.class);
	
	     conf.setMapperClass(Map.class);
	     conf.setCombinerClass(Reduce.class);
	     conf.setReducerClass(Reduce.class);
	
	     conf.setInputFormat(TextInputFormat.class);
	     conf.setOutputFormat(TextOutputFormat.class);
	
	     List<String> other_args = new ArrayList<String>();
	     for (int i=0; i < args.length; ++i) {
	       if ("-skip".equals(args[i])) {
	         DistributedCache.addCacheFile(new Path(args[++i]).toUri(), conf);
	         conf.setBoolean("wordcount.skip.patterns", true);
	       } else {
	         other_args.add(args[i]);
	       }
	     }
	
	     FileInputFormat.setInputPaths(conf, new Path(other_args.get(0)));
	     FileOutputFormat.setOutputPath(conf, new Path(other_args.get(1)));
	
	     JobClient.runJob(conf);
	     return 0;
	   }
	
	   public static void main(String[] args) throws Exception {
	     int res = ToolRunner.run(new Configuration(), new WordCount(), args);
	     System.exit(res);
	   }
	}
