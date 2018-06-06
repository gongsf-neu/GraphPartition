package partitionNeighbor;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.set.hash.TIntHashSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import util.WeightVertex;

public class NH2StagePartition {

	public static void main(String[] args) throws IOException {

		// contribution and communication file
		String input = "/home/gongsf/program/graphPartition/dataSet/Google/"
				+ "vertex_contribution_Indeglog_deep_2.txt";

		// part information
		String output = "/home/gongsf/program/graphPartition/dataSet/Google/"
				+ "vertex_contribution_Indeglog_deep_2.txt.part.4";

		int vertexNum = 916428;
		int partNum = 4;
		int[] partInfo;
		double[] contribution;
		WeightVertex[] vertices;

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
		contribution = new double[partNum];
		vertices = new WeightVertex[vertexNum];
		output = output + "." + partNum;
		TIntHashSet[] parts = new TIntHashSet[partNum];
		for (int i = 0; i < partNum; i++) {
			parts[i] = new TIntHashSet();
		}

		double contributionAll = 0;
		double trafficAll = 0;

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
		for (int i = 0; (line = br.readLine()) != null; i++) {
			commCount = 0;
			StringTokenizer st = new StringTokenizer(line);
			id = Integer.parseInt(st.nextToken());
			weight = Double.parseDouble(st.nextToken());
			contributionAll += weight;
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			while (st.hasMoreTokens()) {
				neighbor.put(Integer.parseInt(st.nextToken()),
						comm = Double.parseDouble(st.nextToken()));
				commCount += comm;
				trafficAll += comm;
			}
			WeightVertex v = new WeightVertex(id, weight, neighbor);
			v.nweight = commCount;
			vertices[i] = v;
		}
		br.close();
		trafficAll /= 2;
		double gamma = 1.5;
		double alpha = Math.sqrt(partNum) * trafficAll
				/ (Math.pow(contributionAll, gamma));
		System.out.println("alpha : " + alpha);
		double v = 1.1;
		double miu = v * contributionAll / partNum;

		List<WeightVertex> top = new ArrayList<WeightVertex>();
		List<WeightVertex> remain = new ArrayList<WeightVertex>();
		HashSet<Integer> topIdSet = Util.getUnstable(vertices, top, remain);
		assignHardStable(topIdSet, top, vertices, partNum, partInfo,
				contribution, parts);
		assignEasyStable(remain, contribution, parts, partInfo, miu,
				alpha, gamma);
	}

	public static void assignHardStable(HashSet<Integer> topIdSet,
			List<WeightVertex> list, WeightVertex[] vertices, int partNum,
			int[] partInfo, double[] contribution, TIntHashSet[] partition) {

		int maxId = 0;
		double maxCont = 0;
		double totalCont = 0;
		for (int i = 0; i < list.size(); i++) {
			totalCont += list.get(i).weight;
			if (maxCont < list.get(i).weight) {
				maxCont = list.get(i).weight;
				maxId = list.get(i).id;
			}
			WeightVertex v = vertices[list.get(i).id];
			TIntDoubleIterator vitr = v.neighbor.iterator();
			int nid = 0;
			while (vitr.hasNext()) {
				vitr.advance();
				nid = vitr.key();
				if (!topIdSet.contains(nid)) {
					vitr.remove();
				}
			}
		}
		double averageCon = totalCont / partNum;
		System.out.println("average contribution " + averageCon);
		int coreId = maxId;
		double countCon = 0;
		for (int i = partNum - 1; i > 0; i--) {
			System.out.println(i + " partition is started");
			Candidates candidates = new Candidates();
			if (!vertices[coreId].isPart)
				candidates.insert(coreId);

			for (int j = 0; contribution[i] < averageCon; j++) {
				coreId = candidates.popAndInsert(vertices);
				// candidates.checkLoop();
				// System.out.println("candidates vertex id " + coreId);
				WeightVertex v = vertices[coreId];
				if (!v.isPart) {
					partInfo[v.id] = i;
					v.isPart = true;
					contribution[i] += v.weight;
					partition[i].add(v.id);
				} else {
					System.out
							.println("candidates return and partitioned vertex, error");
					System.out.println(partInfo[coreId]);
					System.exit(0);
				}
			}
			countCon += contribution[i];
//			System.out.println(i
//					+ " partition is finished with contribution : "
//					+ contribution[i]);
//			System.out.println(i + " partition is finished with partSize : "
//					+ partition[i].size());
			coreId = getMaxId(vertices);
			System.out.println(partInfo[coreId] + " " + coreId);
			if (vertices[coreId].isPart) {
				System.out.println("maxId partitioned vertex, error");
				System.out.println(partInfo[coreId] + " " + coreId);
				System.exit(0);
			}
		}
		contribution[0] = totalCont - countCon;
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).isPart) {
				partition[0].add(list.get(i).id);
			}
		}
//		System.out.println("0 partition is finished with contribution : "
//				+ contribution[0]);
//		System.out.println("0 partition is finished with partSize : "
//				+ partition[0].size());

	}

	public static int getMaxId(WeightVertex[] vertices) {
		int maxId = 0;
		double maxWeight = vertices[0].weight;
		for (WeightVertex vertex : vertices) {
			if (!vertex.isPart && vertex.weight > maxWeight) {
				maxId = vertex.id;
			}
		}
		return maxId;
	}

	public static void assignEasyStable(List<WeightVertex> list,
			double[] contribution, TIntHashSet[] parts, int[] partInfo, double miu,
			double alpha, double gamma) {
		for (int i = 0; i < list.size(); i++) {
			WeightVertex v = list.get(i);
			TIntDoubleHashMap neighbor = v.neighbor;
			double ctbtion = v.weight;
			double score = 0;
			double maxScore = -Double.MAX_VALUE;
			int maxPart = 0;
			int partNum = contribution.length;
			for (int j = 0; j < partNum; j++) {
				if (contribution[j] <= miu) {
					score = getScore(neighbor, parts[i], contribution[i],
							ctbtion, alpha, gamma);
					if (maxScore < score) {
						maxScore = score;
						maxPart = j;
					}
				}
				// System.out.print(score + " ");
			}
			// System.out.println();
			parts[maxPart].add(v.id);
			contribution[maxPart] += ctbtion;
			partInfo[v.id] = maxPart;
		}
	}

	public static double getScore(TIntDoubleHashMap neighbor,
			TIntHashSet partition, double partCtbtion, double ctbtion,
			double alpha, double gamma) {
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
				* (Math.pow(partCtbtion + ctbtion, gamma) - Math.pow(
						partCtbtion, gamma));

		return score - deltaC;

	}
	
}
