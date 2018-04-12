package util;

import gnu.trove.map.hash.TIntDoubleHashMap;

import java.util.Map;

public class WeightVertex {
	
	public int id;
	public double weight;
	public double nweight;
	public TIntDoubleHashMap neighbor;
	public boolean isPart;
	
	public WeightVertex(int id, double weight, TIntDoubleHashMap neighbor){
		this.id = id;
		this.weight = weight;
		this.neighbor = neighbor;
		this.nweight = 0;
		isPart = false;
	}
	
	@Override
	public String toString() {
		String s="";
		for(int key:neighbor.keys())
			s+=key+" ";
		return s;
	}

}
