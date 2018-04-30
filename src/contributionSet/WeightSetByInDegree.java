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

/**
 * @author gongsf
 * this class is used to set contribution of vertices and communication of edges
 * the output is an undirected graph
 *
 */
public class WeightSetByInDegree {

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		int pointNum = 916428;
		
		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/original_data.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_InDeg_Log.txt";

		long edgeNum = 0; // for check the data
		String model = "011";

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
				// if(neighbor.containsKey(key)){
				// neighbor.put(key, neighbor.get(key)+)
				// }else{
				neighbor.put(key, 0.0);
				// }
			}
			points[id] = new WeightVertex(id, 1, neighbor);
			count++;
			// if (i % 100000 == 0) {
			// System.out.println(i);
			// }
		}
		br.close();
		if (count != pointNum) {
			System.out.println("vertex num error");
		}
		// System.out.println(edgeNum);

		System.out.println("load finish");
		long start = System.currentTimeMillis();

		// set vertex weight
		double weight = 0;
		// count in degrees
		for (int j = 0; j < pointNum; j++) {
			WeightVertex p = points[j];
			weight = 1;
			for (int id : p.neighbor.keys()) {
				points[id].nweight += weight;
			}
		}
		for (int j = 0; j < pointNum; j++) {
			points[j].weight = points[j].nweight;
			points[j].nweight = 0;
		}

		System.out.println("set first weight finish");

		// second of formulate
		for (int j = 0; j < pointNum; j++) {
			WeightVertex p = points[j];
			weight = p.weight / p.neighbor.size();
			for (int id : p.neighbor.keys()) {
				points[id].nweight += weight;
			}
		}
		for (int j = 0; j < pointNum; j++) {
			points[j].weight += Math.ceil(points[j].nweight);
			if (points[j].weight == 0) {
				points[j].weight = 1;
			}
		}
		System.out.println("set second weight finish");

		// set edge weight
		double vertexMaxWeight = 0;
		double edgeMaxWeight = 0;
		for (int i = 0; i < pointNum; i++) {
			WeightVertex p = points[i];
			if (vertexMaxWeight < p.weight) {
				vertexMaxWeight = p.weight;
			}
			for (int id : p.neighbor.keys()) {
				if (i != id) {
					if (points[id].neighbor.containsKey(i)) {
						if (id > i) {
							double value = p.weight + points[id].weight;
							if (edgeMaxWeight < value) {
								edgeMaxWeight = value;
							}
							if (value == 0) {
								value = 1;
							}
							points[id].neighbor.put(i, value);
							points[i].neighbor.put(id, value);
						}
					} else {
						double value = p.weight;
						if (edgeMaxWeight < value) {
							edgeMaxWeight = value;
						}
						if (value == 0) {
							value = 1;
						}
						points[id].neighbor.put(i, value);
						points[i].neighbor.put(id, value);
					}
				} else {
					p.neighbor.remove(i);
				}
			}
			if (i % 1000000 == 0) {
				System.out.println(i);
			}
		}
		System.out
				.println("edge set finished, and the graph become undirected");

		edgeNum = 0;
		for (int j = 0; j < pointNum; j++) {
			edgeNum += points[j].neighbor.size();
		}
		System.out.println("compute edge num finished : " + edgeNum);

		if (edgeNum % 2 != 0) {
			System.out.println("error number of edge");
		}
		edgeNum = edgeNum / 2;
		System.out.println("edge Num : " + edgeNum);
		long end = System.currentTimeMillis();
		System.out.println("use time : " + (end - start));
		System.out.println("max vertex weight : " + vertexMaxWeight);
		System.out.println("max edge weight : " + edgeMaxWeight);
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		 bw.write(pointNum + " " + edgeNum + " " + "\n");
		for (int i = 0; i < pointNum; i++) {
			WeightVertex p = points[i];
			// int w = (int) points[i].weight;
//			double w = (points[i].weight / vertexMaxWeight) + 1;
			double w = points[i].weight;
			
//			bw.write(i + " " + (w) + "\t");
			bw.write(i + " " + (Math.pow(w, 0.5)) + "\t");
//			bw.write(i + " " + (Math.log(w)/Math.log(1.5)) + "\t");
			for (int key : p.neighbor.keys()) {
				double value = p.neighbor.get(key);
				if(value < 1){
					
				}else{
					
				}
				// if(value < weight){
				// System.out.println(weight + " " + value);
				// }
//				 bw.write(key + " " + ( value) + " ");
				 bw.write(key + " " + (Math.pow(value,1.5)) + " ");
//				 bw.write(key + " " + (Math.log(value)/Math.log(1.5)) + " ");
			}
			bw.write("\n");
		}
		bw.close();
		System.out.println("finished!!");
	}

}
