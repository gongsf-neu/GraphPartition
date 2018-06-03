package partitionFennel;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.set.hash.TIntHashSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Fennel_W {

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

		double contributionAll = 0;
		double trafficAll = 0;

		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		line = br.readLine();
		String[] s = line.split("\\s+");
		vertexNum = Integer.parseInt(s[0]);
		edgeNum = Integer.parseInt(s[1]);
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			contributionAll += Double.parseDouble(st.nextToken());
			while (st.hasMoreTokens()) {
				st.nextToken();
				trafficAll += Double.parseDouble(st.nextToken());
			}
		}
		br.close();
		trafficAll /= 2;

		partInfo = new int[vertexNum];
		double gamma = 1.5;
		double alpha = Math.sqrt(partNum) * trafficAll
				/ (Math.pow(contributionAll, gamma));
		double v = 1.1;
		double miu = v * contributionAll / partNum;

		br = new BufferedReader(new FileReader(input));
		br.readLine();
		for (int j = 0; (line = br.readLine()) != null; j++) {
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			if (id != j) {
				System.out.println(id + " " + j);
				System.exit(0);
			}
			double cont = Double.parseDouble(st.nextToken());
			while (st.hasMoreTokens()) {
				neighbor.put(Integer.parseInt(st.nextToken()),
						Double.parseDouble(st.nextToken()));
			}

			double score = 0;
			double maxScore = -Double.MAX_VALUE;
			int maxPart = 0;
			for (int i = 0; i < partNum; i++) {
				if (contribution[i] <= miu) {
					score = getScore(neighbor, parts[i], contribution[i], cont,
							alpha, gamma);
					if (maxScore < score) {
						maxScore = score;
						maxPart = i;
					}
				}
				// System.out.print(score + " ");
			}
			// System.out.println();
			parts[maxPart].add(id);
			contribution[maxPart] += cont;
			partInfo[j] = maxPart;
		}
		br.close();

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
