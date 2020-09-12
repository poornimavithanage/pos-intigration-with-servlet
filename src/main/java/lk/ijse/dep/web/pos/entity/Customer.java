package lk.ijse.dep.web.pos.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;

//@NamedQuery(name = "Customer.getCustomersViaNamedQuery",
//        query = "SELECT c.address FROM Customer c WHERE c.address LIKE 'G%'")
//@NamedNativeQuery(name = "Customer.getCustomersViaNamedNativeQuery",
//        query = "SELECT c.address FROM Customer AS c WHERE c.address LIKE 'G%'")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;
}
