package partitionHash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HashPartition {
	
	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/original_data.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/hash/part";
		int partNum = 4;
		
		BufferedReader br = new BufferedReader(new FileReader(input));
		
		BufferedWriter bw[] = new BufferedWriter[partNum];
		for(int i = 0; i < bw.length; i++){
			bw[i] = new BufferedWriter(new FileWriter(output+i));
		}
		
		String line = null;
		for(int i = 0; (line = br.readLine()) != null; i++){
			int part = i % partNum;
			bw[part].write(line+"\n");
		}
		
		br.close();
		for(int i = 0; i < partNum; i++){
			bw[i].close();
		}
		
	}

}
