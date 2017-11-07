package testerClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class CreateTestFiles {

	public static void main(String[] args) {
		RecursiveExpressionGenerator generator = new RecursiveExpressionGenerator();
		Random r = new Random();
		calc calculate = new calc();
		ArrayList<String> tokens = new ArrayList<String>();

		String testFolder = "C:\\\\Users\\\\Toshiba\\\\Downloads\\\\GeneratedTestCases";
		String path_expected = testFolder + "\\\\sample_exp.txt";
		String path_input = testFolder + "\\\\sample_in.txt";
		String path_value = testFolder + "\\\\sample_value.txt";

		File in = new File(path_input);
		File exp = new File(path_expected);
		File value = new File(path_value);

		in.getParentFile().mkdirs();
		exp.getParentFile().mkdirs();
		value.getParentFile().mkdirs();

		Writer writein = new Writer(path_input, true);
		Writer writeexpected = new Writer(path_expected, true);
		Writer writevalue = new Writer(path_value, true);

		generate: for (int i = 0; i < 5000; i++) {
			int result;
			String sresult;
			String expression = generator.generateExpression(r.nextInt(10) + 1, 1000);
			while (expression.contains("/0") || expression.contains("*0") || expression.contains("0*")
					|| expression.contains("[0]") || expression.contains("(0)") || expression.contains("0/")
					|| expression.contains("--1")) {
				expression = generator.generateExpression(r.nextInt(5) + 1, r.nextInt(10) + 1);
			}
			String[] expressions = { expression };
			makeTokens(expressions, tokens);

			try {
				sresult = calculate.calculate(expressions);
				result = Integer.parseInt(sresult);
			} catch (NumberFormatException e) {
				i = i - 1;
				continue generate;
			}

			if (result < (2 ^ 20) - 1 && result > -(2 ^ 20)) {
				writevalue.writeToFile(sresult);
				writein.writeToFile(expression);
				writeexpected.writeToFile(toString(tokens));
			} else {
				i = i - 1;
			}

			tokens.clear();

		}

	}

	public static String toString(ArrayList<String> tokenList) {
		String s = new String();
		for (String t : tokenList)
			s = s + "~" + t;
		return s;
	}

	public static void makeTokens(String[] args, ArrayList<String> tokens) {
		queue inq = new queue();
		queue tq = new queue();

		String temp = null;
		String temp2 = null;
		String joined = String.join("", args);
		// Create 2 instances of StringTokenizer, one used to enqueue each token
		// and another to print the tokens out, separated by spaces
		StringTokenizer st = new StringTokenizer(joined, "+-*/^()[]", true);
		while (st.hasMoreTokens()) {
			inq.enqueue(st.nextToken());
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
			tokens.add(tq.dequeue());
		}
	}

}
