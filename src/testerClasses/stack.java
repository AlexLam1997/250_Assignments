package testerClasses;

// a stack class with push and pop methods associated with it
//last in first out
public class stack {
node top = null; 

// push method adds a node to the stack and pushes the rest of the stack back 
void push (String arg) {
	node pushnode = new node (); 
	pushnode.payload = arg;
	pushnode.next = top; 
	top = pushnode;
	}
//pop method pops the last node added to the stack and "advances" the rest 
String pop() {
		if (top == null) {
			return null;
		} 	else {
				node buffer = top;
				String elem = buffer.payload;
				top = buffer.next;
				return elem;
			}	
	}
}
