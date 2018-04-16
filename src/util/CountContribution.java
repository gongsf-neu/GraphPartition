package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class CountContribution {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/WiKi/contribution_php";
		String output = "/home/gongsf/program/graphPartition/dataSet/WiKi/contribution_php.info";
		int vertexNum = 4206784;
		double[] contributions;
		int groupNum = 20;
		int[] count;

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
		
		count = new int[groupNum];
		double interval = 1.0 / groupNum;
		contributions = new double[vertexNum];

		// load contribution
		BufferedReader br = new BufferedReader(new FileReader(input));
		double maxCont = 0;
		int id = -1;
		String line = null;
		double contribution = 0;
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			id = Integer.parseInt(st.nextToken());
			st.nextToken(); //skip value
			contribution = Double.parseDouble(st.nextToken());
			if(id >= vertexNum){
				System.out.println(id);
			}
			contributions[id] = contribution;
			if (maxCont < contribution) {
				maxCont = contribution;
			}
		}
		br.close();
		
		System.out.println(maxCont);
		
		double tmp = 0;
		int index = 0;
		for(int i = 0; i < vertexNum; i++){
			if(contributions[i] == maxCont){
				count[groupNum - 1]++;
			}else{
				tmp = contributions[i]*1.0 / maxCont;
				index = (int) (tmp / interval);
				count[index]++;
			}
		}
		
		DecimalFormat df = new DecimalFormat("0.00");
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < groupNum; i++) {
			bw.write(df.format(interval * i) + " -- "
					+ df.format(interval * (i + 1)) + " : " + count[i] + "\n");
		}
		bw.close();

//		double dif = maxCont - minCont;
//		int range = (int) Math.ceil((dif / groupNum));
//		int[] count = new int[(int) Math.ceil(dif/range)];// 
//
//		for (double d : contributions) {
//			if((int) ((d - minCont) / range)<0){
//				System.out.println(d + " " + minCont + " " + range);
//			}
//			count[(int) ((d - minCont) / range)]++;
//		}
//		
//		int midId = (int) Math.ceil(dif/range)/2;
//		int up = 0;
//		int down = 0;
//		
//		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
//		for (int i = 0; i < groupNum; i++) {
//			if (count[i] > 0) {
//				if(i < midId){
//					down += count[i];
//				}else{
//					up += count[i];
//				}
//				System.out.println(i * range + "--" + (i + 1) * range + " : "
//						+ count[i]);
//				bw.write(i * range + "\t" + (i + 1) * range + " " + count[i]
//						+ "\n");
//			}
//		}
//		bw.close();
//		System.out.println(up*1.0/(vertexNum));
	}
}
