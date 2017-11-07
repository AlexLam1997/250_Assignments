package testerClasses;

//class In2pJ converts an infix expression to a postfix expression
import java.util.StringTokenizer;

public class In2pJ {
	queue inq = new queue();
	queue outq = new queue();
	stack stack = new stack();
	queue tq = new queue();

	public void In2p(String[] args) {
		boolean unary = true;
		// unary is boolean variable used to keep tract of unary operators,
		// exact use is described below

		tokenise(args);

		// ffg while loop dequeues tokens from inputq one by one, tests them and acts
		// accordingly until input is empty

		// a + or - operator is binary ifF it follows an operand or a right parentheses
		// and it is unary otherwise.
		// in the ffg while loop, every time a token is dequeued the value of
		// unary is changed. unary will be set to false if the token dequeued is an
		// operator or a right parenthesis and will be set to true otherwise.
		// also initialized to true to take into account the case that if the operator
		// is the first token of the queue it is also unary

		while (inq.front != null) {
			String token = inq.dequeue();
			String unarytoken;
			int opprec = isoperatorprecedance(token);

			if (opprec == 0) { // token is not operator
				outq.enqueue(token); // enqueue token directly to output q
				unary = false;
			} else {
				if ((token.equals("-") || token.equals("+")) && unary) {
					unarytoken = token + inq.dequeue();
					outq.enqueue(unarytoken);
					unary = false;
				} else {
					if (stack.top == null) {// token is operator
						stack.push(token); // if stack empty, push token
						unary = true;
					} else {
						// if its a LEFT parenthesis push onto stack regardless
						// of precedence of what is on stack
						if (opprec == 1 || opprec == 4) {
							stack.push(token);
							unary = true;
						}
						// if its a RIGHT parenthesis
						else if (opprec == 9) {
							unary = false;
							// keep enqueueing result of stack pop until left
							// parenthesis found
							while (isoperatorprecedance(stack.top.payload) != 1) {
								outq.enqueue(stack.pop());
							}
							// when left parenthesis is found, just pop it off
							// stack without enqueue-ing it to the outq
							// (prefix notation doesnt have parenthesis)
							if (isoperatorprecedance(stack.top.payload) == 1) {
								stack.pop();
							}
						} else if (opprec == 8) { // right bracket
							unary = false;
							// keep enqueueing result of stack pop until left
							// parenthesis found
							while (isoperatorprecedance(stack.top.payload) != 4) {
								outq.enqueue(stack.pop());
							}
							// when left parenthesis is found, just pop it off
							// stack without enqueue-ing it to the outq
							// (prefix notation doesnt have parenthesis)
							if (isoperatorprecedance(stack.top.payload) == 4) {
								outq.enqueue((stack.pop()));
							}
						} else { // operator is not parenthesis
							// the token is operator
							unary = true;
							// while prec token <= prec of what is on top of stack,
							// enqueue result of pop stack to outq
							while (stack.top != null && opprec <= isoperatorprecedance(stack.top.payload)
									&& opprec != 9) {
								outq.enqueue(stack.pop());
							}
							// push token on stack once its prec is greater
							stack.push(token);
						}
					}
				}
			}
		}
		// once inputq is empty tokens have been sorted,
		// pop the rest out of stack and enqueue onto outputq
		while (stack.top != null) {
			outq.enqueue(stack.pop());
		}

		// queue t = outq;
		// System.out.print("\n");
		// while (t.front!=null) {
		// System.out.print(t.dequeue());
		// }

	}

	/*
	 * Method isoperatorprecedence checks if token is an operator and if it is,
	 * returns level of precedence of operator but if its not then returns 0
	 */
	static int isoperatorprecedance(String arg) {
		if ((arg).equals("+") || (arg).equals("-"))
			return 2;
		else if ((arg).equals("*") || (arg).equals("/"))
			return 3;
		else if ((arg).equals("("))
			return 1;
		else if ((arg).equals(")"))
			return 9;
		else if ((arg).equals("^"))
			return 6;
		else if (arg.equals("["))
			return 4;
		else if (arg.equals("++") || arg.equals("--"))
			return 5;
		else if (arg.equals("]"))
			return 8;
		else
			return 0;
	}

	public void tokenise(String[] args) {
		String temp = null;
		String temp2 = null;
		String joined = String.join("", args);
		// Create 2 instances of StringTokenizer, one used to enqueue each token
		// and another to print the tokens out, separated by spaces
		StringTokenizer st = new StringTokenizer(joined, "+-*/^()[]", true);
		StringTokenizer pst = new StringTokenizer(joined, "+-*/^()[]", true);
		while (st.hasMoreTokens()) {
			inq.enqueue(st.nextToken());
		}
		while (pst.hasMoreTokens()) {
			System.out.print(pst.nextToken());
		}
		while (inq.front.next != null && inq.front.next.next != null) { // stop when 2 left
			if (temp != null) {
				temp = temp2;
				temp2 = inq.dequeue();
			} else {
				temp = inq.dequeue();
				temp2 = inq.dequeue();
			}
			if ((temp.equals("+") || temp.equals("-")) && temp.equals(temp2)) {
				tq.enqueue(temp + temp2);
				temp = temp2 = null;
			} else {
				tq.enqueue(temp);
			}
		}
		// if (temp!=null) {
		// temp = temp2;
		// temp2=inq.dequeue();
		// if ((temp.equals("+") || temp.equals("-")) && temp.equals(temp2)) {
		// tq.enqueue(temp+temp2);
		// }else {
		// tq.enqueue(temp);
		// tq.enqueue(temp2);
		// }
		// tq.enqueue(inq.dequeue());
		// }else {
		// tq.enqueue(inq.dequeue());
		// tq.enqueue(inq.dequeue());
		// }
		if (temp2 != null)
			tq.enqueue(temp2);
		while (inq.front != null) {
			tq.enqueue(inq.dequeue());
		}

		while (tq.front != null) {
			String print = tq.dequeue();
			// System.out.print(print);
			inq.enqueue(print);
		}
	}
}