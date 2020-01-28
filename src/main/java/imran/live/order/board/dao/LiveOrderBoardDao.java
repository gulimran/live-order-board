package imran.live.order.board.dao;

import imran.live.order.board.domain.Order;
import imran.live.order.board.domain.OrderType;

import java.util.Collection;

public interface LiveOrderBoardDao {

    Long save(Order order);

    void remove(Long orderId);

    Collection<Order> getAll(OrderType orderType);

}
