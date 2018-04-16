package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class WeightGraphNormalize {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/WiKi/vertex_edge_weight.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/WiKi/vertex_edge_weight_normal.txt";
		int vertexNum = 4206784;

		double[] weightCount = new double[vertexNum];
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			while (st.hasMoreTokens()) {
				String[] ss = st.nextToken().split(",");
				weightCount[Integer.parseInt(ss[0])] += Double
						.parseDouble(ss[1]);
			}
		}
		br.close();

		DecimalFormat df = new DecimalFormat("0.000");
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			bw.write(st.nextToken() + "\t");
			while (st.hasMoreTokens()) {
				String[] ss = st.nextToken().split(",");
				bw.write(ss[0]
						+ ","
						+ df.format(Double.parseDouble(ss[1]) / weightCount[Integer
								.parseInt(ss[0])]) + " ");
				// weightCount[Integer.parseInt(ss[0])] +=
				// Double.parseDouble(ss[1]);
			}
			bw.write("\n");
		}
		br.close();
		bw.close();
	}
}
