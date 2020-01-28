package imran.live.order.board.acceptance.cucumber;


import imran.live.order.board.domain.Order;
import imran.live.order.board.domain.OrderSummary;

import java.util.List;

public class ScenarioContext {

    public Order registeredOrder;
    public List<? extends Order> actualOrders;
    public List<OrderSummary> actualOrderSummaries;
    public List<OrderSummary> expectedOrderSummaries;

    public void clear() {
        registeredOrder = null;
        actualOrders = null;
        actualOrderSummaries = null;
        expectedOrderSummaries = null;
    }
}
