package test;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

public class TestTIntHashSet {
	
	public static void main(String[] args) {
		TIntHashSet set = new TIntHashSet();
		set.add(1);
		set.add(2);
		TIntIterator itr = set.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
	}

}
