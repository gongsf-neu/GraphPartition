package contributionSet;

import gnu.trove.map.hash.TIntDoubleHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import util.WeightVertex;

public class WeightSetByLog {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		int pointNum = -1;
		String input = "";
		String output = "";

		long edgeNum = 0; // for check the data
		double base = 1.5;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
			if (args[i].equals("-pn")) {
				pointNum = Integer.parseInt(args[++i]);
			}
		}
		WeightVertex[] points = new WeightVertex[pointNum];
		int count = 0;
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			// Map<Integer, Double> neighbor = new HashMap<Integer, Double>();
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			while (st.hasMoreTokens()) {
				int key = Integer.parseInt(st.nextToken());
				neighbor.put(key, 0.0);
			}
			points[id] = new WeightVertex(id, 1, neighbor);
			count++;
		}
		br.close();
		if (count != pointNum) {
			System.out.println("vertex num error");
		}

		System.out.println("load finish");
		long start = System.currentTimeMillis();

		// set vertex weight
		// count in degrees
		for (int j = 0; j < pointNum; j++) {
			WeightVertex p = points[j];
			for (int id : p.neighbor.keys()) {
				points[id].nweight++;
			}
		}
		double vertexMaxWeight = 0;
		for (int j = 0; j < pointNum; j++) {
			if(points[j].nweight < base){
				points[j].weight = 1;
			}else{
				points[j].weight = Math.log(points[j].nweight)/Math.log(base);
			}
			points[j].nweight = 0;
			if(vertexMaxWeight < points[j].weight){
				vertexMaxWeight = points[j].weight;
			}
		}

		System.out.println("set vertex weight finish");

		// set edge weight
		long end = System.currentTimeMillis();
		System.out.println("use time : " + (end - start));
		System.out.println("max vertex weight : " + vertexMaxWeight);
//		System.out.println("max edge weight : " + edgeMaxWeight);
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
//		 bw.write(pointNum + " " + edgeNum + " " + "\n");
		for (int i = 0; i < pointNum; i++) {
			WeightVertex p = points[i];
			double w = points[i].weight;
			bw.write(i + " " + w + "\n");
//			for (int key : p.neighbor.keys()) {
//				double value = p.neighbor.get(key);
//				 bw.write(key + " " + value + " ");
//			}
//			bw.write("\n");
		}
		bw.close();
		System.out.println("finished!!");
	}

}
