package lk.ijse.dep.web.pos.repository;

import lk.ijse.dep.web.pos.entity.CustomEntity;
import lk.ijse.dep.web.pos.entity.CustomEntity2;
import lk.ijse.dep.web.pos.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {

    Item getFirstLastItemCodeByOrderByCodeDesc() throws Exception;

//    List<Item> findAllItemsByUnitPriceGreaterThan(BigDecimal price) throws Exception;
//
//    List<Item> findAllItemsByQtyOnHandLessThanAndUnitPriceGreaterThan(int qtyOnHand, BigDecimal unitPrice) throws Exception;
//
////    @Query(value = "SELECT * FROM Item i WHERE i.qtyOnHand < 50", nativeQuery = true)
//    @Query(value = "SELECT i FROM Item i WHERE i.qtyOnHand < 50")
//    List<Item> findAllItemsLessThan50()throws  Exception;
//
////    @Query(value = "SELECT i.description FROM Item  i WHERE i.unitPrice > ?1", nativeQuery = true)
////    @Query(value = "SELECT i.description FROM Item  i WHERE i.unitPrice > :abc", nativeQuery = true)
////    @Query(value = "SELECT i.description FROM Item  i WHERE i.unitPrice > :#{#abc}", nativeQuery = true)
//    @Query(value = "SELECT i.description FROM Item  i WHERE i.unitPrice > ?#{[0]}", nativeQuery = true)
//    List<String> getItems2(@Param("abc") BigDecimal unitPrice) throws Exception;
//
//    @Query(value = "SELECT * FROM Item i WHERE i.qtyOnHand < ?#{T(lk.ijse.dep.pos.jpa.util.DEPUtil).getQtyOnHand()}", nativeQuery = true)
//    List<String> getItems3() throws Exception;
//
//    List<Item> getItemsViaNamedQuery(String description, int qty) throws Exception;
//
//    List<CustomEntity> getItemsViaNamedQuery2(String description, int qty) throws Exception;
//
//    List<CustomEntity2> getItemsViaNamedQuery3(String description, int qty) throws Exception;
//
//    List<Item> getItemsViaNamedNativeQuery(@Param("query") String description,
//                                           @Param("qty") int qty) throws Exception;

}
