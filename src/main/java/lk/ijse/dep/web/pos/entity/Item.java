package lk.ijse.dep.web.pos.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import java.math.BigDecimal;

//@NamedNativeQuery(name="Item.getItemsViaNamedNativeQuery",
//        query = "SELECT * FROM Item i WHERE i.description LIKE CONCAT(:query,'%') AND i.qtyOnHand > :qty",
//resultClass = Item.class)
//@NamedQuery(name="Item.getItemsViaNamedQuery",
//        query = "SELECT i FROM lk.ijse.dep.pos.jpa.entity.Item i WHERE i.description LIKE CONCAT(?1,'%') " +
//                "AND i.qtyOnHand > ?2")
//@NamedQuery(name="Item.getItemsViaNamedQuery2",
//        query = "SELECT i.code AS code, i.description AS description FROM lk.ijse.dep.pos.jpa.entity.Item i WHERE i.description LIKE CONCAT(?1,'%') " +
//                "AND i.qtyOnHand > ?2")
//@NamedQuery(name="Item.getItemsViaNamedQuery3",
//        query = "SELECT NEW lk.ijse.dep.pos.jpa.entity.CustomEntity2(i.code, i.description) FROM lk.ijse.dep.pos.jpa.entity.Item i WHERE i.description LIKE CONCAT(?1,'%') " +
//                "AND i.qtyOnHand > ?2")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item implements SuperEntity {
    @Id
    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
}
