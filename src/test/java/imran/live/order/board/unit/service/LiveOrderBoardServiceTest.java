package imran.live.order.board.unit.service;

import imran.live.order.board.api.LiveOrderBoardService;
import imran.live.order.board.dao.LiveOrderBoardDao;
import imran.live.order.board.domain.Order;
import imran.live.order.board.domain.OrderSummary;
import imran.live.order.board.domain.OrderType;
import imran.live.order.board.domain.builder.BuyOrderBuilder;
import imran.live.order.board.domain.builder.SellOrderBuilder;
import imran.live.order.board.service.LiveOrderBoardServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LiveOrderBoardServiceTest {

    private static final String USER_ID = "testUserId";
    private static final Double QUANTITY = 1.1;
    private static final Integer PRICE = 100;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private LiveOrderBoardDao orderBoardDao;

    private LiveOrderBoardService orderBoardService;

    @Before
    public void setup() {
        orderBoardService = new LiveOrderBoardServiceImpl(orderBoardDao);
    }

    @Test
    public void testRegisterOrder_ReturnsOrderId() {
        // given
        Long expectedOrderId = 1L;
        Order order = SellOrderBuilder.builder().userId(USER_ID).quantity(QUANTITY).price(PRICE).build();
        given(orderBoardDao.save(order)).willReturn(expectedOrderId);

        // when
        Order actualOrder = orderBoardService.registerOrder(USER_ID, QUANTITY, PRICE, OrderType.SELL);

        // then
        assertThat(expectedOrderId, is(actualOrder.getId()));
        verify(orderBoardDao).save(actualOrder);
    }

    @Test
    public void testRegisterOrder_WithNullType_ThrowsException() {
        // given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Order type cannot be null");

        // when
        orderBoardService.registerOrder(USER_ID, QUANTITY, PRICE, null);
    }

    @Test
    public void testCancelOrder_VerifyThatDaoRemovedIsCalled() {
        // given
        Long orderId = 1L;

        // when
        orderBoardService.cancelOrder(orderId);

        // then
        verify(orderBoardDao).remove(orderId);
    }

    @Test
    public void testCancelOrder_WithNullOrderId_ThrowsException() {
        // given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Order id cannot be null");

        // when
        orderBoardService.cancelOrder(null);
    }

    @Test
    public void testGetOrderSummary_WhenThereAreNoOrders_ReturnsEmptyList() {
        // given
        given(orderBoardDao.getAll(OrderType.BUY)).willReturn(emptyList());

        // when
        List<OrderSummary> orderSummaries = orderBoardService.getOrderSummary(OrderType.BUY);

        // then
        assertThat(orderSummaries, is(empty()));
        verify(orderBoardDao).getAll(OrderType.BUY);
    }

    @Test
    public void testGetOrderSummary_WithNullType_ThrowsException() {
        // given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Order type cannot be null");

        // when
        orderBoardService.getOrderSummary(null);
    }

    @Test
    public void testGetOrderSummary_WhenThereIsOneOrder_ReturnsSummaryOfThatOrder() {
        // given
        Order order = SellOrderBuilder.builder().userId("testUserId1").quantity(5.0).price(175).build();
        OrderSummary expectedOrderSummary = OrderSummary.builder().quantity(5.0).price(175).build();
        given(orderBoardDao.getAll(OrderType.SELL)).willReturn(singletonList(order));

        // when
        List<OrderSummary> orderSummaries = orderBoardService.getOrderSummary(OrderType.SELL);

        // then
        assertThat(orderSummaries, contains(expectedOrderSummary));
        verify(orderBoardDao).getAll(OrderType.SELL);
    }

    @Test
    public void testGetOrderSummary_WhenThereAreMultipleOrdersWithSamePrice_ReturnsSummaryOfMergedOrders() {
        // given
        Order order1 = BuyOrderBuilder.builder().userId("testUserId1").quantity(5.0).price(175).build();
        Order order2 = BuyOrderBuilder.builder().userId("testUserId2").quantity(3.5).price(175).build();
        OrderSummary expectedOrderSummary = OrderSummary.builder().quantity(8.5).price(175).build();
        given(orderBoardDao.getAll(OrderType.BUY)).willReturn(asList(order1, order2));

        // when
        List<OrderSummary> orderSummaries = orderBoardService.getOrderSummary(OrderType.BUY);

        // then
        assertThat(orderSummaries, contains(expectedOrderSummary));
        verify(orderBoardDao).getAll(OrderType.BUY);
    }

    @Test
    public void testGetOrderSummary_WhenThereAreMultipleOrdersWithDifferentPrices_ReturnsSummaryOfEachOrder() {
        // given
        Order order1 = SellOrderBuilder.builder().userId("testUserId1").quantity(5.0).price(175).build();
        Order order2 = SellOrderBuilder.builder().userId("testUserId2").quantity(3.5).price(180).build();
        OrderSummary expectedOrderSummary1 = OrderSummary.builder().quantity(5.0).price(175).build();
        OrderSummary expectedOrderSummary2 = OrderSummary.builder().quantity(3.5).price(180).build();
        given(orderBoardDao.getAll(OrderType.SELL)).willReturn(asList(order1, order2));

        // when
        List<OrderSummary> orderSummariesSell = orderBoardService.getOrderSummary(OrderType.SELL);

        // then
        assertThat(orderSummariesSell, containsInAnyOrder(expectedOrderSummary1, expectedOrderSummary2));
    }

    @Test
    public void testGetOrderSummary_WhenThereAreMultipleOrdersWithDifferentTypes_ReturnsSummaryOfOrdersPerType() {
        // given
        Order order1 = SellOrderBuilder.builder().userId("testUserId1").quantity(5.0).price(175).build();
        Order order2 = BuyOrderBuilder.builder().userId("testUserId2").quantity(3.5).price(175).build();
        OrderSummary expectedOrderSummary1 = OrderSummary.builder().quantity(5.0).price(175).build();
        OrderSummary expectedOrderSummary2 = OrderSummary.builder().quantity(3.5).price(175).build();
        given(orderBoardDao.getAll(OrderType.SELL)).willReturn(singletonList(order1));
        given(orderBoardDao.getAll(OrderType.BUY)).willReturn(singletonList(order2));

        // when
        List<OrderSummary> orderSummariesSell = orderBoardService.getOrderSummary(OrderType.SELL);
        List<OrderSummary> orderSummariesBuy = orderBoardService.getOrderSummary(OrderType.BUY);

        // then
        assertThat(orderSummariesSell, contains(expectedOrderSummary1));
        assertThat(orderSummariesBuy, contains(expectedOrderSummary2));
    }
}
