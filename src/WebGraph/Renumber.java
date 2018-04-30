package WebGraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Renumber {

	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/program/graphPartition/dataSet/twitter/edge_no_renumber.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/twitter/edge.txt";
		int start = 1;

		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		int src = 0;
		int dst = 0;
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			String[] ss = line.split("\\s+");
			src = Integer.parseInt(ss[0]);
			dst = Integer.parseInt(ss[1]);
			bw.write((src - start) + "\t" + (dst - start) + "\n");
		}

		br.close();
		bw.close();
	}

}
