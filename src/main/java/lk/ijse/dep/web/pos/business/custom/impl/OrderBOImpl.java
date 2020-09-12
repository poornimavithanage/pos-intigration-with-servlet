package lk.ijse.dep.web.pos.business.custom.impl;

import lk.ijse.dep.web.pos.business.custom.OrderBO;
import lk.ijse.dep.web.pos.dto.OrderDTO;
import lk.ijse.dep.web.pos.dto.OrderDetailDTO;
import lk.ijse.dep.web.pos.repository.CustomerRepository;
import lk.ijse.dep.web.pos.repository.ItemRepository;
import lk.ijse.dep.web.pos.repository.OrderRepository;
import lk.ijse.dep.web.pos.repository.OrderDetailRepository;
import lk.ijse.dep.web.pos.entity.Item;
import lk.ijse.dep.web.pos.entity.Order;
import lk.ijse.dep.web.pos.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional
@Component
public class OrderBOImpl implements OrderBO { // , Temp

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CustomerRepository customerRepository;

    // Interface through injection
/*    @Override
    public void injection() {
        this.orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    }  */

    // Setter method injection
/*    private void setOrderDAO(){
        this.orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    }*/

    @Transactional(readOnly = true)
    public String getNewOrderId() throws Exception {
        String lastOrderId = orderRepository.getFirstLastOrderIdByOrderByIdDesc().getId();

        if (lastOrderId == null) {
            return "OD001";
        } else {
            int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public void placeOrder(OrderDTO order, List<OrderDetailDTO> orderDetails) throws Exception {
        orderRepository.save(new Order(order.getOrderId(),
                Date.valueOf(order.getOrderDate()),
                customerRepository.findById(order.getCustomerId()).get()));

        for (OrderDetailDTO orderDetail : orderDetails) {
            orderDetailRepository.save(new OrderDetail(
                    order.getOrderId(), orderDetail.getCode(),
                    orderDetail.getQty(), orderDetail.getUnitPrice()
            ));

            Item item = itemRepository.findById(orderDetail.getCode()).get();
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
        }
    }

    @Override
    public boolean getOrder(String orderId) throws Exception {
      return orderRepository.findById(orderId).isPresent();
    }


}
