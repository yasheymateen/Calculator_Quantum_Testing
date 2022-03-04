/**
 * @author Yashar Mateen
 *
 */
public class Test {
	private Calculator calculator;
	private String results;
	private int iters, successes, min = 0, max = 10;
	private double rateOfSuccess;
	
	public Test(String name) {
		calculator = new Calculator(name);
		results = "Calculator " + name + ":\n";
		iters = 0;
		successes = 0;
		rateOfSuccess = 0;
	}
	
	public String getResults() {
		return results;
	}
	
	public String getName() {
		return calculator.getName();
	}
	
	public double getrateOfSuccess() {
		return rateOfSuccess;
	}
	
	public void testCalc(int iters, boolean add) {
		for (int i = 0; i < iters; i++) {
			double rand1 = min + (max - min) * Math.random();
			double rand2 = min + (max - min) * Math.random();
			double output;
			if (add) {
				output = calculator.add(rand1, rand2);
			} else {
				output = calculator.subtract(rand1, rand2);
			}
			this.iters++;
			boolean ans = checkAns(rand1, rand2, output, add);
			if (ans) {
				successes++;
			}
			rateOfSuccess = (double) successes/this.iters;
			String operator = (add) ? " + " : " - ";
			String corr = (ans) ? "(correct)":"(error)";
			results += String.format("%-70s", "" + rand1 + operator + rand2 + " = " + output) + corr  + "\n";	
		}
	}
	
	private boolean checkAns(double num1, double num2, double result, boolean add) {
		if (add) {
			return (num1 + num2 == result);
		} else {
			return (num1 - num2 == result);
		}
	}	
}