package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.StringTokenizer;

public class Graph2EdgeWeightGraph {
	
	public static void main(String[] args) throws IOException {
		
		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/original_data.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/original_data_weight.txt";
		
		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		
		double max = 20;
		
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("0.000");
		
		String line = null;
		while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line);
			bw.write(st.nextToken() + "\t");
			while(st.hasMoreTokens()){
				bw.write(st.nextToken() + "," + df.format(Math.abs(random.nextDouble()) * max+1) + " ");
			}
			bw.write("\n");
		}
		
		br.close();
		bw.close();
	}

}
