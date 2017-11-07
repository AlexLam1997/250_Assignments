package a2;

import java.io.IOException;

import testerClasses.Failedcase;

import java.io.FileReader;
import java.io.BufferedReader;

public class Tester {
	public static void main(String[] args) throws IOException {
		// You need to change your root directory.

		String root = "C:\\\\Users\\\\Toshiba\\\\Downloads\\\\GeneratedTestCases\\\\";

		String[] input = { "sample_in.txt" };
		String[] output = { "sample_value.txt" };
		String[] parsedExp = { "sample_exp.txt" };
		/**
		 * Test each input file in the array of strings 'input' and compare with
		 * expected output in corresponding file in output files listed in array of
		 * strings 'output'
		 **/
		for (int i = 0; i < input.length; i++) {
			String inputFileName = root + input[i];
			String outputFileName = root + output[i];
			String expFileName = root + parsedExp[i];

			FileReader fin = new FileReader(inputFileName);
			FileReader fout = new FileReader(outputFileName);
			FileReader fexp = new FileReader(expFileName);

			BufferedReader inputReader = new BufferedReader(fin);
			BufferedReader outputReader = new BufferedReader(fout);
			BufferedReader expReader = new BufferedReader(fexp);

			Integer passed = 0, failed = 0, expectedValue = 0, computedValue = 0, actualerror = 0;
			Failedcase[] expfailed = new Failedcase[5000];
			String expString;
			String computedParsedExpression, expectedParsedExpression;
			Expression exp;
			/**
			 * Test for each input test case in the input file
			 **/
			int expressionID = 0;
			int total = 0;
			while ((expString = inputReader.readLine()) != null) {
				total++;
				// Read expected output from the output file
				expectedValue = Integer.valueOf(outputReader.readLine());
				expectedParsedExpression = expReader.readLine();

				// Construct Expression from expression string read from
				exp = new Expression(expString);
				
				System.out.print(expressionID + ": ");
				for (String t : exp.getTokenList()) { // print expression without ~
					System.out.print(t);
				}

				computedParsedExpression = exp.toString();
				// compare the parsed expressions
				if (computedParsedExpression.equals(expectedParsedExpression)) {
					// passed++; only want test passed when computed value is correct
				} else {
					System.out.println("Passed " + passed + " tests");
					System.out.println("Failed for input: " + expString);
					System.out.println("Expected : " + expectedParsedExpression);
					System.out.println("Result:   " + computedParsedExpression);
					// System.exit(-1);
				}

				// compute the value of expression using the eval() method


				try {
					computedValue = exp.eval();
					// compare the values
					if (computedValue.equals(expectedValue)) {
						passed++;
						System.out.println("= " + computedValue);
						// System.out.println(exp + " = " + computedValue);
					} else {
						System.out.println("= " + computedValue);
						System.out.println("Passed " + passed + " tests");
						System.out.println("Failed for input #: " + expressionID);
						System.out.println("Expected : " + expectedValue);
						System.out.println("Result:   " + computedValue);
						expfailed[failed] = new Failedcase(expString, expectedValue, computedValue, expressionID);
						failed++;
						// System.exit(-1);
					}
				} catch (NullPointerException e) {
					expfailed[failed] = new Failedcase(expString, expectedValue, 0, expressionID, "Divide by 0");
					failed++;
				}
				expressionID++;
			}

			// test recap
			System.out.println("Passed " + passed + " tests.");
			System.out.println("Failed " + failed + " tests.");
			System.out.println("\nFailed cases:");
			for (int n = 0; n < failed; n++) {
				System.out.println("\nID: " + expfailed[n].getID());
				System.out.println("Failed for input: " + expfailed[n].getExp());
				System.out.println("Expected: " + expfailed[n].getEValue());
				System.out.println("Result: " + expfailed[n].getCValue());
				System.out.println(expfailed[n].getNote());
				if (expfailed[n].getNote().equals("Actual error"))
					actualerror++;
			}
			System.out.println("Passed " + passed + "/" + total + " tests.");
			System.out.println("Failed " + failed + " tests (See above).");
			System.out.println(actualerror + " actual errors");
		}
	}
}
