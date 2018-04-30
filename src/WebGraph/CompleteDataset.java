package WebGraph;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

public class CompleteDataset {

	public static void main(String[] args) throws NumberFormatException,
			IOException {

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
			if (args[i].equals("-vertexNum")) {
				pointNum = Integer.parseInt(args[++i]);
			}
		}

		int degreeCount = 0;
		int realNum = 0;
		TIntHashSet[] points = new TIntHashSet[pointNum];
		BufferedReader br = new BufferedReader(new FileReader(input));
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
			degreeCount += neighbor.size();
			if (neighbor.size() != 0) {
				realNum++;
			}
		}
		br.close();
		System.out.println("load finished");
		int avg = degreeCount / realNum;
		System.out.println("average = " + avg);
		Random random = new Random();
		for (int i = 0; i < pointNum; i++) {
			TIntHashSet set = points[i];
			if (set == null || set.size() == 0) {
				for (int j = 0; j < avg; j++) {
					set.add(Math.abs(random.nextInt() % pointNum));
				}
			}
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < pointNum; i++) {
			TIntHashSet set = points[i];
			bw.write(i + "\t");
			TIntIterator itr = set.iterator();
			while (itr.hasNext()) {
				bw.write(itr.next() + " ");
			}
			bw.write("\n");
		}
		bw.close();
		System.out.println("process original dataSet finished!!!");
	}

}
