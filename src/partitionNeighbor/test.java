package partitionNeighbor;

import gnu.trove.map.hash.TIntDoubleHashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import util.WeightVertex;

public class test {

	public static int boundry = 0;

	public static void main(String[] args) throws IOException {
		// contribution and communication file
		String input = "/home/gongsf/program/graphPartition/dataSet/Google/"
				+ "vertex_contribution_Indeglog_deep_2.txt";

		int vertexNum = 916428;
		WeightVertex[] vertices;

		vertices = new WeightVertex[vertexNum];

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
		double maxContribution = 0;
		double minContribution = 0;

		int sampleNum = 2000;
		WeightVertex[] samples = new WeightVertex[sampleNum];
		for (int i = 0; i < sampleNum; i++) {
			samples[i] = vertices[i];
		}
		Random random = new Random();
		for (int i = sampleNum; i < vertices.length; i++) {
			int r = Math.abs(random.nextInt(i));
			if (r < sampleNum) {
				samples[r] = vertices[i];
			}
		}
		System.out.println("load finish");
		Arrays.sort(samples, new Util().new MyCompartor());
		for(WeightVertex v : samples){
			System.out.println(v.weight);
		}
		
		int index = (int) (0.2 * sampleNum);
		double bound = samples[index].weight;
	}
}
