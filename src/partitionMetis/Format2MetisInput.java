package partitionMetis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Format2MetisInput {

	public static void main(String[] args) throws IOException {

		String input = "";
		String output = "";

//		String mode = "011";
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
			if(args[i].equals("-out")){
				output = args[++i];
			}
		}

		String line = null;
		int edgeCount = 0;
		int vertexCount = 0;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line);
			edgeCount += (st.countTokens()-1);
			vertexCount++;
		}
		br.close();
		if(edgeCount % 2 != 0){
			System.out.println("the graph is not undirected");
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		bw.write(vertexCount  + " " + edgeCount/2 + "\n");
		
		br = new BufferedReader(new FileReader(input));

		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken(); //skip id
			int id = 0;
			while (st.hasMoreTokens()) {
				id = Integer.parseInt(st.nextToken()) + 1;
				bw.write(id + " ");
			}
			bw.write("\n");
		}

		br.close();
		bw.close();
		System.out.println("format 2 metis input finished!!");
	}
}
