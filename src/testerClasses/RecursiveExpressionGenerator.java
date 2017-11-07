package testerClasses;

import java.util.Random;

/**
 * Description: Generator of random valid expressions based on the recursive definition given 
 * in the Comp 250 Assignment 2. Feel free to use this class to test your code of parsing and evaluation, 
 * it works fine for me. Divide by zero exception will probably occur during the evaluation, 
 * that's because the expression is random, just try again. Let me know if you find a bug, 
 * my email is xinyao.sun@mail.mcgill.ca . 
 * If this is useful, I'm glad that I can help, but if there is a problem, 
 * notice that I will not be responsible for any kind of consequences. 
 * @author Xinyao(Kevin) Sun 
 * @since 2017 Oct 19
 * @version 2
 *	
 */
public class RecursiveExpressionGenerator {
	

	private String expression;
	private Random r = new Random();
	
	
	private static final String[] VALID_EXPRESSION_MODELS = new String[] {
		// e = expression, b = binary operator, u = unary operator
		"(ebe)","(ue)","e"
	};
	
	public RecursiveExpressionGenerator() {
		
	}
		
	
	/**
	 * @param nbRecursion is how many times you want to apply the recursive definition to generate the expression. 
	 * nbRecursion starts from 0.
	 * @param maxValue is the maximum value of random positive integers in the expression. 
	 * @return the expression
	 */
	public String generateExpression(int nbRecursion, int maxValue) {	
		
		expression = this.newModel();		
		
		for(int i=0;i<nbRecursion;i++) {
			substituteExpression();
		}
		
		substituteOperator();
		
		// case [positiveInteger]
		if(expression.equals("e")) expression = "[" + expression +"]";
		
		substitutePositiveInteger(maxValue);				
		
		return expression;
	}

	private String newModel() {
	
		int index = r.nextInt(VALID_EXPRESSION_MODELS.length);		
		String model =  VALID_EXPRESSION_MODELS[index];
		
		// 1/4 chance to have [ ]
		if(r.nextBoolean() && r.nextBoolean()) {
			model = "[" + model +"]";
		}		
		
		return  model ;
	}
	
	private void substituteExpression() {
		String newExp="";
		
		for(int j=0;j<expression.length();j++) {
			
			char c = expression.charAt(j);
			if((c+"").equals("e")) {
			
				newExp+=newModel();
			}else {
				newExp+=c;
			}
		}
		expression = newExp;
	}
	
	private void substituteOperator() {
		
		String newExp="";
		
		for(int j=0;j<expression.length();j++) {
			
			char c = expression.charAt(j);
			
			if((c+"").equals("u")) {
				
				newExp+= r.nextBoolean()? "--" : "++";
				
			}else if((c+"").equals("b")){
				
				switch(r.nextInt(4)) {
					case 0: newExp+= "+";
							break;
					case 1: newExp+= "-";
							break;
					case 2: newExp+= "*";
							break;
					case 3: newExp+= "/";
							break;
				}
				
			}else {
				newExp+=c;
				
			}
		}
		
		expression = newExp;
	
	}
	
	private void substitutePositiveInteger(int maxValue) {
		
		String newExp="";
		for(int j=0;j<expression.length();j++) {
			
			char c = expression.charAt(j);
			if((c+"").equals("e")) {
			
				newExp+=r.nextInt(maxValue);
			}else {
				newExp+=c;
			}
		}
		
		expression = newExp;
		
	}
	
}
