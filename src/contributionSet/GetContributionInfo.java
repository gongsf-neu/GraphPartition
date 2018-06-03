package contributionSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class GetContributionInfo {

	public static void main(String[] args) throws IOException {
		String input = "";
		String output = "";
		int groupNum = -1;
		int vertexNum = -1;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
			if (args[i].equals("-group")) {
				groupNum = Integer.parseInt(args[++i]);
			}
			if (args[i].equals("-vertexNum")) {
				vertexNum = Integer.parseInt(args[++i]);
			}
		}

		double maxContribution = 0;
		double minContribution = Double.MAX_VALUE;
		double[] contributions = new double[vertexNum];
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		for (int i = 0; (line = br.readLine()) != null; i++) {
			String[] ss = line.split("\\s+");
			double curCont = Double.parseDouble(ss[1]);
			contributions[i] = curCont;
			if (maxContribution < curCont) {
				maxContribution = curCont;
			}
			if (minContribution > curCont) {
				minContribution = curCont;
			}
		}
		br.close();

		double gap = maxContribution - minContribution;
		double groupGap = gap / groupNum;
		int[] countContribution = new int[groupNum];
		for (int i = 0; i < vertexNum; i++) {
			double cur = contributions[i] - minContribution;
			int index = (int) (cur / groupGap);
			if(contributions[i] == maxContribution){
				index = groupNum-1;
			}
			if (index >= groupNum) {
				System.out.println(contributions[i] + " " + maxContribution + " " + minContribution + " " + groupGap);
				System.out.println("the groupId is overhead the groupNum");
				System.out.println(index);
				System.exit(0);
			}
			countContribution[index]++;
		}

		DecimalFormat df = new DecimalFormat("0.0000");
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < groupNum; i++) {
			bw.write(df.format((minContribution + i * groupGap)) + "-"
					+ df.format((minContribution + (i+1) * groupGap)) + " : "
					+ countContribution[i] + "\n");
		}
		bw.close();

	}
}
