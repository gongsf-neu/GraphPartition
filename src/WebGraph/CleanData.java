package WebGraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CleanData {
	
	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/program/graphPartition/dataSet/BerkStan/edge_dirty.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/BerkStan/edge_no_renumber.txt";
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
		}
		
		int maxValue = -1;
		int minValue = Integer.MAX_VALUE;
		int src = 0;
		int dst = 0;
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		while((line = br.readLine()) != null){
			if(!(line.startsWith("#") || line.startsWith("%"))){
				bw.write(line + "\n");
			}
		}
		br.close();
		bw.close();
		System.out.println("clean finished!!");
	}

}
