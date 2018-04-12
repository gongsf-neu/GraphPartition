package fennelPartition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Fennel_NW {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_InDeg.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/"
				+ "fennel/fennel_vertex_weight_no_partInfo";
		int partNum = 4;
		
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
		
		output += "."+partNum;
		
		long startTime = System.currentTimeMillis();
		
		HashSet<Integer>[] parts = new HashSet[partNum];
		for (int i = 0; i < partNum; i++) {
			parts[i] = new HashSet<Integer>();
		}

		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;
		line = br.readLine();
		String[] s = line.split("\\s+");
		vertexNum = Integer.parseInt(s[0]);
		edgeNum = Integer.parseInt(s[1]);
		
		partInfo = new int[vertexNum];

		double gamma = 3.0 / 2;
		double alpha = Math.sqrt(partNum) * edgeNum
				/ (Math.pow(vertexNum, gamma));
		double al_ga = alpha * gamma / 2;
		
		for (int j = 0; (line = br.readLine()) != null; j++) {
			HashSet<Integer> set = new HashSet<Integer>();
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken()); // point id
			st.nextToken(); // point weight, skip this
			while (st.hasMoreTokens()) {
				set.add(Integer.parseInt(st.nextToken())); //neighbors id
				st.nextToken(); // edge communication
			}

			double score = 0;
			double maxScore = -Double.MAX_VALUE;
			int maxPart = 0;
			for (int i = 0; i < partNum; i++) {
				score = getScore(set, parts[i], al_ga);
//				System.out.print(score + " ");
				if (maxScore < score) {
					maxScore = score;
					maxPart = i;
				}
			}
//			System.out.println();
			parts[maxPart].add(id);
			partInfo[j] = maxPart;
		}
		br.close();

		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for(int i = 0; i < vertexNum; i++){
			bw.write(partInfo[i] +"\n");
		}
		bw.close();
		long endTime = System.currentTimeMillis();
		System.out.println("used time : " + (endTime - startTime));
	}

	public static double getScore(HashSet<Integer> neighbor,
			HashSet<Integer> set, double al_ga) {
		double score = 0;
		for (int id : neighbor) {
			if (set.contains(id)) {
				score++;
			}
		}

		return score - al_ga * (Math.sqrt(set.size()+1));

	}
}
