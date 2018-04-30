package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CombDegCont {
	
	public static void main(String[] args) throws IOException {
		String contribution = "";
		String degreePath = "";
		String output = "";
		int vertexNum = -1;
		int sampleNum = -1;
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-degreePath")){
				degreePath = args[++i];
			}
			if(args[i].equals("-contribution")){
				contribution = args[++i];
			}
			if(args[i].equals("-out")){
				output = args[++i];
			}
			if(args[i].equals("-vertexNum")){
				vertexNum = Integer.parseInt(args[++i]);
			}
			if(args[i].equals("-sample")){
				sampleNum = Integer.parseInt(args[++i]);
			}
		}
		
		String deg = null;
		String cont = null;
		String[] sample = new String[sampleNum];
		
		BufferedReader degbr = new BufferedReader(new FileReader(degreePath));
		BufferedReader contbr = new BufferedReader(new FileReader(contribution));
		Random random = new Random();
		for(int i = 0; (deg = degbr.readLine()) != null; i++){
			cont = contbr.readLine();
			if(i < sampleNum){
				sample[i] = deg + " " + cont;
			}else{
				int k = Math.abs(random.nextInt() % i);
				if(k < sampleNum){
					sample[k] = deg + " " + cont;
				}
			}
		}		
		degbr.close();
		contbr.close();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		for(String s : sample){
			String[] ss = s.split("\\s+");
			bw.write(ss[1] + " " + ss[3] + "\n");
		}
		bw.close();
		System.out.println("combine finish!!!");
	}
}
