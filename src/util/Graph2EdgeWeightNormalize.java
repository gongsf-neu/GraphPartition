package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class Graph2EdgeWeightNormalize {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/dynamicSchedule/dataSet/roadNet/vertex.txt";
		String output = "/home/gongsf/program/dynamicSchedule/dataSet/roadNet/vertex_edge_weight_normal.txt";
		int vertexNum = 1965206;
		
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
				weightCount[Integer.parseInt(st.nextToken())]++;
			}
		}
		br.close();
		
		for(int i = 0; i < vertexNum; i++){
			if(weightCount[i] != 0){
				weightCount[i] = 1.0/weightCount[i];
			}
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			bw.write(st.nextToken() + "\t");
			while (st.hasMoreTokens()) {
				String[] ss = st.nextToken().split(",");
				bw.write(ss[0]
						+ ","
						+ weightCount[Integer.parseInt(ss[0])] + " ");
			}
			bw.write("\n");
		}
		br.close();
		bw.close();
		
		System.out.println("edge weight normalized!!");
	}
}
