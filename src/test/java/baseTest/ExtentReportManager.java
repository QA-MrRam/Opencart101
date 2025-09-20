package baseTest;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	// UI of the report
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent; // populate common info in the report
	public ExtentTest test; // Creating test case entries in the report and update status of the test
							// methods

	String repName;

	@Override
	public void onStart(ITestContext context) {

		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyt.MM.dd.HH.mm.ss"); Date date
		 * = new Date(); String currentDateTimeStamp = df.format(date);
		 */
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";

		sparkReporter = new ExtentSparkReporter("./Reports/" + repName);
		sparkReporter.config().setDocumentTitle("Open Cart Automation Report");
		sparkReporter.config().setReportName("Open Cart Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		// Object of Extent Report
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		// add common info in report
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Enviornment", "QA");

		String os = context.getCurrentXmlTest().getParameter("os");
		if (os != null)
			extent.setSystemInfo("Operating System", os);

		String browser = context.getCurrentXmlTest().getParameter("browser");
		if (browser != null)
			extent.setSystemInfo("Browser", browser);
		List<String> includedGruops = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGruops.isEmpty()) {
			extent.setSystemInfo("Groups", includedGruops.toString());
		}

	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName()); // create new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, result.getName() + "Got successfully excuted. "); // update status
	}

	@Override
	public void onTestFailure(ITestResult result) {

		test = extent.createTest(
				result.getTestClass().getRealClass().getSimpleName() + " :: " + result.getMethod().getMethodName());
//		test = extent.createTest(result.getTestClass().getName() +" :: "+ result.getMethod().getMethodName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report

//		test = extent.createTest(result.getName()); // create new entry in the report
		test.log(Status.FAIL, result.getName() + "got failed. "); // update status
		if (result.getThrowable() != null) {
			test.log(Status.INFO, result.getThrowable().getMessage());
		}

		try {

			String imgPath = new BaseClass().captureScreenshot(result.getName());
			test.addScreenCaptureFromPath(imgPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
//		String className = result.getTestClass().getName();
//		String methodName = result.getMethod().getMethodName();
//		String testName = className + ":: " + methodName;
//		test = extent.createTest(testName); // create new entry in the report

		test = extent.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
//		test = extent.createTest(result.getTestClass().getName()); // create new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report

		test.log(Status.SKIP, result.getName() + "got Skipped. ");

		if (result.getThrowable() != null) {
			test.log(Status.INFO, result.getThrowable().getMessage());
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();

		// To open report automatically
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
