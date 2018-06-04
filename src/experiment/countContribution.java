package experiment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class countContribution {
	public static void main(String[] args) throws IOException {
		String contributionInfo = "/home/gongsf/project/graphPartition/dataSet/Google/contribution_pagerank";
		String partition = "/home/gongsf/project/graphPartition/dataSet/Google/metis/contribution_pagerank/weight_no/vertex";
		
		int vertexNum = 916428;
		
		double[] contribution = new double[vertexNum];
		
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(contributionInfo));
		while((line = br.readLine()) != null) {
			String[] ss = line.split("\\s+");
			int id = Integer.parseInt(ss[0]);
			double cont = Double.parseDouble(ss[1]);
			contribution[id]=cont;
		}
		br.close();
		
		BufferedReader[] brs = new BufferedReader[4];
		for(int i = 0; i < 4; i++) {
			double value = 0;
			brs[i] = new BufferedReader(new FileReader(partition+i));
			while((line = brs[i].readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				value += contribution[id];
			}
			System.out.println(value);
			brs[i].close();
		}
	}
}
