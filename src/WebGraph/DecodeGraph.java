package WebGraph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.NodeIterator;

public class DecodeGraph {

	public static void main(String[] args) throws IOException {

		String input = "/home/gongsf/program/graphPartition/dataSet/WiKi/enwiki-2013-hc";
		String output = "/home/gongsf/program/graphPartition/dataSet/WiKi/vertex_incomplete.txt";

		ImmutableGraph graph = ImmutableGraph.loadSequential(input);
		NodeIterator nodeIterator = graph.nodeIterator();
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		while (nodeIterator.hasNext()) {
			int src = nodeIterator.next();
			bw.write(src + "\t");
			int[] dest = nodeIterator.successorArray();
			int outdegree = nodeIterator.outdegree();
			for (int i = 0; i < outdegree; i++) {
				bw.write(dest[i] + " ");
			}
			bw.write("\n");
		}
		bw.close();

//		ImmutableGraph immutableGraph = ImmutableGraph.loadSequential(input);
//		NodeIterator nodeIterator2 = immutableGraph.nodeIterator();
//		BufferedWriter writer = new BufferedWriter(new FileWriter(input
//				+ "edge.txt"));
//		while (nodeIterator2.hasNext()) {
//			int src = nodeIterator2.next();
//			writer.write(src + "\t");
//			System.out.println(nodeIterator2.successorArray().length);
//			System.out.println(nodeIterator2.outdegree());
//			for (int dstInd = 0; dstInd < nodeIterator2.outdegree(); dstInd++) {
//				int dst = nodeIterator2.successorArray()[dstInd];
//				System.out.println(dst);
//				writer.write(dst + "\n");
//			}
//		}
//		writer.close();
	}

}
