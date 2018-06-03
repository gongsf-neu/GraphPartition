package partitionHash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HashPartition {
	
	public static void main(String[] args) throws IOException {
		String input = "";
		String output = "";
		int partNum = -1;
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
			if(args[i].equals("-out")){
				output = args[++i];
			}
			if(args[i].equals("-partNum")){
				partNum = Integer.parseInt(args[++i]);
			}
		}
		
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
		
		System.out.println("hash part finished");
		
	}

}
