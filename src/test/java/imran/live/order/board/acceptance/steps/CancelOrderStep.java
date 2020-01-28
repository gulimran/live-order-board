package imran.live.order.board.acceptance.steps;

import cucumber.api.java.en.And;

public class CancelOrderStep extends BaseStep {

    @And("^when I cancel this order$")
    public void whenICancelThisOrder() {
        liveOrderBoardService.cancelOrder(scenarioContext.registeredOrder.getId());
    }
}
