package lk.ijse.dep.web.pos.repository;

import lk.ijse.dep.web.pos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    Customer getFirstLastCustomerIdByOrderByIdDesc() throws Exception;

//    List<Customer> findAllCustomersByOrderByName() throws Exception;
//
//    List<Customer> readAllCustomersByAddressLike(String query) throws Exception;
//
//    long countAllCustomersByNameStartingWithAndAddressStartingWith(String name, String address) throws Exception;

    //@Query(value = "SELECT * FROM Customer c WHERE c.name LIKE ?1%", nativeQuery = true)
//    @Query(value = "SELECT c FROM Customer c WHERE c.name LIKE ?1%")
//    List<Customer> findCustomers1(String name)throws Exception;
//
//    List<String> getCustomersViaNamedQuery() throws Exception;
//
//    List<String> getCustomersViaNamedNativeQuery() throws Exception;

}
