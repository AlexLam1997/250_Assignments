package a4;

public class HashLinkedList<K, V> {
	/*
	 * Fields
	 */
	private HashNode<K, V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList() {
		head = null;
		size = 0;
	}

	/*
	 * Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value) {
		// ADD CODE BELOW HERE
		HashNode<K, V> nnode = new HashNode<K, V>(key, value);
		HashNode<K, V> temp = this.head;
		this.head = nnode;
		nnode.next = temp;
		this.size += 1;
		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key returns the node with key
	 */

	public HashNode<K, V> getListNode(K key) {
		// ADD CODE BELOW HERE
		if (listContains(key)) {
			HashNode<K, V> snode = head;
			while ((snode != null && !snode.getKey().equals(key))) {
				snode = snode.next;
			}
			return snode;
		} else {
			return null;
		}

		// ADD CODE ABOVE HERE
	}

	/*
	 * Remove the head node of the list Note: Used by remove method and next method
	 * of hash table Iterator
	 */

	public HashNode<K, V> removeFirst() {
		// ADD CODE BELOW HERE
		HashNode<K, V> temp = head;
		head = head.next;
		size -= 1;
		return temp;
		// ADD CODE ABOVE HERE
	}

	/*
	 * Remove Node by key from linked list
	 */

	public HashNode<K, V> remove(K key) {
		// ADD CODE BELOW HERE
		HashNode<K, V> removed = null;
		HashNode<K, V> helper = new HashNode<K, V>(null, null);
		helper.next = head;
		HashNode<K, V> p = null;
		
		if (this.listContains(key)) {
			p = helper;
			while (p.next != null) {
				if (p.next.getKey().equals(key)) {
					removed = p.next;
					HashNode<K, V> next = p.next;
					p.next = next.next;
				} else {
					p = p.next;
				}
			}
			this.head = helper.next;
		}
		this.size--;
		return removed;

		// ADD CODE ABOVE HERE
	}

	/*
	 * Delete the whole linked list
	 */
	public void clear() {
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty() {
		return size == 0 ? true : false;
	}

	int size() {
		return this.size;
	}

	// ADD YOUR HELPER METHODS BELOW THIS

	boolean listContains(K key) {
		boolean contained = false;
		HashNode<K, V> snode = head;
		
		for(int i=0;i<this.size();i++) {
			if (!snode.getKey().equals(key)) {
				snode = snode.next;
			} else {
				contained = true;
				return contained;
			}			
		}
		
//		int i =0;
//		while (i<this.size()) {
//			if (!snode.getKey().equals(key)) {
//				snode = snode.next;
//				i++;
//			} else {
//				contained = true;
//				break;
//			}
//		}
		
		
		return contained;
	}

	HashLinkedList<K, V> append(HashLinkedList<K, V> b) {
		while (!b.isEmpty()) {
			if (b.head != null) {
				HashNode<K, V> top = b.removeFirst();
				this.add(top.getKey(), top.getValue());
			} else {
				break;
			}
		}
		return this;
	}
	
	public HashLinkedList<K, V> clone (){
		HashLinkedList<K, V> cloned = new HashLinkedList<K,V>();
		HashNode<K, V> front = this.head;
		while(front!=null) {
			cloned.add(front.getKey(), front.getValue());
			front=front.next;
		}
		return cloned;
	}
	// ADD YOUR HELPER METHODS ABOVE THIS

}
