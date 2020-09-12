package lk.ijse.dep.web.pos.business.custom;

import lk.ijse.dep.web.pos.business.SuperBO;
import lk.ijse.dep.web.pos.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO {

    List<CustomerDTO> getAllCustomers() throws Exception;

    CustomerDTO getCustomer(String id) throws Exception;

    void saveCustomer(String id, String name, String address) throws Exception;

    void deleteCustomer(String customerId) throws Exception;

    void updateCustomer(String name, String address, String customerId) throws Exception;

    String getNewCustomerId() throws Exception;
}
