package contributionSet;

import gnu.trove.map.hash.TIntDoubleHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import util.WeightVertex;

public class WeightSetByPR {

	public static void main(String[] args) throws IOException {

		double initPr = 10;
		double decayFactor = 0.8;
		double random = initPr - decayFactor * initPr;
		int pnum = 916428;
		// pnum++;
		int iterNum = 2;
		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/original_data.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_pr_"
				+ iterNum + ".txt";
		long edgeNum = 0; //for check the data
		String model = "011";

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
			if (args[i].equals("-pn")) {
				pnum = Integer.parseInt(args[++i]);
			}
			if (args[i].equals("-itn")) {
				iterNum = Integer.parseInt(args[++i]);
			}
		}

		WeightVertex[] points = new WeightVertex[pnum];
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
			points[id] = new WeightVertex(id, initPr, neighbor);
			count++;
			// if (i % 100000 == 0) {
			// System.out.println(i);
			// }
		}
		br.close();
		if (count != pnum) {
			System.out.println("vertex num error");
		}
		// System.out.println(edgeNum);

		System.out.println("load finish");
		long start = System.currentTimeMillis();

		// set vertex weight
		int size = 0;
		double weight = 0;
		for (int i = 0; i < iterNum; i++) {
			for (int j = 0; j < pnum; j++) {
				WeightVertex p = points[j];
				p.weight = decayFactor * p.weight + random;
				size = p.neighbor.size();
				weight = p.weight / size;
				for (int id : p.neighbor.keys()) {
					points[id].nweight += weight;
				}
			}
			for (int j = 0; j < pnum; j++) {
				points[j].weight = points[j].nweight;
				points[j].nweight = 0;
			}
			System.out.println(i);
		}
		for (int j = 0; j < pnum; j++) {
			points[j].weight = Math.ceil(points[j].weight);
			if (points[j].weight == 0) {
				points[j].weight = 1;
			}
		}
		System.out.println("weight : double to int");

		// set edge weight
		double vertexMaxWeight = 0;
		double edgeMaxWeight = 0;
		for (int i = 0; i < pnum; i++) {
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
		System.out.println("edge set finished, and the graph become undirected");

		edgeNum = 0;
		// for (int i = 1; i < pnum; i++) {
		// WeightVertex p = points[i];
		// for (int id : p.neighbor.keys()) {
		// if (!(points[i].neighbor.containsKey(id) && points[id].neighbor
		// .containsKey(i))) {
		// System.out.println(i + " " + id);
		// System.out.println("directed edge");
		// System.out.println(i + " contain " + id + " : "
		// + points[i].neighbor.containsKey(id));
		// System.out.println(id + " contain " + i + " : "
		// + points[id].neighbor.containsKey(i));
		// System.exit(0);
		// }
		// edgeNum++;
		// }
		// }
		// System.out.println("compute edge num finished : " + edgeNum);

		edgeNum = 0;
		for (int j = 0; j < pnum; j++) {
			edgeNum += points[j].neighbor.size();
		}
		System.out.println("compute edge num finished : " + edgeNum);

		if (edgeNum % 2 != 0) {
			System.out.println("error number of edge");
		}
		edgeNum = edgeNum / 2;
		System.out.println("edge Num" + edgeNum);
		long end = System.currentTimeMillis();
		System.out.println("use time : " + (end - start));
		System.out.println("max vertex weight : " + vertexMaxWeight);
		System.out.println("max edge weight : " + edgeMaxWeight);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		// bw.write((pnum - 1) + " " + edgeNum + " " + model + "\n");
		for (int i = 0; i < pnum; i++) {
			WeightVertex p = points[i];
			// int w = (int) points[i].weight;
			double w = (points[i].weight / vertexMaxWeight) + 1;
			bw.write(i + " " + w + "\t");
			for (int key : p.neighbor.keys()) {
				double value = p.neighbor.get(key);
				// if(value < weight){
				// System.out.println(weight + " " + value);
				// }
				// bw.write(key + " " + ((int) value) + " ");
				bw.write(key + " " + ((value / edgeMaxWeight) + 1) + " ");
			}
			bw.write("\n");
		}
		bw.close();
		System.out.println("finished!!");
	}
}
