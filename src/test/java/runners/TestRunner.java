package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/features", // Path to your feature files
	    glue = {"stepDefinition", "hooks"},       // Package(s) containing step definitions and hooks
	    plugin = {
	        "pretty",                            // For readable console output
	        "html:target/cucumber-reports.html", // HTML report
	        "json:target/cucumber-reports.json", // JSON report
	        "junit:target/cucumber-reports.xml"  // JUnit XML report
	    },
	    monochrome = true                       // Makes console output more readable
	    //tags = "@tag2"                      // Runs scenarios with this tag
	)
public class TestRunner extends AbstractTestNGCucumberTests {

}
