package imran.live.order.board.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import imran.live.order.board.domain.BuyOrder;
import imran.live.order.board.domain.SellOrder;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class LiveOrderBoardServiceStep extends BaseStep {

    @Given("^the live order board service is available$")
    public void theLiveOrderBoardServiceIsAvailable() {
        assertNotNull(liveOrderBoardService);
    }

    @And("^there are no orders on the live order board$")
    public void thereAreNoOrdersOnTheLiveOrderBoard() {
        dataSource.orderTable().clear();
        scenarioContext.clear();
    }

    @Given("^a sell order with:$")
    public void aSellOrderWith(List<SellOrder> orders) {
        scenarioContext.actualOrders = orders;
    }

    @Given("^a buy order with:$")
    public void aBuyOrderWith(List<BuyOrder> orders) {
        scenarioContext.actualOrders = orders;
    }

    @Given("^multiple sell orders with:$")
    public void multipleSellOrdersWith(List<SellOrder> orders) {
        scenarioContext.actualOrders = orders;
    }

    @Given("^multiple buy orders with:$")
    public void multipleBuyOrdersWith(List<BuyOrder> orders) {
        scenarioContext.actualOrders = orders;
    }
}
