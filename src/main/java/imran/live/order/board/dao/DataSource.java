package imran.live.order.board.dao;

import imran.live.order.board.domain.Order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSource {

    private static final Map<Long, Order> ORDER_TABLE = new ConcurrentHashMap<>();

    public Map<Long, Order> orderTable() {
        return ORDER_TABLE;
    }

}
