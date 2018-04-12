package PreWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Analysis {
	
	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/program/grpahPartition/data/part";
		String output = "/home/gongsf/program/grpahPartition/data/data";
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		BufferedReader[] brs = new BufferedReader[4];
		for(int i = 0; i < 4; i++){
			brs[i] = new BufferedReader(new FileReader(input+i));
		}
		
		String line = null;
		while((line = brs[0].readLine()) != null){
			bw.write(line + "\n");
			line = brs[1].readLine();
			bw.write(line + "\n");
			line = brs[2].readLine();
			bw.write(line + "\n");
			line = brs[3].readLine();
			bw.write(line + "\n");
		}
		
		bw.close();
	}

}
