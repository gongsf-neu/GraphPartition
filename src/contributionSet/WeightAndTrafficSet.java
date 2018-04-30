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

public class WeightAndTrafficSet {

	public static void main(String[] args) throws NumberFormatException, IOException {
		int pointNum = -1;

		String input = "";
		String output = "";
		String resultPath = "";
		
		long edgeNum = 0; // for check the data

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
			if(args[i].equals("-result")){
				resultPath = args[++i];
			}
			if (args[i].equals("-pointNum")) {
				pointNum = Integer.parseInt(args[++i]);
			}
			if(args[i].equals("-edgeNum")){
				edgeNum = Integer.parseInt(args[++i]);
			}
		}
		WeightVertex[] points = new WeightVertex[pointNum];
		int count = 0;
		//load original data
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
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

		BufferedReader resultBR = new BufferedReader(new FileReader(resultPath));
		line = null;
		for(int id = 0; (line = resultBR.readLine()) != null; id++){
			String[] ss = line.split("\\s+");
			int pid = Integer.parseInt(ss[0]);
			if(pid != id){
				System.out.println("not correspond");
				System.exit(0);
			}
			int weight = Integer.parseInt(ss[1]);
			points[id].weight = weight;
		}
		resultBR.close();
		
		System.out.println("set weight finish");

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
			double w = points[i].weight;

			bw.write(i + " " + w + "\t");
			for (int key : p.neighbor.keys()) {
				double value = p.neighbor.get(key);
				bw.write(key + " " + value + " ");
			}
			bw.write("\n");
		}
		bw.close();
		System.out.println("finished!!");
	}

}
