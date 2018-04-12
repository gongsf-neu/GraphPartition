package neighborPartition;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import util.WeightVertex;

public class NeighborPartition {

	public static void main(String[] args) throws IOException {

		// contribution and communication file
		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/"
				+ "vertex_weight_InDeg.txt";

		// part information
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/"
				+ "neighbor/neighbor_vertex_weight_InDeg_partInfo";

		int vertexNum = 916428;
		int partNum = 4;
		int[] partInfo;
		WeightVertex[] vertices;
		double allCon = 0; // all the contribution of all vertices
		double averageCon = 0; // average contribution for each partition

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

			if (args[i].equals("-partNum")) {
				partNum = Integer.parseInt(args[++i]);
			}

		}

		partInfo = new int[vertexNum];
		vertices = new WeightVertex[vertexNum];
		output = output + "." + partNum;

		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;
		int id = 0;
		double weight = 0;
		double comm = 0;
		double commCount = 0;

		// the first line contains [vertexNum] [edgeNum]
		line = br.readLine();
		String[] s = line.split("\\s+");
		vertexNum = Integer.parseInt(s[0]);
		int maxId = 0;
		double maxCon = 0;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			commCount = 0;
			StringTokenizer st = new StringTokenizer(line);
			id = Integer.parseInt(st.nextToken());
			weight = Double.parseDouble(st.nextToken());
			allCon += weight;
			if (maxCon < weight) {
				maxCon = weight;
				maxId = id;
			}
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			while (st.hasMoreTokens()) {
				neighbor.put(Integer.parseInt(st.nextToken()),
						comm = Double.parseDouble(st.nextToken()));
				commCount += comm;
			}
			WeightVertex v = new WeightVertex(id, weight, neighbor);
			v.nweight = commCount;
			vertices[i] = v;
		}
		br.close();

		averageCon = allCon / partNum;

		for (int i = 0; i < partNum; i++) {
			System.out.println(i + " partition is started");
			double con = 0;
			TIntDoubleHashMap candidates = new TIntDoubleHashMap();
			HashSet<Integer> partSet = new HashSet<Integer>();
			int coreId = maxId;
			for (int j = 0; con < averageCon; j++) {
				if (j % 10000 == 0) {
					System.out.println(con + " " + averageCon);
				}
				WeightVertex v = vertices[coreId];
				if (!v.isPart) {
					partSet.add(coreId);
					v.isPart = true;
					if (candidates.contains(coreId)) {
						candidates.remove(coreId);
					}
					con += v.weight;

					TIntDoubleIterator vitr = v.neighbor.iterator();
					while (vitr.hasNext()) {
						vitr.advance();
						id = vitr.key();
						if (!vertices[id].isPart) {
							if (candidates.containsKey(id)) {
								candidates.put(id,
										candidates.get(id) + vitr.value());
							} else {
								candidates.put(id, vitr.value());
							}
						}
					}

				} else {
					if (candidates.contains(coreId)) {
						candidates.remove(coreId);
					}
				}
				coreId = getNextId(candidates, vertices);
			}
			System.out.println(i + " partition is finished");
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		bw.close();

	}

	public static int getNextId(TIntDoubleHashMap candidates,
			WeightVertex[] vertices) {
		System.out.println(candidates.size());
		double inCom = 0;
		double outCom = 0;
		double maxInCom = 0;
		int maxInComId = -1;

		TIntDoubleIterator citr = candidates.iterator();
		while (citr.hasNext()) {
			citr.advance();
			inCom = citr.value();
			outCom = vertices[citr.key()].nweight - inCom;
			if (maxInCom > (inCom - outCom)) {
				maxInComId = citr.key();
			}
		}
		return maxInComId;
	}
}
