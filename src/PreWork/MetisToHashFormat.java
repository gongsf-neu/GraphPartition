package PreWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class MetisToHashFormat {

	public static void main(String[] args) throws IOException {
		String partInfo = "/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc/vertex_metis_input.txt.part.4";
		String orgData = "/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc/vertex.txt";
		String output = "/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc/vertex_metis_nw_part";
		int partNum = 4;
		int pointNum = 4206784;

		int[] partCount = new int[partNum];
		int[] pointIdMap = new int[pointNum];

		BufferedWriter[] bws = new BufferedWriter[partNum];
		for (int i = 0; i < partNum; i++) {
			bws[i] = new BufferedWriter(new FileWriter(output + i));
		}

		BufferedReader infobr = new BufferedReader(new FileReader(partInfo));
		String pline = null;
		for (int i = 0; (pline = infobr.readLine()) != null; i++) {
			int prtId = Integer.parseInt(pline);
			pointIdMap[i] = partNum * partCount[prtId] + prtId;
			// bws[prtId].write(i + " " + pointIdMap[i] + "\n");
			partCount[prtId]++;
		}
		infobr.close();
		System.out.println("map finished!!");

		infobr = new BufferedReader(new FileReader(partInfo));
		String line = null;
		BufferedReader orgbr = new BufferedReader(new FileReader(orgData));
		while ((line = orgbr.readLine()) != null) {
			pline = infobr.readLine();
			int prtId = Integer.parseInt(pline);
			StringTokenizer st = new StringTokenizer(line);
			bws[prtId].write(pointIdMap[Integer.parseInt(st.nextToken())]
					+ "\t");
			while (st.hasMoreTokens()) {
				bws[prtId].write(pointIdMap[Integer.parseInt(st.nextToken())]
						+ " ");
			}
			bws[prtId].write("\n");
		}
		infobr.close();
		orgbr.close();
		for (int i = 0; i < partNum; i++) {
			bws[i].close();
		}
		System.out.println("re-set ids finished！！");
	}

}
