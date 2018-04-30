package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class CountComponent {
	
	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/Maiter/result/CC/part-0";
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-in")){
				input = args[++i];
			}
		}
		
		
		HashSet<String> set = new HashSet<String>();
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(input));
		while((line = br.readLine()) != null){
			String[] ss = line.split("\\s+");
			set.add(ss[1]);
		}
		br.close();
		System.out.println("there are " + set.size() + " connected component!!");
	}

}
