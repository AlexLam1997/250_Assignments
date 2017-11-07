package testerClasses;

//a queue class with enqueue and dequeue methods associated with it
//implemented as a linked list
//first in first out -> keep track of front and rear 
//add to rear, remove from front
public class queue {
	node front = null;
	node rear = null;

	// enqueue method adds a node to the END of the linked list
	// if list is empty, ass node and associates the front with it
	// the new node is added to the rear, so it becomes the new rear
	void enqueue(String arg) {
		node newnode = new node();
		newnode.payload = arg;
		newnode.next = null;
		if (rear != null)
			rear.next = newnode;
		else
			front = newnode;
		rear = newnode;
	}

	// returns the payload of the node at the front of the linked list and the next
	// node becomes the front
	// if the list (queue) is empty, returns null
	String dequeue() {
		if (front == null)
			return null;
		else {
			node buffer = front;
			String elem = buffer.payload;
			front = buffer.next;
			if (buffer == rear)
				rear = null;
			return elem;
		}

	}

}
