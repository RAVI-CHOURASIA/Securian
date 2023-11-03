import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	public static ExtentReports extent;
		private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
		
		public static ExtentReports getInstance() {
			if(extent == null) {
				extent = createInstance("extent.html");
			}
			return extent;
		}
		
		private static ExtentReports createInstance(String Facebook) {
			ExtentSparkReporter htmlReporter = new ExtentSparkReporter(Facebook);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			return extent;
		}

		public static ExtentTest createTest(String testName) {
			ExtentTest test = extent.createTest(testName);
			extentTest.set(test);
			return test;
		}
		
		public static ExtentTest getTest() {
			return extentTest.get();
		}
		
		public static void removeTest() {
			extentTest.remove();
		}
	}
