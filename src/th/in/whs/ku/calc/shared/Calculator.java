package th.in.whs.ku.calc.shared;

public class Calculator {
	public static final String[] supported = new String[]{"Plus", "Minus", "Multiply", "Divide", "Modulo"};
	
	public static int plus(int a, int b){
		return a+b;
	}
	public static int minus(int a, int b){
		return a-b;
	}
	public static int multiply(int a, int b){
		return a*b;
	}
	public static int divide(int a, int b){
		return a/b;
	}
	public static int modulo(int a, int b){
		return a%b;
	}
	
	public static int computeByString(String operand, int a, int b){
		switch(operand.toLowerCase()){
		case "plus":
			return plus(a, b);
		case "minus":
			return minus(a, b);
		case "multiply":
			return multiply(a, b);
		case "divide":
			return divide(a, b);
		case "modulo":
			return modulo(a, b);
		default:
			throw new RuntimeException("No such operand");
		}
	}
}
