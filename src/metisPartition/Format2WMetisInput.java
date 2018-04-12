package metisPartition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Format2WMetisInput {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_result.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/metis/"
				+ "vertex_weight_result_MetisInput.txt";

		String mode = "011";

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));

		String line = br.readLine();
		bw.write(line + " ");
		bw.write(mode + "\n");

		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken()) + 1;//not be written
			int weight = 0;
			bw.write((int)Double.parseDouble(st.nextToken()) + "");
			while (st.hasMoreTokens()) {
				id = Integer.parseInt(st.nextToken()) + 1;
				weight = (int)Double.parseDouble(st.nextToken());
				bw.write(" " + id + " " + weight);
			}
			bw.write("\n");
		}

		br.close();
		bw.close();
		System.out.println("finished");
	}
}
