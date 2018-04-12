package PreWork;

import gnu.trove.map.hash.TIntDoubleHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import util.WeightVertex;

public class MetisInput {

	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc/vertex.txt";
		String output = "/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc/vertex_metis_nw_input.txt";
		int pointNum = 4206785 + 1;
		int edgeNum = 0;
		double initPr = 1;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
			if (args[i].equals("-pNum")) {
				pointNum = Integer.parseInt(args[++i]) + 1;
			}
		}

		WeightVertex[] points = new WeightVertex[pointNum];

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));

		int count = 0;
		String line = null;
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken()) + 1;
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			while (st.hasMoreTokens()) {
				int key = Integer.parseInt(st.nextToken()) + 1;
				if (key != id) {
					neighbor.put(key, initPr);
				}
			}
			points[id] = new WeightVertex(id, initPr, neighbor);
			if(id == pointNum){
				System.out.println(id);
			}
			count++;
			if(count % 1000000 == 0){
				System.out.println(count);
			}
		}
		System.out.println("load finished");

		for (int i = 1; i < pointNum; i++) {
			WeightVertex p = points[i];
			if(p == null){
				System.out.println("points " + i + "is null");
			}
			for (int id : p.neighbor.keys()) {
				if (points[id].neighbor.containsKey(i)) {
					double value = 1;
					points[id].neighbor.put(i, value);
					points[i].neighbor.put(id, value);
				} else {
					double value = 1;
					points[id].neighbor.put(i, value);
					points[i].neighbor.put(id, value);
				}
			}
			if(i % 1000000 == 0){
				System.out.println(i);
			}
		}
		System.out.println("edge weight set finished");
		
		for (int j = 1; j < pointNum; j++) {
			if (points[j] == null) {
				System.out.println(j);
			}
			edgeNum += points[j].neighbor.size();
		}
		edgeNum = edgeNum / 2;
		// System.out.println(edgeNum);
		bw.write((pointNum - 1) + " " + edgeNum + " " + "\n");
		for (int i = 1; i < pointNum; i++) {
			WeightVertex p = points[i];
//			int weight = (int) points[i].weight;
//			bw.write();
			for (int id : p.neighbor.keys()) {
//				double value = entry.getValue();
				bw.write(id + " ");
			}
			bw.write("\n");
		}
		bw.close();
	}
}
