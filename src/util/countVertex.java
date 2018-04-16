package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class countVertex {
	
	public static void main(String[] args) throws IOException {
		
		String input = "/home/gongsf/program/graphPartition/dataSet/WiKi/edge.txt";
		
		int maxIndex = 0;
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while((line = br.readLine()) != null){
			String[] ss = line.split("\\s+");
			int src = Integer.parseInt(ss[0]);
			int dest = Integer.parseInt(ss[1]);
			if(maxIndex < src || maxIndex < dest){
				maxIndex = Math.max(src, dest);
			}
		}
		br.close();
		System.out.println(maxIndex+1);
	}

}
