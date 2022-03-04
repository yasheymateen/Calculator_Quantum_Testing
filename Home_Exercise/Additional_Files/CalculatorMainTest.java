import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CalculatorMainTest {
	final static int NUM_CALCULATORS = 2;
	
	final static int NUM_ITERS = 20;
	
	final static String CALC_NAME = "Crystal ";
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		boolean add = true;
		
		String[] names = new String[NUM_CALCULATORS];
		
		for (int i = 0; i < NUM_CALCULATORS; i++) {
			int calcNum = i+1;
			String calcName = CALC_NAME + calcNum;
			names[i] = calcName;
		}
		
		List<Callable<Test>> actions = new ArrayList<Callable<Test>>();
		
		for (final String name : names) {
			Callable<Test> call = new Callable<Test>() {
				@Override
				public Test call() throws Exception {
					return createTestCalc(name, NUM_ITERS, add);
				}
			};
			
			actions.add(call);
		}
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		try {
			List<Future<Test>> results = exec.invokeAll(actions);
			
			String rateOfSuccesses = "";
			String betterCalc = "";
			double betterCalcRate = -1;
			for (Future<Test> ft : results) {
				Test tester = ft.get();
				System.out.println(tester.getResults());
				if (tester.getrateOfSuccess() > betterCalcRate) {
					betterCalcRate = tester.getrateOfSuccess();
					betterCalc = tester.getName();
				}
				rateOfSuccesses += "" + tester.getName() + " Success rate: " + tester.getrateOfSuccess() + "\n";
				
			}
			System.out.println(rateOfSuccesses + betterCalc + " is better");
			} finally {
				exec.shutdown();
			}
		}
	
	private static Test createTestCalc(String name, int iters_num, boolean add) {
		Test tester = new Test(name);
		tester.testCalc(iters_num, add);
		return tester;
	}
}
