package partition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class IdPartToHashPart {

	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/Desktop/PageRank/Google_90w/fennel/2/part";
		String originalPath = "/home/gongsf/Desktop/PageRank/Google_90w/vertex.txt";
		String output = "/home/gongsf/Desktop/PageRank/Google_90w/fennel/result/part";
		int partNum = 4;
		int pointNum = 916428;
		
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
			
			if(args[i].equals("-out")){
				output = args[++i];
			}
			
			if(args[i].equals("-org")){
				originalPath = args[++i];
			}
			
			if(args[i].equals("-prt")){
				partNum = Integer.parseInt(args[++i]);
			}
			
			if(args[i].equals("-pNum")){
				pointNum = Integer.parseInt(args[++i]);
			}
		}
		
		int[] idMap = new int[pointNum];

		BufferedReader[] brs = new BufferedReader[partNum];
		for (int i = 0; i < partNum; i++) {
			brs[i] = new BufferedReader(new FileReader(input + i));
			String line = null;
			for (int j = 0; (line = brs[i].readLine()) != null; j++) {
				int id = Integer.parseInt(line);
				idMap[id] = j * partNum + i;
			}
			brs[i].close();
		}

		BufferedReader orgbr = new BufferedReader(new FileReader(originalPath));
		BufferedWriter[] bws = new BufferedWriter[partNum];

		for (int i = 0; i < partNum; i++) {
			bws[i] = new BufferedWriter(new FileWriter(output + i));
		}
		String line = null;
		while ((line = orgbr.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			int prtId = idMap[id] % partNum;
			bws[prtId].write(idMap[id] + "\t");
			while (st.hasMoreTokens()) {
				bws[prtId].write(idMap[Integer.parseInt(st.nextToken())] + " ");
			}
			bws[prtId].write("\n");
		}
		orgbr.close();
		for (int i = 0; i < partNum; i++) {
			bws[i].close();
		}
	}
}
