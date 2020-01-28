package imran.live.order.board.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import imran.live.order.board.domain.OrderSummary;
import imran.live.order.board.domain.OrderType;

import java.util.List;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GetOrderSummaryStep extends BaseStep {

    @Then("^the live order board should provide the following summary for \"([^\"]*)\" orders:$")
    public void theLiveOrderBoardShouldProvideTheFollowingSummaryForOrders(String orderType, List<OrderSummary> orderSummaries) {
        scenarioContext.actualOrderSummaries = liveOrderBoardService.getOrderSummary(OrderType.get(orderType));
        scenarioContext.expectedOrderSummaries = orderSummaries;
        assertThat(scenarioContext.expectedOrderSummaries, is(scenarioContext.actualOrderSummaries));
    }

    @Then("^the live order board should provide blank summary for \"([^\"]*)\" orders$")
    public void theLiveOrderBoardShouldProvideBlankSummaryForOrders(String orderType) {
        List<OrderSummary> orderSummary = liveOrderBoardService.getOrderSummary(OrderType.get(orderType));
        assertThat(orderSummary, is(empty()));
    }

    @And("^sell orders are ordered with lowest price first$")
    public void sellOrdersAreOrderedWithLowestPriceFirst() {
        assertThat(scenarioContext.expectedOrderSummaries, is(scenarioContext.actualOrderSummaries));
    }

    @And("^buy orders are ordered with highest price first$")
    public void buyOrdersAreOrderedWithHighestPriceFirst() {
        assertThat(scenarioContext.expectedOrderSummaries, is(scenarioContext.actualOrderSummaries));
    }
}
