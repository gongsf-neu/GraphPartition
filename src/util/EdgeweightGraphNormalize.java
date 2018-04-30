package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class EdgeweightGraphNormalize {
	
	public static void main(String[] args) throws IOException {

		String input = "";
		String output = "";
		int vertexNum = 0;
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
			if(args[i].equals("-out")){
				output = args[++i];
			}
			if(args[i].equals("-pointNum")){
				vertexNum = Integer.parseInt(args[++i]);
			}
		}

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
