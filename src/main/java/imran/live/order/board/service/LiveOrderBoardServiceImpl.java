package imran.live.order.board.service;

import imran.live.order.board.api.LiveOrderBoardService;
import imran.live.order.board.dao.LiveOrderBoardDao;
import imran.live.order.board.domain.Order;
import imran.live.order.board.domain.OrderSummary;
import imran.live.order.board.domain.OrderType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static imran.live.order.board.util.OrdersUtil.create;
import static imran.live.order.board.util.OrdersUtil.merge;
import static imran.live.order.board.util.OrdersUtil.sort;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;

@Slf4j
public class LiveOrderBoardServiceImpl implements LiveOrderBoardService {

    private final LiveOrderBoardDao orderBoardDao;

    public LiveOrderBoardServiceImpl(LiveOrderBoardDao orderBoardDao) {
        this.orderBoardDao = orderBoardDao;
    }

    @Override
    public Order registerOrder(String userId, Double orderQuantity, Integer pricePerKg, OrderType orderType) {
        notNull(orderType, "Order type cannot be null");
        log.info("Registering order of orderQuantity: {} and type: {} for userId: {} for pricePerKg: {}", orderQuantity, orderType, userId, pricePerKg);

        Order order = create(userId, orderQuantity, pricePerKg, orderType);
        Long orderId = orderBoardDao.save(order);
        order.setId(orderId);

        return order;
    }

    @Override
    public void cancelOrder(Long orderId) {
        notNull(orderId, "Order id cannot be null");
        log.info("Cancelling order orderId: {}", orderId);

        orderBoardDao.remove(orderId);
    }

    @Override
    public List<OrderSummary> getOrderSummary(OrderType orderType) {
        notNull(orderType, "Order type cannot be null");
        log.info("Getting order summary orderType: {}", orderType);

        Collection<Order> orders = orderBoardDao.getAll(orderType);

        if (orders.isEmpty()) {
            return emptyList();
        }

        Collection<Order> mergedOrders = sort(merge(new ArrayList<>(orders)), orderType);

        return mergedOrders.stream()
                .map(order -> OrderSummary.builder().quantity(order.getQuantity()).price(order.getPrice()).build())
                .collect(toList());
    }
}
