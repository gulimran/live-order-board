package imran.live.order.board.acceptance.config;

import imran.live.order.board.acceptance.cucumber.ScenarioContext;
import imran.live.order.board.config.LiveOrderBoardConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.imran.live.order.board.acceptance")
@Import(LiveOrderBoardConfig.class)
public class CucumberConfig {

    @Bean
    @Scope("cucumber-glue")
    public ScenarioContext getScenarioContext() {
        return new ScenarioContext();
    }
}
