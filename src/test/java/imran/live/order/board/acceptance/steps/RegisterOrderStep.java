package imran.live.order.board.acceptance.steps;

import cucumber.api.java.en.When;
import imran.live.order.board.domain.Order;

public class RegisterOrderStep extends BaseStep {

    @When("^the order is registered in the live order board service$")
    public void theOrderIsRegisteredInTheLiveOrderBoardService() {
        scenarioContext.registeredOrder = registerOrder(scenarioContext.actualOrders.get(0));
    }

    @When("^these order are registered in the live order board service$")
    public void theseOrderAreRegisteredInTheLiveOrderBoardService() {
        scenarioContext.actualOrders.forEach(this::registerOrder);
    }

    private Order registerOrder(Order order) {
        return liveOrderBoardService.registerOrder(order.getUserId(), order.getQuantity(), order.getPrice(), order.getType());
    }
}
