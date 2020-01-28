package imran.live.order.board.integration.dao;


import imran.live.order.board.config.LiveOrderBoardConfig;
import imran.live.order.board.domain.Order;
import imran.live.order.board.domain.OrderType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import imran.live.order.board.dao.DataSource;
import imran.live.order.board.dao.LiveOrderBoardDao;
import imran.live.order.board.domain.builder.BuyOrderBuilder;
import imran.live.order.board.domain.builder.SellOrderBuilder;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        LiveOrderBoardConfig.class
})
public class LiveOrderBoardDaoITest {

    @Inject
    private LiveOrderBoardDao orderBoardDao;

    @Inject
    private DataSource dataSource;

    @Before
    public void setup() {
        dataSource.orderTable().clear();
    }

    @Test
    public void testSave_StoresOrderInDatabase() {
        // given
        Order expectedOrder = BuyOrderBuilder.builder().userId("testUserId").quantity(3.5).price(200).build();

        // when
        Long orderId = orderBoardDao.save(expectedOrder);

        // then
        assertThat(expectedOrder, is(dataSource.orderTable().get(orderId)));
    }

    @Test
    public void testRemove_DeletesOrderFromDatabase() {
        // given
        Order expectedOrder = SellOrderBuilder.builder().userId("testUserId").quantity(3.5).price(200).build();
        Long orderId = orderBoardDao.save(expectedOrder);
        assertThat(expectedOrder, is(dataSource.orderTable().get(orderId)));

        // when
        orderBoardDao.remove(orderId);

        // then
        assertThat(dataSource.orderTable().get(orderId), is(nullValue()));
    }

    @Test
    public void testGetAll_ReturnsAllSellOrders() {
        // given
        Order sellOrder1 = SellOrderBuilder.builder().userId("testUserId1").quantity(3.5).price(200).build();
        Order sellOrder2 = SellOrderBuilder.builder().userId("testUserId2").quantity(4.0).price(210).build();
        Order buyOrder = BuyOrderBuilder.builder().userId("testUserId3").quantity(1.5).price(190).build();
        saveOrders(sellOrder1, sellOrder2, buyOrder);

        // when
        Collection<Order> sellOrders = orderBoardDao.getAll(OrderType.SELL);

        // then
        assertThat(sellOrders, containsInAnyOrder(sellOrder1, sellOrder2));
    }

    @Test
    public void testGetAll_ReturnsAllBuyOrders() {
        // given
        Order sellOrder = SellOrderBuilder.builder().userId("testUserId1").quantity(3.5).price(200).build();
        Order buyOrder1 = BuyOrderBuilder.builder().userId("testUserId2").quantity(4.0).price(210).build();
        Order buyOrder2 = BuyOrderBuilder.builder().userId("testUserId3").quantity(1.5).price(190).build();
        saveOrders(sellOrder, buyOrder1, buyOrder2);

        // when
        Collection<Order> buyOrders = orderBoardDao.getAll(OrderType.BUY);

        // then
        assertThat(buyOrders, containsInAnyOrder(buyOrder1, buyOrder2));
    }

    private void saveOrders(Order... orders) {
        for (Order order : orders) {
            orderBoardDao.save(order);
        }
    }
}
