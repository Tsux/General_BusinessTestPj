package org.ektqa.commons;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestNGCustom extends TestListenerAdapter {

	PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
	ManageTestResult testResultManager = new ManageTestResult();

	@Override
	public void onTestFailure(ITestResult tr) {
		testResultManager.ScreenShot(tr, "Failure");
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		//ScreenShot(tr, "Skipped");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		// ScreenShot();
	}

	@Override
	public void onStart(ITestContext context) {
		String suiteNameclassName = context.getSuite().getName() + " > " + context.getName();
		String dashes = prepareDashes(suiteNameclassName.length());
		log("\n\n" + dashes);
		log(suiteNameclassName);
		log(dashes + "\n");
	}

	@Override
	public void onTestStart(ITestResult tr) {
		String testFullName = tr.getTestContext().getSuite().getName() + " > " + testResultManager.getTestClassNameWithoutPackageStucture(tr) + " > " + tr.getMethod().getMethodName();
		String dashes = prepareDashes(testFullName.length());
		log(dashes);
		log(testFullName);
		log(dashes);
	}

	private String prepareDashes(int length) {
		String dashes = "";
		for (int i = 0; i < length; i++) {
			dashes += "-";
		}
		return dashes;
	}

	private void log(String string) {
		stdout.println(string);
	}

}
