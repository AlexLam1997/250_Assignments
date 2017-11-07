package testerClasses;

/**
 * Created by Toshiba on 16/11/2016.
 */
// main method in calc takes mathematical infix notation expression from command
// line then calculates and prints the result into the terminal
public class calc {
	String return_type = "int";

	public calc() {

	}

	public String calculate(String[] args) {

		In2pJ calc = new In2pJ();
		stack result = new stack();

		calc.In2p(args);
		System.out.print("=" + " ");
		// while the outputq isnt null and the token at its front is not an operator
		// dequeue it and push it onto the result stack. if the element
		// is an operator, dequeue it & pop the stack twice to get 2 operands,
		// perform the operation and push the result
		// on to the "result" stack. repeat until output queue is empty
		// when it is empty, print the top of the result stack
		while (calc.outq.front != null) {
			if (calc.outq.front.payload != null) {
				while (In2pJ.isoperatorprecedance(calc.outq.front.payload) == 0) {
					result.push(calc.outq.dequeue());
				}
			}
			switch (calc.outq.front.payload) {
			case ("+"):
				calc.outq.dequeue();
				result.push(tostring(tofloat(result.pop()) + tofloat(result.pop())));
				break;

			case ("++"):
				calc.outq.dequeue();
				result.push(tostring(tofloat(result.pop()) + 1));
				break;
			case ("--"):
				calc.outq.dequeue();
				result.push(tostring(tofloat(result.pop()) - 1));
				break;
			case ("["):
				calc.outq.dequeue();
				int g = tofloat(result.pop());
				if (g < 0) {
					result.push(tostring(g * (-1)));
				} else
					result.push(tostring(g));
				break;
			case ("-"):
				calc.outq.dequeue();
				int a = tofloat(result.pop());
				int b = tofloat(result.pop());
				result.push(tostring(b - a));
				break;
			case ("*"):
				calc.outq.dequeue();
				result.push(tostring(tofloat(result.pop()) * tofloat(result.pop())));
				break;
			case ("/"):
				calc.outq.dequeue();
				float c = tofloat(result.pop());
				float d = tofloat(result.pop());
				int quotient = (int) (d / c);
				result.push(tostring(quotient));
				break;
			case ("^"):
				calc.outq.dequeue();
				int e = tofloat(result.pop());
				int f = tofloat(result.pop());
				result.push(tostring(exponential(f, e)));
				break;
			}
		}
		if (result.top.payload != null) {
			System.out.print(result.top.payload);
			System.out.print("\n");
		}
		return result.top.payload;

	}

	// Converts string to float and returns the float
	static int tofloat(String args) {
		return Integer.parseInt(args);
	}

	// converts float to string & returns the string
	static String tostring(int arg) {
		return String.valueOf(arg);
	}

	// returns result of an exponential of 2 arguments
	static int exponential(int a, int b) {
		int c = (int) Math.pow(a, b);
		return c;
	}
}
