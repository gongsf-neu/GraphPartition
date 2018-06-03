package WebGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CountEdge {
	
	public static void main(String[] args) throws IOException {
		String input = "";
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
		}
		
		long count = 0;
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line);
			count += (st.countTokens()-1);
		}
		br.close();
		if(count % 2 != 0){
			System.out.println("the graph is not undirected");
		}
		System.out.println(input + " vertexNum : " + count/2);
	}

}
