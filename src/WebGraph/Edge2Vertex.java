package WebGraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Edge2Vertex {

	public static void main(String[] args) throws IOException {
		String input = "";
		String output = "";
		int vertexNum = -1;
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
			if(args[i].equals("-out")){
				output = args[++i];
			}
			if(args[i].equals("-vertexNum")){
				vertexNum = Integer.parseInt(args[++i]);
			}
		}
		
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
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < vertexNum; i++) {
			bw.write(i + "\t");
			if (outs[i] != null) {
				bw.write(outs[i].toString());
			}
			bw.write("\n");
		}
		bw.close();
		
		System.out.println("from edge to vertex trasform finish");
	}
}
