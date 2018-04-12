package WebGraph;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TIntHashSet;
import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.NodeIterator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


public class LoadGraph {
	public static void main(String[] args) throws IOException {
		directed("/home/gongsf/Desktop/PageRank/WiKi/enwiki-2013-hc");

	}

	static void directed(String graphPath) throws IOException {
		ImmutableGraph immutableGraph = ImmutableGraph
				.loadSequential(graphPath);
		NodeIterator nodeIterator = immutableGraph.nodeIterator();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				graphPath + "/edge.txt"))) {
			while (nodeIterator.hasNext()) {
				int src = nodeIterator.next();
				for (int dstInd = 0; dstInd < nodeIterator.outdegree(); dstInd++) {
					int dst = nodeIterator.successorArray()[dstInd];
					if (dst < 0 || dst >= immutableGraph.numNodes())
						throw new RuntimeException("error dataset");
					writer.write(src + "\t" + dst + "\n");
				}
			}
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				graphPath + "/node.txt"))) {
			for (int i = 0; i < immutableGraph.numNodes(); i++)
				writer.write(i + "\n");
		}
	}

	static void undirected(String graphPath) throws IOException {
        ImmutableGraph immutableGraph = ImmutableGraph.loadSequential(graphPath);
        NodeIterator nodeIterator = immutableGraph.nodeIterator();
        TIntObjectHashMap<TIntHashSet> srcDstListMap = new TIntObjectHashMap<>();
        while (nodeIterator.hasNext()) {
            int src = nodeIterator.next();
            TIntHashSet dstList = srcDstListMap.get(src);
            if (dstList == null) {
                dstList = new TIntHashSet();
                srcDstListMap.put(src, dstList);
            }

            for (int dstInd = 0; dstInd < nodeIterator.outdegree(); dstInd++) {
                int dst = nodeIterator.successorArray()[dstInd];
                if (dst < 0 || dst >= immutableGraph.numNodes())
                    throw new RuntimeException("error dataset");
                dstList.add(dst);

                TIntHashSet srcList = srcDstListMap.get(dst);
                if (srcList == null) {
                    srcList = new TIntHashSet();
                    srcDstListMap.put(dst, srcList);
                }
                srcList.add(src);

            }

        }
        try (BufferedWriter edgeWriter = new BufferedWriter(new FileWriter(graphPath + "/edge_undirected.txt"))) {
            try (BufferedWriter nodeWriter = new BufferedWriter(new FileWriter(graphPath + "/node.txt"))) {
                for (int src = 0; src < immutableGraph.numNodes(); src++) {
                    TIntHashSet dstList = srcDstListMap.get(src);
                    int finalSrc = src;
                    TIntIterator itr = dstList.iterator();
                    while(itr.hasNext()){
                    	edgeWriter.write(finalSrc + "\t" + itr.next() + "\n");
                    }
//                    dstList.forEach(dst -> {
//                        try {
//                            edgeWriter.write(finalSrc + "\t" + dst + "\n");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return true;
//                    });
                    nodeWriter.write(src + "\n");
                }
            }
        }
    }
}
