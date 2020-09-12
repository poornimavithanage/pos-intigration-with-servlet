package lk.ijse.dep.web.pos.dto;

import lk.ijse.dep.web.pos.entity.Customer;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private String orderId;
    private String orderDate;
    private String customerId;


}
