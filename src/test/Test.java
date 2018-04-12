package test;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;

public class Test {
	
	public static void main(String[] args) {
		TIntDoubleHashMap idmap = new TIntDoubleHashMap();
		idmap.put(1, 2.0);
		idmap.put(3, 2.2);
		idmap.put(4, 2.3);
		TIntDoubleIterator itr = idmap.iterator();
		while(itr.hasNext()){
			itr.advance();
			System.out.println(itr.key() + " " + itr.key());
		}
	}
}
