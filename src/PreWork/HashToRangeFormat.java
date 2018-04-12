package PreWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class HashToRangeFormat {

	public static void main(String[] args) throws IOException {
		String hashInput = "/home/gongsf/program/graphPartition/data/Google_90w/vertex.txt";
		String rangeOutput = "/home/gongsf/program/graphPartition/data/Google_90w/edge.txt";

		int pointNum = 1000000;
		int partNum = 1;
		int count = 0;
		int[] pidMap = new int[pointNum];
		BufferedReader[] brs = new BufferedReader[partNum];
		for (int i = 0; i < partNum; i++) {
			brs[i] = new BufferedReader(new FileReader(hashInput + i));
			String line = null;
			for (int j = 0; (line = brs[i].readLine()) != null; j++, count++) {
				StringTokenizer stk = new StringTokenizer(line);
				pidMap[Integer.parseInt(stk.nextToken())] = count;
			}
			brs[i].close();
		}
		System.out.println(count);

		BufferedWriter bw = new BufferedWriter(new FileWriter(rangeOutput));
		for (int i = 0; i < partNum; i++) {
			brs[i] = new BufferedReader(new FileReader(hashInput + i));
			System.out.println(hashInput);
			String line = null;
			for (int j = 0; (line = brs[i].readLine()) != null; j++) {
				StringTokenizer stk = new StringTokenizer(line);
				int id = Integer.parseInt(stk.nextToken());
				while (stk.hasMoreTokens()) {
					int neid = Integer.parseInt(stk.nextToken());
					bw.write(pidMap[id] + "\t" + pidMap[neid] + "\n");
				}
			}
			brs[i].close();
		}
		bw.close();
	}
}
