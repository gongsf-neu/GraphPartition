package partitionFennel;

import gnu.trove.set.hash.TIntHashSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Fennel_NW {

	public static void main(String[] args) throws IOException {

		String input = "";
		String output = "";
		int partNum = -1;

		int edgeNum = 0;
		int vertexNum = 0;
		int[] partInfo;

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
			System.out.println("invalid parameter");
			System.exit(0);
		}

		output += "." + partNum;

		long startTime = System.currentTimeMillis();

		TIntHashSet[] parts = new TIntHashSet[partNum];
		for (int i = 0; i < partNum; i++) {
			parts[i] = new TIntHashSet();
		}

		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			vertexNum++;
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			while (st.hasMoreTokens()) {
				st.nextToken();
				edgeNum++;
			}
		}
		// vertexNum = Integer.parseInt(s[0]);
		// edgeNum = Integer.parseInt(s[1]);
		partInfo = new int[vertexNum];
		double gamma = 1.5;
		double alpha = Math.sqrt(partNum) * edgeNum
				/ (Math.pow(vertexNum, gamma));
		double v = 1.1;
		double miu = v * vertexNum / partNum;
		br.close();
		br = new BufferedReader(new FileReader(input));
		for (int j = 0; (line = br.readLine()) != null; j++) {
			TIntHashSet set = new TIntHashSet();
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken()); // point id
			if (id != j) {
				System.out.println(id + " " + j);
				System.exit(0);
			}
			while (st.hasMoreTokens()) {
				set.add(Integer.parseInt(st.nextToken())); // neighbors id
			}

			double score = 0;
			double maxScore = -Double.MAX_VALUE;
			int maxPart = 0;
			for (int i = 0; i < partNum; i++) {
				if (parts[i].size() <= miu) {
					score = getScore(set, parts[i], alpha, gamma);
//					System.out.println("part " + i + " " + score);
					// System.out.print(score + " ");
					if (maxScore < score) {
						maxScore = score;
						maxPart = i;
					}
				}
			}
//			if(j == 5){
//				System.exit(0);
//			}
			// System.out.println();
			parts[maxPart].add(id);
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

	public static double getScore(TIntHashSet neighbor, TIntHashSet partition,
			double alpha, double gamma) {
		double score = 0;
		neighbor.retainAll(partition);
		score = neighbor.size();
		double deltaC = alpha * (Math.pow(partition.size() + 1, gamma)
				- Math.pow(partition.size(), gamma));
//		System.out.println(score + " - " + deltaC + " = " + (score-deltaC));
		return score - deltaC;

	}
}
