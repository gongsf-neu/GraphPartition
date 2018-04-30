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

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));

		String line = br.readLine();
		bw.write(line + "\n");
//		bw.write(mode + "\n");

		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken(); //skip id
			st.nextToken(); // skip weight
			int id = 0;
			while (st.hasMoreTokens()) {
				id = Integer.parseInt(st.nextToken()) + 1;
				bw.write(id + " ");
				st.nextToken();// skip communication
			}
			bw.write("\n");
		}

		br.close();
		bw.close();
		System.out.println("format 2 metis input finished!!");
	}
}
