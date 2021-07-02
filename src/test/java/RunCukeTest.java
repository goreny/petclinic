import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/java/petclinic/com/cucumber/Features",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"}
)

public class RunCukeTest {
}