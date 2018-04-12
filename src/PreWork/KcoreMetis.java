package PreWork;

import gnu.trove.map.hash.TIntIntHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class KcoreMetis {

	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/last";
		String mapPath = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/idMap_last_metis.txt";
		String output = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/metis_input_last.txt";
		String coreInPath = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/result-120000";
		String coreOutPath = "/home/gongsf/Desktop/PageRank/Google_90w/kcore/120000/core.txt";

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter mapbw = new BufferedWriter(new FileWriter(mapPath));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		BufferedReader coreBr = new BufferedReader(new FileReader(coreInPath));
		BufferedWriter coreBw = new BufferedWriter(new FileWriter(coreOutPath));
		
		String line = null;
		while((line = coreBr.readLine()) != null){
			coreBw.write(line+"\n");
		}
		coreBr.close();

		TIntIntHashMap tiimap = new TIntIntHashMap();

		int edgeCount = 0;
		int pointCount = 0;

		line = null;
		for (int i = 1; (line = br.readLine()) != null; ) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			if (!st.hasMoreTokens()) {
//				System.out.println("there is vertex with 0 edge");
				
				coreBw.write(id + "\n");
			} else {
				while (st.hasMoreTokens()) {
					int key = Integer.parseInt(st.nextToken());
					if (key != id) {
						edgeCount++;
					}
				}
				mapbw.write(id + " " + i + "\n");
				tiimap.put(id, i);
				i++;
				pointCount++;
			}
		}
		br.close();
		mapbw.close();
		coreBw.close();

		edgeCount = edgeCount / 2;
		bw.write(pointCount + " " + edgeCount + "\n");

		br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			if(st.hasMoreTokens()){
				while (st.hasMoreTokens()) {
					int key = Integer.parseInt(st.nextToken());
					if (key != id) {
						if(tiimap.get(key)==0){
							System.out.println(key);
							System.exit(0);
						}
						bw.write(tiimap.get(key) + " ");
					}
				}
				bw.write("\n");
			}
		}
		bw.close();
	}
}
