package partition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PartFromInfo {

	public static void main(String[] args) throws IOException {

		int partNum = 4;
		//note whether the graph is weight graph

		String partInfo = "";
		String originalData = "";
		String output = "";
		

		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-partInfo")){
				partInfo = args[++i];
			}
			if(args[i].equals("-in")){
				originalData = args[++i];
			}
			if(args[i].equals("-out")){
				output = args[++i];
			}
			if(args[i].equals("-partNum")){
				partNum = Integer.parseInt(args[++i]);
			}
		}
		
		if(partNum == -1){
			System.out.println("partNum is error");
			System.exit(0);
		}
		
		BufferedReader brmo = new BufferedReader(new FileReader(partInfo));
		BufferedReader bror = new BufferedReader(new FileReader(originalData));
		BufferedWriter bw[] = new BufferedWriter[partNum];
		for (int i = 0; i < partNum; i++) {
			bw[i] = new BufferedWriter(new FileWriter(output + i));
		}

		String data = null;
		String partIdString = null;
		for (int i = 0; (partIdString = brmo.readLine()) != null; i++) {
			int partId = Integer.parseInt(partIdString);
			bw[partId].write(bror.readLine() + "\n");
		}

		brmo.close();
		bror.close();
		for (int i = 0; i < partNum; i++) {
			bw[i].close();
		}
		System.out.println("part finish!!!");
	}
}
