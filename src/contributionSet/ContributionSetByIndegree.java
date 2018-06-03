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

/**
 * @author gongsf this class is used to set contribution of vertices and
 *         communication of edges the output is an undirected graph
 * 
 */
public class ContributionSetByIndegree {

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		String input = "";
		String output = "";
		int pointNum = -1;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
			if (args[i].equals("-vertexNum")) {
				pointNum = Integer.parseInt(args[++i]);
			}
		}
		int[] indegrees = new int[pointNum];
		int count = 0;
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			if(i != id){
				System.out.println("the vertex index is error");
				System.exit(0);
			}
			while (st.hasMoreTokens()) {
				int key = Integer.parseInt(st.nextToken());
				indegrees[key]++;
			}
			count++;
		}
		br.close();
		if (count != pointNum) {
			System.out.println("vertex num error");
			System.exit(0);
		}

		System.out.println("load finish");
		long start = System.currentTimeMillis();

		// set vertex weight
		for (int j = 0; j < pointNum; j++) {
			indegrees[j]++;
		}

		long end = System.currentTimeMillis();
		System.out.println("use time : " + (end - start));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < pointNum; i++) {
			bw.write(i + "\t" + indegrees[i] + "\n");
		}
		bw.close();
		System.out.println("contribution setting by indegree is finished!!");
	}

}
