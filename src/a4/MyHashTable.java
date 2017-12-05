package a4;

import java.util.ArrayList;
import java.util.Iterator;

class MyHashTable<K, V> {
	/*
	 * Number of entries in the HashTable.
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR = 0.75;

	/**
	 * Buckets to store lists of key-value pairs. Traditionally an array is used for
	 * the buckets and a linked list is used for the entries within each bucket. We
	 * use an Arraylist rather than an array, since the former is simpler to use in
	 * Java.
	 */

	ArrayList<HashLinkedList<K, V>> buckets;

	/*
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {
		// ADD YOUR CODE BELOW HERE
		this.buckets = new ArrayList<HashLinkedList<K, V>>(numBuckets);
		this.numBuckets = numBuckets;
		for (int i = 0; i < numBuckets; i++) {
			buckets.add(new HashLinkedList<K, V>());
		}
		// ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Given a key, return the bucket position for the key.
	 */
	private int hashFunction(K key) {
		return Math.abs(key.hashCode()) % numBuckets;
	}

	/**
	 * Checking if the hash table is empty.
	 */

	public boolean isEmpty() {
		if (entryCount == 0)
			return true;
		else
			return (false);
	}

	/**
	 * return the number of entries in the hash table.
	 */

	public int size() {
		return (this.entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting.
	 * 
	 * If there was a previous value for the given key in this hashtable, then
	 * overwrite it with new value and return the old value. Otherwise return null.
	 */

	public V put(K key, V value) {
		// ADD YOUR CODE BELOW HERE
		int hashvalue = hashFunction(key);
		if (this.containsKey(key)) { // previous value already associated w/key
			return buckets.get(hashvalue).getListNode(key).resetValue(value);
		} else { // new key
			if (buckets.get(hashvalue) == null) { // no previous entry with this hashvalue, create new bucket
				HashLinkedList<K, V> newEntry = new HashLinkedList<K, V>();
				newEntry.add(key, value);
				buckets.add(hashvalue, newEntry);
				entryCount++;

			} else { // hashvalue already exists, add to the linkedlist
				buckets.get(hashvalue).add(key, value); // can use buckets.set instead
				entryCount++;
			}

			if (entryCount > this.MAX_LOAD_FACTOR * this.numBuckets) {
				this.rehash();
			}
			return null; // if not overwriting anything
		}
		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Retrieves a value associated with some given key in the hash table. Returns
	 * null if the key could not be found in the hash table)
	 */
	public V get(K key) {
		// ADD YOUR CODE BELOW HERE
		if (this.containsKey(key)) {
			return buckets.get(hashFunction(key)).getListNode(key).getValue();
		} else {
			return null;
		}

		// for (int i = 0; i < buckets.size(); i++) {
		// HashNode<K, V> current = buckets.get(i).getListNode(key);
		// if (current != null) {
		// return current.getValue();
		// }
		// }
		// return null;
		// ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Removes a key-value pair from the hash table. Return value associated with
	 * the provided key. If the key is not found, return null.
	 */
	public V remove(K key) {
		// ADD YOUR CODE BELOW HERE
		V value = null;
		if (this.containsKey(key)) {
			for (HashLinkedList<K, V> linkedList : buckets) {
				if (linkedList.listContains(key)) {
					value = linkedList.remove(key).getValue();
				}
			}
		}
		return value;
		// return buckets.get(hashFunction(key)).remove(key).getValue();
		// ADD YOUR CODE ABOVE HERE
	}

	/*
	 * This method is used for testing rehash(). Normally one would not provide such
	 * a method.
	 */

	public int getNumBuckets() {
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table.
	 */

	public MyHashTable<K, V>.HashIterator iterator() {
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, but keeps the number of buckets
	 * intact.
	 */
	public void clear() {
		for (int ct = 0; ct < buckets.size(); ct++) {
			buckets.get(ct).clear();
		}
		entryCount = 0;
	}

	/**
	 * Create a new hash table that has twice the number of buckets. Actually, just
	 * increases the capacity of the hashtable's arraylist
	 */

	public void rehash() {
		// ADD YOUR CODE BELOW HERE
		// new table with around twice the capacity
		MyHashTable<K, V> newTable = new MyHashTable<K, V>(buckets.size() * 2);
		newTable.entryCount = this.entryCount;
		// transfer all the key value pairs of the old hashtable into the new one
		for (HashLinkedList<K, V> linkedList : this.buckets) {
			for (int i = 0; i < linkedList.size(); i++) {
				HashNode<K, V> oldNode = linkedList.removeFirst();
				newTable.put(oldNode.getKey(), oldNode.getValue());
				// transfer all old entries to the new arraylist
				// take every node and add its key.value pairs to the new table using put
				// which uses the updated numbuckets value to appropriatly store in buckets
			}
		}
		// update old hashtable values
		this.buckets = newTable.buckets;
		this.numBuckets = newTable.numBuckets;

		// ADD YOUR CODE ABOVE HERE

	}

	/*
	 * Checks if the hash table contains the given key. Return true if the hash
	 * table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key) {
		int hashValue = hashFunction(key);
		boolean contains = false;
		for (HashLinkedList<K, V> linkedList : buckets) {
			if (linkedList.listContains(key)) {
				contains = true;
			}
		}
		return contains;
		// if (buckets.get(hashValue).getListNode(key) == null) {
		// return false;
		// }
		// return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K> keys() {
		ArrayList<K> listKeys = new ArrayList<K>();
		// ADD YOUR CODE BELOW HERE
		HashIterator iterator = this.iterator();
		while (iterator.hasNext()) {
			listKeys.add(iterator.next().getKey());
		}
		return listKeys;
		// ADD YOUR CODE ABOVE HERE

	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList<V> values() {
		ArrayList<V> listValues = new ArrayList<V>();

		// ADD YOUR CODE BELOW HERE
		// ADD YOUR CODE BELOW HERE
		HashIterator iterator = this.iterator();
		while (iterator.hasNext()) {
			listValues.add(iterator.next().getValue());
		}
		return listValues;
		// ADD YOUR CODE ABOVE HERE
	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 * Inner class: Iterator for the Hash Table.
	 */
	public class HashIterator implements Iterator<HashNode<K, V>> {
		HashLinkedList<K, V> allEntries = new HashLinkedList<K, V>();

		/**
		 * Constructor: make a linkedlist (HashLinkedList) 'allEntries' of all the
		 * entries in the hash table
		 */
		public HashIterator() {
			// ADD YOUR CODE BELOW HERE
			for (int i = 0; i < numBuckets; i++) {
				HashLinkedList<K, V> current = buckets.get(i).clone();
				if (current.size() != 0) {
					HashNode<K, V> topNode = current.removeFirst();
					allEntries.add(topNode.getKey(), topNode.getValue());
				}
			}

			// ADD YOUR CODE ABOVE HERE
		}

		// Override
		@Override
		public boolean hasNext() {
			return !allEntries.isEmpty();
		}

		// Override
		@Override
		public HashNode<K, V> next() {
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented, but must be declared because it is in the Iterator interface

		}
	}

}
