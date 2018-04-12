package fennelPartition;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Fennel_W {
	
	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_result.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/fennel/"
				+ "fennel_vertex_weight_result_partInfo";
		
		int edgeNum = 0;
		int vertexNum = 0;
		int[] partInfo;
		double[] contribution;
		int partNum = 4;
		
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

		long startTime = System.currentTimeMillis();
		
		output += "."+partNum;
		HashSet<Integer>[] parts = new HashSet[partNum];
		contribution = new double[partNum];
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
			TIntDoubleHashMap neighbor = new TIntDoubleHashMap();
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			double weight = Double.parseDouble(st.nextToken());
			while (st.hasMoreTokens()) {
				neighbor.put(Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken()));
			}

			double score = 0;
			double maxScore = -Double.MAX_VALUE;
			int maxPart = 0;
			for(int i = 0; i < partNum; i++){
				score = getScore(neighbor,parts[i], contribution[i]+weight, al_ga);
				if(maxScore < score){
					maxScore = score;
					maxPart = i;
				}
//				System.out.print(score + " ");
			}
//			System.out.println();
			parts[maxPart].add(id);
			contribution[maxPart] += weight;
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

	public static double getScore(TIntDoubleHashMap neighbor,
			HashSet<Integer> set, double contribution, double al_ga) {
		double score = 0;
		TIntDoubleIterator itr = neighbor.iterator();
		while(itr.hasNext()){
			itr.advance();
			int id = itr.key();
			if(set.contains(id)){
				score += itr.value();
			}
		}

		return score - al_ga * (Math.sqrt(contribution));

	}
}
