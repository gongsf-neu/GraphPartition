package PreWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

public class CompleteData {
	
	public static void main(String[] args) throws IOException {
		
		String input = "/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc/vertex_noComplete.txt";
		String output = "/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc/vertex.txt";
		int pointNum = 4206784;
		
		int count = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		
		Random random = new Random();
		
		String line = null;
		while((line = br.readLine()) != null){
			StringTokenizer stk = new StringTokenizer(line);
			int id = Integer.parseInt(stk.nextToken());
			bw.write(id + "\t");
			if(stk.hasMoreTokens()){
				while(stk.hasMoreTokens()){
					bw.write(Integer.parseInt(stk.nextToken()) + " ");
				}
			}else{
				bw.write(Math.abs(random.nextInt()%pointNum)+"");
			}
			bw.write("\n");
			count++;
		}
		System.out.println(count);
		bw.close();
	}
}
