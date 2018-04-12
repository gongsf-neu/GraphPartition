package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class CountContribution {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_InDeg.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/Google_90w/vertex_weight_InDeg_.info";
		int vertexNum = 0;
		double[] contributions;
		int groupNum = 20;

		for (int i = 0; i < args.length; i++) {
			if (args.equals("-help") || args.equals("-h")
					|| args.equals("--help")) {
				System.out.println("-in \t input file path");
				System.out.println("-groupNum \t partitions num in the range");
			}
			if (args.equals("-in")) {
				input = args[++i];
			}
			if (args.equals("-groupNum")) {
				groupNum = Integer.parseInt(args[++i]);
			}
			if (args.equals("-out")) {
				output = args[++i];
			}
		}

		// load contribution
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = br.readLine();// read top 1 line
		vertexNum = Integer.parseInt(line.split("\\s+")[0]);
		contributions = new double[vertexNum];
		double maxCont = 0;
		double minCont = Double.POSITIVE_INFINITY;
		int id = -1;
		double contribution = 0;
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			id = Integer.parseInt(st.nextToken());
			contribution = Double.parseDouble(st.nextToken());
			contributions[id] = contribution;
			if (maxCont < contribution) {
				maxCont = contribution;
			}
			if (minCont > contribution) {
				minCont = contribution;
			}
		}
		br.close();

		double dif = maxCont - minCont;
		int range = (int) Math.ceil((dif / groupNum));
		int[] count = new int[(int) Math.ceil(dif/range)];// 

		for (double d : contributions) {
			if((int) ((d - minCont) / range)<0){
				System.out.println(d + " " + minCont + " " + range);
			}
			count[(int) ((d - minCont) / range)]++;
		}
		
		int midId = (int) Math.ceil(dif/range)/2;
		int up = 0;
		int down = 0;
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < groupNum; i++) {
			if (count[i] > 0) {
				if(i < midId){
					down += count[i];
				}else{
					up += count[i];
				}
				System.out.println(i * range + "--" + (i + 1) * range + " : "
						+ count[i]);
				bw.write(i * range + "\t" + (i + 1) * range + " " + count[i]
						+ "\n");
			}
		}
		bw.close();
		System.out.println(up*1.0/(vertexNum));
	}
}
