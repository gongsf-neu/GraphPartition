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
		
		double accuCon = 0;

		int coreId = maxId;
		for (int i = partNum-1; i > 0; i--) {
			System.out.println(i + " partition is started");
			double con = 0;
			Candidates candidates = new Candidates();
			candidates.insert(coreId);
			
			//while(con<averageCon)
			for (int j = 0; con < averageCon; j++) {
//				if (j % 10000 == 0) {
//					System.out.println(con + " " + averageCon);
//					System.out.println(candidates.size);
//				}
//				candidates.checkLoop();
				coreId = candidates.popAndInsert(vertices);
//				candidates.checkLoop();
//				System.out.println("candidates vertex id " + coreId);
				WeightVertex v = vertices[coreId];
				if (!v.isPart) {
					partInfo[v.id] = i;
					v.isPart = true;
					con += v.weight;
				} else{
					System.out.println("candidates return and partitioned vertex, error");
					System.out.println(partInfo[coreId]);
					System.exit(0);
				}
			}
			accuCon += con;
			System.out.println(i + " partition is finished with contribution : " + con);
			coreId = getMaxId(vertices);
			System.out.println(partInfo[coreId] + " " + coreId);
			if(vertices[coreId].isPart){
				System.out.println("maxId partitioned vertex, error");
				System.out.println(partInfo[coreId] + " " + coreId);
				System.exit(0);
			}
		}
		
		System.out.println("0 partition is finished with contribution : " + (allCon- accuCon));

		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for(int i = 0; i < vertexNum; i++){
			bw.write(partInfo[i] + "\n");
		}
		bw.close();

	}
	
	public static int getMaxId(WeightVertex[] vertices){
		int maxId = 0;
		double maxWeight = vertices[0].weight;
		for(WeightVertex vertex : vertices){
			if(!vertex.isPart && vertex.weight > maxWeight){
				maxId = vertex.id;
			}
		}
		return maxId;
	}
}
