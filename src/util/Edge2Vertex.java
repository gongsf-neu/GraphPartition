package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class Edge2Vertex {

	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/program/dynamicSchedule/dataSet/roadNet/roadNet-CA.txt";
		String output = "/home/gongsf/program/dynamicSchedule/dataSet/roadNet/vertex.txt";
		int vertexNum = 1971281;
		
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
			if(line.startsWith("#"))
				continue;
			String[] ss = line.split("\\s+");
			int src = Integer.parseInt(ss[0]);
			int dest = Integer.parseInt(ss[1]);
			if(src > vertexNum || dest > vertexNum){
				System.out.println("the index is larger");
				System.out.println(src + "\t" + dest);
				System.exit(0);
			}
			if (outs[src] == null) {
				outs[src] = new StringBuilder(src + "\t");
				outs[src].append(dest + " " );
			} else {
				outs[src].append(dest + " ");
			}
		}
		br.close();
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0, j = 0; i < vertexNum; i++) {
			if (outs[i] != null) {
				list.add(j++);
			}else{
				list.add(-1);
			}
		}
		
		
		int count0outdegree = 0;
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < vertexNum; i++) {
			if (outs[i] != null) {
				if(list.get(i) < 0){
					System.out.println("oh no");
					System.exit(0);
				}
				bw.write(list.get(i) + "\t");
				String[] ss = outs[i].toString().split("\\s+");
				for(String s : ss){
					int id = Integer.parseInt(s);
					if(list.get(id) < 0){
						System.out.println("oh no");
						System.exit(0);
					}
					bw.write(list.get(id) + " ");
				}
				bw.write("\n");
			}else{
				count0outdegree++;
//				System.out.println(i + " vertex the outdegree is zero");
			}
		}
		bw.close();
		System.out.println(count0outdegree);
		
		System.out.println("from edge to vertex trasform finish");
	}
}
