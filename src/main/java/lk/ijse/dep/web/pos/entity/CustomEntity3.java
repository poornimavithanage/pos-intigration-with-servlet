package lk.ijse.dep.web.pos.entity;

import java.math.BigDecimal;
import java.sql.Date;

public interface CustomEntity3 {

    String getOrderId();

    Date getOrderDate();

    String getCustomerId();

    String getCustomerName();

    BigDecimal getOrderTotal();

}
