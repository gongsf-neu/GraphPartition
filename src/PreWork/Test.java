package PreWork;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TIntHashSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("/home/gongsf/Desktop/PageRank/Google_90w/vertex.txt"));
		String line = null;
		TIntHashSet nodeSet = new TIntHashSet();
		int last=-1;
		TIntObjectHashMap<TIntHashSet> edges = new TIntObjectHashMap<>();
		while ((line = br.readLine()) != null) {
			String[] tmp = line.split("\t");
			int src = Integer.parseInt(tmp[0]);
			tmp = tmp[1].split(" ");
			edges.put(src,new TIntHashSet());
			for(String sDst:tmp){
				int dst = Integer.parseInt(sDst);
				nodeSet.add(src);
				nodeSet.add(dst);
				if(src==dst)
					throw new RuntimeException();
				edges.get(src).add(dst);
				if(edges.get(dst)==null)
					edges.put(dst,new TIntHashSet());
				edges.get(dst).add(src);
			}
			if(src-last!=1)
				throw new RuntimeException(src+" "+last);
			last = src;
		}
		int cnt=0;
		for(int src:edges.keys())
			cnt+=edges.get(src).size();
		System.out.println(cnt);
	}

}
