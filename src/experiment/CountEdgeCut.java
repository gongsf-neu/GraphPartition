package experiment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CountEdgeCut {
	
	public static void main(String[] args) throws IOException {
		String input = "/home/gongsf/project/graphPartition/dataSet/BerkStan/metis/contribution_no/weight_no/part";
		int total = 0;
		BufferedReader[] brs = new BufferedReader[4];
		for(int i = 0; i < 4; i++) {
			String line = null;
			int sum = 0;
			brs[i] = new BufferedReader(new FileReader(input+i));
			while((line = brs[i].readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				while(st.hasMoreTokens()) {
					int nei = Integer.parseInt(st.nextToken());
					if(nei % 4 != i) {
						sum++;	
					}
				}
			}
			total += sum;
			System.out.println(sum);
			brs[i].close();
		}
		System.out.println(total);
	}

}
