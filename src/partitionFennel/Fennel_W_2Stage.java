package partitionFennel;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.set.hash.TIntHashSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import util.WeightVertex;

public class Fennel_W_2Stage {

	public static void main(String[] args) throws IOException {

		String input = "";
		String output = "";

		int edgeNum = 0;
		int vertexNum = 0;
		int[] partInfo;
		double[] contribution;
		int partNum = -1;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}

			if (args[i].equals("-out")) {
				output = args[++i];
			}

			if (args[i].equals("-partNum")) {
				partNum = Integer.parseInt(args[++i]);
			}
		}

		if (partNum == -1) {
			System.out.println("invalid parameter partNum");
			System.exit(0);
		}

		long startTime = System.currentTimeMillis();

		output += "." + partNum;
		TIntHashSet[] parts = new TIntHashSet[partNum];
		contribution = new double[partNum];
		for (int i = 0; i < partNum; i++) {
			parts[i] = new TIntHashSet();
		}
		WeightVertex[] vertices = new WeightVertex[vertexNum];

		double contributionAll = 0;
		double trafficAll = 0;

		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		line = br.readLine();
		String[] s = line.split("\\s+");
		vertexNum = Integer.parseInt(s[0]);
		edgeNum = Integer.parseInt(s[1]);
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			double weight = Double.parseDouble(st.nextToken());
			contributionAll += weight;
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			double comm = 0;
			while (st.hasMoreTokens()) {
				neighbor.put(Integer.parseInt(st.nextToken()),
						comm = Double.parseDouble(st.nextToken()));
				trafficAll += comm;
			}
			WeightVertex v = new WeightVertex(id, weight, neighbor);
			vertices[i] = v;
		}
		br.close();
		trafficAll /= 2;

		partInfo = new int[vertexNum];
		double gamma = 1.5;
		double alpha = Math.sqrt(partNum) * trafficAll
				/ (Math.pow(contributionAll, gamma));
//		double v = 1.1;
		double miu = 1.1 * contributionAll / partNum;
		ArrayList<WeightVertex> top = new ArrayList<WeightVertex>();
		ArrayList<WeightVertex> remain = new ArrayList<WeightVertex>();
		Util.getUnstable(vertices, top, remain);
		for (int j = 0; j < top.size(); j++) {
			WeightVertex v = top.get(j);
			TIntDoubleHashMap neighbor = v.neighbor;
			double score = 0;
			double maxScore = -Double.MAX_VALUE;
			int maxPart = 0;
			for (int i = 0; i < partNum; i++) {
				if (contribution[i] <= miu) {
					score = getScore(neighbor, parts[i], contribution[i], v.weight,
							alpha, gamma);
					if (maxScore < score) {
						maxScore = score;
						maxPart = i;
					}
				}
				// System.out.print(score + " ");
			}
			// System.out.println();
			parts[maxPart].add(v.id);
			contribution[maxPart] += v.weight;
			partInfo[j] = maxPart;
		}
		
		for (int j = 0; j < remain.size(); j++) {
			WeightVertex v = remain.get(j);
			TIntDoubleHashMap neighbor = v.neighbor;
			double score = 0;
			double maxScore = -Double.MAX_VALUE;
			int maxPart = 0;
			for (int i = 0; i < partNum; i++) {
				if (contribution[i] <= miu) {
					score = getScore(neighbor, parts[i], contribution[i], v.weight,
							alpha, gamma);
					if (maxScore < score) {
						maxScore = score;
						maxPart = i;
					}
				}
				// System.out.print(score + " ");
			}
			// System.out.println();
			parts[maxPart].add(v.id);
			contribution[maxPart] += v.weight;
			partInfo[j] = maxPart;
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < vertexNum; i++) {
			bw.write(partInfo[i] + "\n");
		}
		bw.close();

		long endTime = System.currentTimeMillis();
		System.out.println("used time : " + (endTime - startTime));

	}

	public static double getScore(TIntDoubleHashMap neighbor,
			TIntHashSet partition, double partcont, double cont, double alpha,
			double gamma) {
		double score = 0;
		TIntDoubleIterator itr = neighbor.iterator();
		while (itr.hasNext()) {
			itr.advance();
			int id = itr.key();
			if (partition.contains(id)) {
				score += itr.value();
			}
		}

		double deltaC = alpha
				* (Math.pow(partcont + cont, gamma) - Math.pow(partcont, gamma));

		return score - deltaC;

	}

}
