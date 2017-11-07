package a2;

/*

 */

import java.util.Stack;
import java.util.ArrayList;

public class Expression {
	private ArrayList<String> tokenList;

	// Constructor
	/**
	 * The constructor takes in an expression as a string and tokenizes it (breaks
	 * it up into meaningful units) These tokens are then stored in an array list
	 * 'tokenList'.
	 */

	Expression(String expressionString) throws IllegalArgumentException {
		tokenList = new ArrayList<String>();
		StringBuilder token = new StringBuilder();

		for (int i = 0; i < expressionString.length(); i++) {
			String digit = String.valueOf(expressionString.charAt(i));
			if (type(digit) == 3)
				continue;
			token.append(digit);
			if (type(digit) == 1 | type(digit) == 4) { // token is parenthesis or a binary operator
				// do nothing
			} else if ((type(digit) == 2) && (type(String.valueOf(expressionString.charAt(i + 1))) == 2)) {
				token.append(digit);
				i++;
			} else if (type(digit) == 0) {
				while (type(String.valueOf(expressionString.charAt(i + 1))) == 0) {
					token.append(expressionString.charAt(i + 1));
					i++;
				}
			}
			tokenList.add(token.toString());
			token.setLength(0); // clear
		}
	}

	int type(String digit) {
		switch (digit) {
		case "*":
		case "/":
		case "(":
		case "[":
			return 1;
		case "+":
		case "-":
			return 2;
		case " ":
			return 3;
		case "]":
		case ")":
			return 4;

		default:
			return 0;
		}
	}

	/**
	 * This method evaluates the expression and returns the value of the expression
	 * Evaluation is done using 2 stack ADTs, operatorStack to store operators and
	 * valueStack to store values and intermediate results. - You must fill in code
	 * to evaluate an expression using 2 stacks
	 */
	public Integer eval() {
		Stack<String> operatorStack = new Stack<String>();
		Stack<Integer> valueStack = new Stack<Integer>();
		int nullexception = 0;
		// you were just hacked by BATMAN
		for (String s : this.tokenList) {
			if (nullexception == 1)
				break;
			if (type(s) != 4) { // not right parenthesis or bracket
				if (isInteger(s)) {
					valueStack.push(Integer.valueOf(s));
				} else
					operatorStack.push(s);
			} else { // case where it is a right parenthesis/bracket
				while (!operatorStack.isEmpty()) { // do this while the top of the stack isnt a (
					String operator = operatorStack.pop();
					if (operator.equals("(")) {
						break;
					} else if (operator.equals("[")) {
						Integer subresult = math(valueStack.pop(), operator);
						valueStack.push(subresult);
						break;
					} else if (isUnary(operator)) { // operator is unary operator but not an absolute value
						Integer subresult = math(valueStack.pop(), operator);
						valueStack.push(subresult);
					} else {
						try {
							Integer subresult = math(valueStack.pop(), valueStack.pop(), operator);
							valueStack.push(subresult);
						} catch (NullPointerException e) {
							nullexception = 1;
							break;
						}

					}
				}
			}
		}
		if (nullexception != 1) {
			return valueStack.pop();
		} else {
			return null;
		}
	}

	public Integer math(Integer operand, String operator) { // unary math
		switch (operator) {
		case "++":
			return new Integer(operand.intValue() + 1);
		case "--":
			return new Integer(operand.intValue() - 1);
		case "[":
			if (operand.intValue() < 0) {
				return new Integer(-operand.intValue());
			} else
				return new Integer(operand.intValue());
		default:
			return null;
		}
	}

	public Integer math(Integer o1, Integer o2, String operator) {
		switch (operator) {
		case "*":
			return new Integer(o2 * o1);
		case "/":
			try {
				return new Integer(o2 / o1);

			} catch (ArithmeticException | NullPointerException e) {
				System.out.print("= Error: Cannot divide by 0");
				throw new NullPointerException();
			}
		case "+":
			return new Integer(o2 + o1);
		case "-":
			return new Integer(o2 - o1);
		default:
			return null;
		}
	}

	public boolean isUnary(String operator) {
		if (operator.equals("++") | operator.equals("--") | operator.equals("[")) {
			return true;
		} else
			return false;
	}

	// Helper methods
	/**
	 * Helper method to test if a string is an integer Returns true for strings of
	 * integers like "456" and false for string of non-integers like "+" - DO NOT
	 * EDIT THIS METHOD
	 */
	private boolean isInteger(String element) {
		try {
			Integer.valueOf(element);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Method to help print out the expression stored as a list in tokenList. - DO
	 * NOT EDIT THIS METHOD
	 */

	@Override
	public String toString() {
		String s = new String();
		for (String t : tokenList)
			s = s + "~" + t;
		return s;
	}

	public ArrayList<String> getTokenList() {
		return this.tokenList;
	}

}
