package partition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PartFromInfo {

	public static void main(String[] args) throws IOException {
		
		String method = "fennel";

		String partInfo = "/home/gongsf/program/graphPartition/dataSet/Google_90w/"
				+ method +"/"+ method  +"_vertex_weight_InDeg_partInfo.4";
		String originalData = "/home/gongsf/program/graphPartition/dataSet/Google_90w/"
				+ "original_data_weight.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/"
				+ method +"/"+ method + "_vertex_weight_InDeg_result/weight/vertex";
		int partNum = 4;

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
	}
}
