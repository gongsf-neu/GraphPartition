package partitionNeighbor;

import java.util.HashSet;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntObjectHashMap;
import util.WeightVertex;

public class Candidates {

	private TIntObjectHashMap<Node> candidates;
	public int size;
	private Node head;
	private Node tail;

	public Candidates() {
		candidates = new TIntObjectHashMap<Node>();
	}

	public boolean contains(int id) {
		return candidates.contains(id);
	}

	public int getFirst() {

		return head.id;
	}

	public void insert(int coreId) {
		Node node = new Node(coreId, 0);
		candidates.put(coreId, node);
		head = node;
		tail = node;
		size++;
	}

	public int popAndInsert(WeightVertex[] vertices) {
		Node node = head;
		candidates.remove(head.id);
		if (head.next != null) {
			head = head.next;
			head.previous = null;
			node.next = null;
		} else {
			head = null;
			tail = null;
		}
		size--;
		int id = node.id;
		WeightVertex v = vertices[id];
		TIntDoubleIterator vitr = v.neighbor.iterator();
		while (vitr.hasNext()) {
			vitr.advance();
			id = vitr.key();
			// have not been sent partition
			if (!vertices[id].isPart) {

				if (candidates.containsKey(id)) {
					Node tmpNode = candidates.get(id);
					tmpNode.value += vitr.value();
					if (tmpNode == tail) {
						tail = tmpNode.previous;
					}
					adjust(tmpNode);
					if (tmpNode.next == null) {
						tail = tmpNode;
					}
				} else {
					Node tmpNode = new Node(id, vitr.value());

					if (head == null) {
						head = tmpNode;
						tail = tmpNode;
					} else {
						tail.next = tmpNode;
						tmpNode.previous = tail;
						adjust(tmpNode);
						if (tmpNode.next == null) {
							tail = tmpNode;
						}
					}
					candidates.put(id, tmpNode);
					size++;
				}
			} else {
				vitr.remove();
			}
		}
		return node.id;
	}

	private void adjust(Node node) {

		if (node.previous == null) {
			return;
		}

		Node tmpNode = node.previous;
		// System.out.println(node.tmpid + " " + node.value);

		// cut the node from linked list
		node.previous.next = node.next;
		node.previous = null;
		if (node.next != null) {
			node.next.previous = node.previous;
			node.next = null;
		}

		// find the appropriate position
		while (tmpNode != null && node.value > tmpNode.value) {
			tmpNode = tmpNode.previous;
			if (tmpNode != null && tmpNode == tmpNode.previous) {
				System.out.println("there is dead loop");
				break;
			}
		}

		// insert node to linked list head
		if (tmpNode == null) {
			node.next = head;
			head.previous = node;
			head = node;
		} else {
			node.next = tmpNode.next;
			node.previous = tmpNode;
			tmpNode.next = node;
			if (node.next != null) {
				node.next.previous = node;
			}
		}
	}

	public void clear() {
		head = null;
		candidates.clear();
	}

	private class Node {
		public Node next;
		public Node previous;
		public int id;
		public double value;

		public Node(int id, double value) {
			this.id = id;
			this.value = value;
		}
	}

	public void checkLoop() {
		
		if(size != candidates.size()){
			System.out.println("the size is not equal");
			System.out.println("linked list size : " + size);
			System.out.println("candidates size : " + candidates.size());
			System.exit(0);
		}else{
			Node tmp = head;
			while(tmp != null){
				if(!candidates.contains(tmp.id)){
					System.out.println("the linked list is not corresponding to candidates");
					System.exit(0);
				}
				tmp = tmp.next;
			}
		}
		
		if(head.previous != null){
			System.out.println("the head.previous != null, error");
			System.exit(0);
		}
		
		if(tail.next != null){
			System.out.println("the tail.next != null");
			System.exit(0);
		}

		HashSet<Node> set = new HashSet<Node>();
		Node tmp = head;
		while (tmp != null) {
			if (!set.contains(tmp)) {
				set.add(tmp);
			} else {
				System.out.println("there is loop in linked list next");
				System.exit(0);
			}
			tmp = tmp.next;
		}

		set.clear();
		tmp = tail;
		while (tmp != null) {
			if (!set.contains(tmp)) {
				set.add(tmp);
			} else {
				System.out.println("there is loop in linked list previous");
				System.exit(0);
			}
			tmp = tmp.previous;
		}
	}
}
