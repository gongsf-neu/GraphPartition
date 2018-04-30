package WebGraph;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.set.hash.TIntHashSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import util.WeightVertex;

public class DirectToNDirect {

	public static void main(String[] args) throws IOException {
		String input = "";
		String output = "";
		int pointNum = -1;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}
			if (args[i].equals("-out")) {
				output = args[++i];
			}
			if (args[i].equals("-pointNum")) {
				pointNum = Integer.parseInt(args[++i]);
			}
		}

		TIntHashSet[] points = new TIntHashSet[pointNum];

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));

		String line = null;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			TIntHashSet neighbor = new TIntHashSet();
			while (st.hasMoreTokens()) {
				int key = Integer.parseInt(st.nextToken());
				if (key != id) {
					neighbor.add(key);
				}
			}
			points[id] = neighbor;
//			if (id == pointNum) {
//				System.out.println(id);
//			}
		}
		System.out.println("load finished");

		for (int i = 0; i < pointNum; i++) {
			TIntHashSet set = points[i];
			if (set == null || set.size() == 0) {
				 System.out.println("points " + i + " is null");
			} else {
				TIntIterator itr = set.iterator();
				while(itr.hasNext()){
				int id = itr.next();	
				if (!points[id].contains(i)) {
						points[id].add(i);
					}
				}
			}
		}
		System.out.println("undirect finished");

		// for (int j = 0; j < pointNum; j++) {
		// if (points[j] == null) {
		// System.out.println(j);
		// }
		// edgeNum += points[j].neighbor.size();
		// }
		for (int i = 0; i < pointNum; i++) {
			TIntHashSet set = points[i];
			bw.write(i + "\t");
			TIntIterator itr = set.iterator();
			while(itr.hasNext()){
				bw.write(itr.next() + " ");
			}
			bw.write("\n");
		}
		br.close();
		bw.close();
	}
}
