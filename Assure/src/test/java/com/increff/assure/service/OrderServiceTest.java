//package com.increff.assure.service;
//
//import com.increff.assure.pojo.OrderItemPojo;
//import com.increff.assure.pojo.OrderPojo;
//import com.increff.assure.spring.AbstractUnitTest;
//import com.increff.assure.spring.TestPojo;
//import com.increff.commons.Constants.OrderStatus;
//import com.increff.commons.Exception.ApiException;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static org.junit.Assert.assertEquals;
//
//public class OrderServiceTest extends AbstractUnitTest {
//
//    @Autowired
//    private OrderService orderService;
//
//
//    @Test
//    public void testAddOrder() throws ApiException {
//        TestPojo testPojo = new TestPojo();
//        testPojo.preliminaryInsert();
//        List<OrderPojo> list_before = orderService.getAllOrders();
//        Map<String, Long> channelOrderIdToChannelId = list_before.stream().collect(Collectors.toMap(value->value
//                .getChannelOrderId(), value->value.getChannelId()));
//        OrderPojo orderPojoReq = orderService.getByChannelIdAndChannelOrderId(channelOrderIdToChannelId.get("cOId1"),
//                "cOId1");
//        OrderPojo orderPojo_now = getOrderPojo(orderPojoReq.getClientId(), orderPojoReq.getCustomerId(),
//                orderPojoReq.getChannelId());
//        orderService.addOrder(orderPojo_now);
//        List<OrderPojo> list_after = orderService.getAllOrders();
//
//        assertEquals(list_before.size()+1, list_after.size());
//
//    }
//
//
//    @Test
//    public void testInsertOrderItemPojo() throws ApiException {
//        TestPojo testPojo = new TestPojo();
//        testPojo.preliminaryInsert();
//        List<OrderPojo> list_order_before = orderService.getAllOrders();
//        List<OrderItemPojo> list_before = orderService.getAllOrderItems();
//        Map<String, Long> channelOrderIdToChannelId = list_order_before.stream().collect(Collectors.toMap(value->value
//                .getChannelOrderId(), value->value.getChannelId()));
//        OrderPojo orderPojoReq = orderService.getByChannelIdAndChannelOrderId(channelOrderIdToChannelId.get("cOId1"),
//                "cOId1");
//        OrderPojo orderPojo_now = getOrderPojo(orderPojoReq.getClientId(), orderPojoReq.getCustomerId(),
//                orderPojoReq.getChannelId());
//        orderService.addOrder(orderPojo_now);
//
//        List<OrderItemPojo> orderItemPojoReqd = orderService.getItemsByOrderId(orderPojoReq.getId());
//        OrderItemPojo orderItemPojo = getOrderItemPojo(orderPojo_now.getId(), orderItemPojoReqd.get(0).getGlobalSkuId());
//        orderService.insertOrderItem(orderItemPojo);
//        List<OrderItemPojo> list_after = orderService.getAllOrderItems();
//
//        assertEquals(list_before.size()+1, list_after.size());
//
//    }
//
//    @Test
//    public void testGetByChannelIdAndChannelOrderId() throws ApiException {
//        TestPojo testPojo = new TestPojo();
//        testPojo.preliminaryInsert();
//        List<OrderPojo> list_before = orderService.getAllOrders();
//        Map<String, Long> channelOrderIdToChannelId = list_before.stream().collect(Collectors.toMap(value->value
//                .getChannelOrderId(), value->value.getChannelId()));
//        OrderPojo orderPojoReq = orderService.getByChannelIdAndChannelOrderId(channelOrderIdToChannelId.get("cOId1"),
//                "cOId1");
//
//        assertEquals(orderPojoReq.getId(), orderService.getByChannelIdAndChannelOrderId(orderPojoReq.getChannelId(),
//                "cOId1").getId());
//
//    }
//
//    @Test
//    public void testAllocateOrder() throws ApiException {
//        TestPojo testPojo = new TestPojo();
//        testPojo.preliminaryInsert();
//        List<OrderPojo> list_before = orderService.getAllOrders();
//        Map<String, Long> channelOrderIdToChannelId = list_before.stream().collect(Collectors.toMap(value->value
//                .getChannelOrderId(), value->value.getChannelId()));
//        OrderPojo orderPojoReq = orderService.getByChannelIdAndChannelOrderId(channelOrderIdToChannelId.get("cOId1"),
//                "cOId1");
//        OrderPojo orderPojo_now = getOrderPojo(orderPojoReq.getClientId(), orderPojoReq.getCustomerId(),
//                orderPojoReq.getChannelId());
//        orderService.addOrder(orderPojo_now);
//
//        List<OrderItemPojo> orderItemPojoReqd = orderService.getItemsByOrderId(orderPojoReq.getId());
//        OrderItemPojo orderItemPojo = getOrderItemPojo(orderPojo_now.getId(), orderItemPojoReqd.get(0).getGlobalSkuId());
//        orderService.insertOrderItem(orderItemPojo);
//        orderService.allocateOrder(orderItemPojo.getId(), 10L);
//
//        List<OrderItemPojo> orderItemPojoList = orderService.getItemsByOrderId(orderPojo_now.getId());
//        assertEquals(Long.valueOf(10), orderItemPojoList.get(0).getAllocatedQty());
//
//    }
//
//    @Test
//    public void testChangeStatusToAllocated() throws ApiException {
//        TestPojo testPojo = new TestPojo();
//        testPojo.preliminaryInsert();
//        List<OrderPojo> list_before = orderService.getAllOrders();
//        Map<String, Long> channelOrderIdToChannelId = list_before.stream().collect(Collectors.toMap(value->value
//                .getChannelOrderId(), value->value.getChannelId()));
//        OrderPojo orderPojoReq = orderService.getByChannelIdAndChannelOrderId(channelOrderIdToChannelId.get("cOId1"),
//                "cOId1");
//        OrderPojo orderPojo_now = getOrderPojo(orderPojoReq.getClientId(), orderPojoReq.getCustomerId(),
//                orderPojoReq.getChannelId());
//        orderService.addOrder(orderPojo_now);
//        orderService.changeStatusToAllocated(orderPojo_now.getId());
//
//        assertEquals(OrderStatus.ALLOCATED, orderPojo_now.getOrderStatus());
//
//    }
//
//
//
//
//
//
//    private OrderPojo getOrderPojo(Long clientId, Long customerId, Long channelId){
//        OrderPojo orderPojo = new OrderPojo();
//        orderPojo.setClientId(clientId);
//        orderPojo.setOrderStatus(OrderStatus.CREATED);
//        orderPojo.setChannelId(channelId);
//        orderPojo.setChannelOrderId("cOId2");
//        orderPojo.setCustomerId(customerId);
//        return orderPojo;
//    }
//
//    public OrderItemPojo getOrderItemPojo(Long orderId, Long globalSkuId){
//        OrderItemPojo orderItemPojo = new OrderItemPojo();
//        orderItemPojo.setOrderId(orderId);
//        orderItemPojo.setOrderedQty(10L);
//        orderItemPojo.setGlobalSkuId(globalSkuId);
//        orderItemPojo.setAllocatedQty(0L);
//        orderItemPojo.setFulfilledQty(0L);
//        orderItemPojo.setSellingPricePerUnit(10.0);
//        return orderItemPojo;
//    }
//
//}
