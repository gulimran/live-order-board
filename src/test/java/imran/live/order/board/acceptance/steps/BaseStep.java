package imran.live.order.board.acceptance.steps;

import imran.live.order.board.acceptance.config.CucumberConfig;
import imran.live.order.board.acceptance.cucumber.ScenarioContext;
import imran.live.order.board.api.LiveOrderBoardService;
import imran.live.order.board.dao.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;

@ContextConfiguration(classes = {
        CucumberConfig.class
})
@Slf4j
public class BaseStep {

    @Inject
    protected LiveOrderBoardService liveOrderBoardService;

    @Inject
    protected DataSource dataSource;

    @Inject
    protected ScenarioContext scenarioContext;

}
