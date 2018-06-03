package contributionSet;

import gnu.trove.map.hash.TIntDoubleHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

import util.WeightVertex;

public class ContributionSetByUnstable {

	public static void main(String[] args) throws IOException {
		String input = "";
		String output = "";
		int vertexNum = -1;
		double threshold = -1;
		int deep = -1;
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
			if (args[i].equals("-thres")) {
				threshold = Double.parseDouble(args[++i]);
			}
			if(args[i].equals("-deep")){
				deep = Integer.parseInt(args[++i]);
			}
			if(args[i].equals("-decay")){
				decay = Double.parseDouble(args[++i]);
			}
		}

		if (vertexNum == -1 || threshold == -1 || deep == -1) {
			System.out.println("the parameter is error");
			System.exit(0);
		}

		double[] indegrees = new double[vertexNum];
		long countIndegrees = 0;
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
				countIndegrees++;
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

		// find unstable vertex
		int countUnstable = 0;
		double thres = getThres(indegrees, threshold);
		boolean[] easyStable = new boolean[vertexNum];
		for (int i = 0; i < vertexNum; i++) {
			indegrees[i]++;
			if (indegrees[i] > thres) {
				easyStable[i] = false;
				countUnstable++;
			} else {
				easyStable[i] = true;
			}
			indegrees[i] = 1;
		}
		System.out.println("the rate of unstable : " + (countUnstable*1.0/vertexNum));

		// set contribution
		double contribution[] = new double[vertexNum];
		for(int j = 0; j < deep; j++){
			br = new BufferedReader(new FileReader(input));
			line = null;
			for (int i = 0; (line = br.readLine()) != null; i++) {
				if (!easyStable[i]) {
					StringTokenizer st = new StringTokenizer(line);
					st.nextToken();// skip id
					while (st.hasMoreTokens()) {
						int key = Integer.parseInt(st.nextToken());
						contribution[key]+= indegrees[i]*decay;
					}
				}
			}
			br.close();
			countUnstable = 0;
			for(int i = 0; i < vertexNum; i++){
				indegrees[i] = contribution[i];
				if(indegrees[i] > 1.1){
					easyStable[i] = false;
					countUnstable++;
				}else{
					easyStable[i] = true;
				}
				contribution[i] = 1;
			}
			System.out.println("the rate of unstable : " + (countUnstable*1.0/vertexNum));
		}

		System.out.println("set vertex weight finish");

		long end = System.currentTimeMillis();
		System.out.println("use time : " + (end - start));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < vertexNum; i++) {
			bw.write(i + "\t" + indegrees[i] + "\n");
		}
		bw.close();
		System.out.println("contribution setting by unstable is finished!!");
	}
	
	public static double getThres(double[] value, double thres){
		int num = value.length;
		int sampleNum = 2000;
		int cutIndex = (int) (sampleNum * (1-thres));
		System.out.println("cutIndex " + cutIndex);
		Random random = new Random();
		double[] samples = new double[sampleNum];
		for(int i = 0; i < num; i++){
			if(i < sampleNum){
				samples[i] = value[i];
			}else{
				int index = Math.abs(random.nextInt()%sampleNum);
				if(index < sampleNum){
					samples[index] = value[i];
				}
			}
		}
		Arrays.sort(samples);
		
		return samples[cutIndex];
	}
}