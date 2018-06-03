package contributionSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class ContributionSetByIndegSqrt {
	
	public static void main(String[] args) throws IOException {
		String input = "";
		String output = "";
		int vertexNum = -1;
		int deep = -1;
		double base = -1;
		double decay = -1;

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
			if (args[i].equals("-deep")) {
				deep = Integer.parseInt(args[++i]);
			}
			if (args[i].equals("-base")) {
				base = Double.parseDouble(args[++i]);
			}
			if (args[i].equals("-decay")) {
				decay = Double.parseDouble(args[++i]);
			}
		}

		if (vertexNum == -1  || deep == -1) {
			System.out.println("the parameter is error");
			System.exit(0);
		}

		double[] indegrees = new double[vertexNum];
		int countVertex = 0;

		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = null;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			if (i != id) {
				System.out.println("the vertex index is error");
				System.exit(0);
			}
			while (st.hasMoreTokens()) {
				int key = Integer.parseInt(st.nextToken());
				indegrees[key]++;
			}
			countVertex++;
		}
		br.close();
		if (countVertex != vertexNum) {
			System.out.println("vertex num error");
			System.exit(0);
		}

		System.out.println("load finish");
		long start = System.currentTimeMillis();

		// set contribution
		double contribution[] = new double[vertexNum];
		for (int j = 0; j < deep; j++) {
			br = new BufferedReader(new FileReader(input));
			line = null;
			for (int i = 0; (line = br.readLine()) != null; i++) {
				StringTokenizer st = new StringTokenizer(line);
				st.nextToken();// skip id
				while (st.hasMoreTokens()) {
					int key = Integer.parseInt(st.nextToken());
					contribution[key] += indegrees[i] * decay;
				}
			}
			br.close();
			for(int i = 0; i < vertexNum; i++){
				indegrees[i] = contribution[i];
				contribution[i] = 1;
			}
			System.out.println("deep " + j + " is finished");
		}

		System.out.println("set vertex weight finish");

		long end = System.currentTimeMillis();
		System.out.println("use time : " + (end - start));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < vertexNum; i++) {
			if (indegrees[i] < 1) {
				bw.write(i + "\t" + 1 + "\n");
			} else {
				bw.write(i + "\t" + Math.pow(indegrees[i], 1/base)
						+ "\n");
			}
		}
		bw.close();
		System.out.println("contribution setting by indegsqrt" + base + " is finished!!");
	}
}
