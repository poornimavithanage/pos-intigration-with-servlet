package lk.ijse.dep.web.pos.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDTO {
    private String code;
    private String description;
    private int qtyOnHand;
    private BigDecimal unitPrice;
}
