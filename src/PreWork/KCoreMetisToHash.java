package PreWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KCoreMetisToHash {

	public static void main(String[] args) throws IOException {

		String coreInput = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/core.txt";
		String idToMetis = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/idMap_last_metis.txt";
		String partInfo = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/metis_input_last.txt.part.3";
		String outputPath = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/part";
		int pointNum = 851991 + 1;
		int partNum = 3;

		int[] idMap = new int[pointNum];
		BufferedReader idToMetisBr = new BufferedReader(new FileReader(
				idToMetis));
		String line = null;
		while ((line = idToMetisBr.readLine()) != null) {
			String[] ss = line.split("\\s+");
			idMap[Integer.parseInt(ss[1])] = Integer.parseInt(ss[0]);
		}
		idToMetisBr.close();

		BufferedWriter[] bws = new BufferedWriter[partNum];
		for (int i = 0; i < partNum; i++) {
			bws[i] = new BufferedWriter(new FileWriter(outputPath + i));
		}

		BufferedReader infoBr = new BufferedReader(new FileReader(partInfo));
		line = null;
		for (int i = 1; (line = infoBr.readLine()) != null; i++) {
			int partId = Integer.parseInt(line);
			bws[partId].write(idMap[i] + "\n");
		}
		infoBr.close();

		for (int i = 0; i < partNum; i++) {
			bws[i].close();
		}

	}

}
