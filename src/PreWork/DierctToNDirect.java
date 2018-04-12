package PreWork;

import gnu.trove.map.hash.TIntDoubleHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import util.WeightVertex;

public class DierctToNDirect {

	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/Desktop/PageRank/Google_90w/vertex.txt";
		String output = "/home/gongsf/Desktop/PageRank/Google_90w/vertex_nd.txt";
		int pointNum = 916428;
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
				pointNum = Integer.parseInt(args[++i]);
			}
		}

		WeightVertex[] points = new WeightVertex[pointNum];

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));

		String line = null;
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			while (st.hasMoreTokens()) {
				int key = Integer.parseInt(st.nextToken());
				if (key != id) {
					neighbor.put(key, initPr);
				}
			}
			points[id] = new WeightVertex(id, initPr, neighbor);
//			if (id == pointNum) {
//				System.out.println(id);
//			}
		}
		System.out.println("load finished");

		for (int i = 0; i < pointNum; i++) {
			WeightVertex p = points[i];
			if (p == null) {
				 System.out.println("points " + i + "is null");
			} else {
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
			}
		}
		System.out.println("edge weight set finished");

		// for (int j = 0; j < pointNum; j++) {
		// if (points[j] == null) {
		// System.out.println(j);
		// }
		// edgeNum += points[j].neighbor.size();
		// }
		for (int i = 0; i < pointNum; i++) {
			WeightVertex p = points[i];
			bw.write(i + "\t");
			for (int id : p.neighbor.keys()) {
				// double value = entry.getValue();
				bw.write(id + " ");
			}
			bw.write("\n");
		}
		br.close();
		bw.close();
	}
}
