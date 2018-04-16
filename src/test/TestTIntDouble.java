package test;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;

public class TestTIntDouble {

	public static void main(String[] args) {
		TIntDoubleHashMap map = new TIntDoubleHashMap();
		map.put(1, 1.1);
		map.put(2, 2.2);
		map.put(3, 3.3);
		map.put(4, 4.4);

		TIntDoubleIterator itr = map.iterator();
		while (itr.hasNext()) {
			itr.advance();
			if (itr.key() == 2)
				itr.remove();
		}

		itr = map.iterator();
		while (itr.hasNext()) {
			itr.advance();
			System.out.println(itr.key() + " " + itr.value());
		}
	}

}
