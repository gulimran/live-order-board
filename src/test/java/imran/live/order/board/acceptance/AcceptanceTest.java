package imran.live.order.board.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        tags = {"~@ignore"},
        glue = {"imran.live.order.board"})
public class AcceptanceTest { }
