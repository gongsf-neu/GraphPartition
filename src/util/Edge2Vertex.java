package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Edge2Vertex {

	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/program/graphPartition/dataSet/LiveJournal/edge.txt";
		String output = "/home/gongsf/program/graphPartition/dataSet/LiveJournal/vertex.txt";

		int vertexNum = 4847571;
		StringBuilder[] outs = new StringBuilder[vertexNum];

		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while ((line = br.readLine()) != null) {
			String[] ss = line.split("\\s+");
			int src = Integer.parseInt(ss[0]);
			int dest = Integer.parseInt(ss[1]);
			if (outs[src] == null) {
				outs[src] = new StringBuilder();
				outs[src].append(dest + " " );
			} else {
				outs[src].append(dest + " ");
			}
		}
		br.close();
		Random random = new Random();
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < vertexNum; i++) {
			bw.write(i + "\t");
			if (outs[i] == null) {
				bw.write(Math.abs(random.nextInt()%vertexNum) + "");
			} else {
				bw.write(outs[i].toString());
			}
			bw.write("\n");
		}
		bw.close();
	}
}
