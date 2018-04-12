package metisPartition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Format2MetisInput {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_InDeg.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/metis/vertex_weight_no_MetisInput.txt";

//		String mode = "011";

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));

		String line = br.readLine();
		bw.write(line + "\n");
//		bw.write(mode + "\n");

		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			st.nextToken();
			int id = 0;
			while (st.hasMoreTokens()) {
				id = Integer.parseInt(st.nextToken()) + 1;
				bw.write(id + " ");
				st.nextToken();
			}
			bw.write("\n");
		}

		br.close();
		bw.close();
	}
}
