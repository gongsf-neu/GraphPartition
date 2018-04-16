package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class CountDegree {

	public static void main(String[] args) throws IOException {

		// the input is direct graph
		String input = "/home/gongsf/program/graphPartition/dataSet/WiKi/vertex.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/WiKi/vertex.txt.info";

		int vertexNum = 4206784;
		int partNum = 20;
		int[] degrees;
		int[] count = new int[partNum];
		double interval = 1.0 / partNum;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}

			if (args[i].equals("-out")) {
				output = args[++i];
			}

			if (args[i].equals("-vertexNum")) {
				vertexNum = Integer.parseInt(args[++i]);
			}
		}
		degrees = new int[vertexNum];

		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			while (st.hasMoreTokens()) {
				degrees[Integer.parseInt(st.nextToken())]++;
			}
		}
		br.close();

		int maxDegree = 0;
		int maxIndex = 0;

		for (int i = 0; i < vertexNum; i++) {
			if (maxDegree < degrees[i]) {
				maxDegree = degrees[i];
				maxIndex = i;
			}
		}

		System.out.println(maxDegree);

		double tmp = 0;
		int index = 0;
		for (int i = 0; i < vertexNum; i++) {
			if (degrees[i] == maxDegree) {
				count[partNum - 1]++;
			} else {
				tmp = degrees[i]*1.0 / maxDegree;
				index = (int) (tmp / interval);
				count[index]++;
			}
		}

		DecimalFormat df = new DecimalFormat("0.00");
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < partNum; i++) {
			bw.write(df.format(interval * i) + " -- "
					+ df.format(interval * (i + 1)) + " : " + count[i] + "\n");
		}
		bw.close();

	}

}
