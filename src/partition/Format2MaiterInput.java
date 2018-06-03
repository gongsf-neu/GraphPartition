package partition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Format2MaiterInput {

	/**
	 * reset id of dataset according to the input format of Maiter
	 */

	String input;
	String output;
	int partNum;
	int pointNum;
	boolean isWeight;

	public Format2MaiterInput(String input, String output, int partNum,
			int pointNum) {
		this.input = input;
		this.output = output;
		this.partNum = partNum;
		this.pointNum = pointNum;
	}

	public void formatWithWeight() throws NumberFormatException, IOException {
		int[] idMap = new int[pointNum];

		BufferedReader[] brs = new BufferedReader[partNum];
		for (int i = 0; i < partNum; i++) {
			brs[i] = new BufferedReader(new FileReader(input + i));
			String line = null;
			for (int j = 0; (line = brs[i].readLine()) != null; j++) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				idMap[id] = j * partNum + i;
			}
			brs[i].close();
		}

		BufferedWriter[] bws = new BufferedWriter[partNum];

		for (int i = 0; i < partNum; i++) {
			brs[i] = new BufferedReader(new FileReader(input + i));
			bws[i] = new BufferedWriter(new FileWriter(output + i));
			String line = null;
			for (int j = 0; (line = brs[i].readLine()) != null; j++) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				bws[i].write(idMap[id] + "\t");
				while (st.hasMoreTokens()) {
					String[] ss = st.nextToken().split(",");
					bws[i].write(idMap[Integer.parseInt(ss[0])] + "," + ss[1]
							+ " ");
				}
				bws[i].write("\n");
			}
			brs[i].close();
			bws[i].close();
		}
	}

	public void formatWithNoWeight() throws NumberFormatException, IOException {
		int[] idMap = new int[pointNum];

		BufferedReader[] brs = new BufferedReader[partNum];
		for (int i = 0; i < partNum; i++) {
			brs[i] = new BufferedReader(new FileReader(input + i));
			String line = null;
			for (int j = 0; (line = brs[i].readLine()) != null; j++) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				idMap[id] = j * partNum + i;
			}
			brs[i].close();
		}

		BufferedWriter[] bws = new BufferedWriter[partNum];

		for (int i = 0; i < partNum; i++) {
			brs[i] = new BufferedReader(new FileReader(input + i));
			bws[i] = new BufferedWriter(new FileWriter(output + i));
			String line = null;
			for (int j = 0; (line = brs[i].readLine()) != null; j++) {
				StringTokenizer st = new StringTokenizer(line);
				int id = Integer.parseInt(st.nextToken());
				bws[i].write(idMap[id] + "\t");
				while (st.hasMoreTokens()) {
					bws[i].write(idMap[Integer.parseInt(st.nextToken())] + " ");
				}
				bws[i].write("\n");
			}
			brs[i].close();
			bws[i].close();
		}
	}

	public static void main(String[] args) throws IOException {

		String method = "neighbor";
		String weightmethod = "InDeg";
		//note whether the graph is weight
		boolean isWeight = false;

		String input = "";
		String output = "";
		int partNum = -1;
		int pointNum = -1;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-in")) {
				input = args[++i];
			}

			if (args[i].equals("-out")) {
				output = args[++i];
			}

			if (args[i].equals("-partNum")) {
				partNum = Integer.parseInt(args[++i]);
			}
			
			if(args[i].equals("-pointNum")){
				pointNum = Integer.parseInt(args[++i]);
			}
			
			if (args[i].equals("-w")) {
				String sbool = args[++i];
				if (sbool.equals("true")) {
					isWeight = true;
				} else if (sbool.equals("false")) {
					isWeight = false;
				} else {
					System.out
							.println("parameter w must be true or false, can not be "
									+ sbool);
				}
			}
		}
		
		if(partNum == -1 || pointNum == -1){
			System.out.println("partNum or vertexNum is error");
			System.exit(0);
		}
		
		Format2MaiterInput format = new Format2MaiterInput(input, output,
				partNum, pointNum);
		if (isWeight) {
			format.formatWithWeight();
		} else {
			format.formatWithNoWeight();
		}
		System.out.println("format to Maiter input finish!!");
	}
}
