package test;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		System.out.println(df.format(1123.214));
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(10, 5);
		for(int val : list){
			System.out.println(val);
		}
	}
}
