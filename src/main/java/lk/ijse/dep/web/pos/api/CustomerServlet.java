package lk.ijse.dep.web.pos.api;

import lk.ijse.dep.web.pos.business.custom.CustomerBO;
import lk.ijse.dep.web.pos.dto.CustomerDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerBO customerBO;

    public static String getParameter(String queryString, String parameterName) {
        if (queryString == null || parameterName == null || queryString.trim().isEmpty() || parameterName.trim().isEmpty()) {
            return null;
        }

        String[] queryParameters = queryString.split("&");
        for (String queryParameter : queryParameters) {
            if (queryParameter.contains("=") && queryParameter.startsWith(parameterName)) {
                return queryParameter.split("=")[1];
            }
        }
        return null;
    }

    @Override
    public void init() throws ServletException {
        customerBO = ((AnnotationConfigApplicationContext)
                (getServletContext().getAttribute("ctx"))).getBean(CustomerBO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        resp.setContentType("text/plain");
        try (PrintWriter out = resp.getWriter()) {
            if (id == null) {
                List<CustomerDTO> allCustomers = customerBO.getAllCustomers();
                allCustomers.forEach(out::println);
            } else {
                try {
                    CustomerDTO customer = customerBO.getCustomer(id);
                    out.println(customer);
                } catch (NoSuchElementException e) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        if (!id.matches("C\\d{3}") || name.trim().length() < 3 || address.trim().length() < 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            try {
                customerBO.getCustomer(id);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (NoSuchElementException e) {
                customerBO.saveCustomer(id, name, address);
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("Customer has been saved successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        if (queryString == null) {
            // There is nothing to proceed
            return;
        }

        String id = getParameter(queryString, "id");

        if (id == null) {
            // Could not find an id
            return;
        }

        BufferedReader reader = req.getReader();
        String line = null;
        String requestBody = "";

        while ((line = reader.readLine()) != null) {
            requestBody += line;
        }

        String name = getParameter(requestBody, "name");
        String address = getParameter(requestBody, "address");

        if (!id.matches("C\\d{3}") || name.trim().length() < 3 || address.trim().length() < 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.setContentType("text/plain");
        try {
            customerBO.getCustomer(id);
            customerBO.updateCustomer(name,address,id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            System.out.println("Customer updated successfully");
        } catch (NoSuchElementException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        String id = getParameter(queryString, "id");
        if (id == null){
            return;
        }
        resp.setContentType("text/plain");
        try {
            customerBO.getCustomer(id);
            customerBO.deleteCustomer(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            System.out.println("Customer deleted successfully");
        } catch (NoSuchElementException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
