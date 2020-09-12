package lk.ijse.dep.web.pos.api;

import lk.ijse.dep.web.pos.business.custom.CustomerBO;
import lk.ijse.dep.web.pos.business.custom.ItemBO;
import lk.ijse.dep.web.pos.dto.CustomerDTO;
import lk.ijse.dep.web.pos.dto.ItemDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet(name = "ItemServlet", urlPatterns = "/items")
public class ItemServlet extends HttpServlet {

    private ItemBO itemBO;

    public static String getParameter(String queryString, String parameterName){
        if (queryString == null || parameterName == null || queryString.trim().isEmpty() || parameterName.trim().isEmpty()){
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
        itemBO = ((AnnotationConfigApplicationContext)
                (getServletContext().getAttribute("ctx"))).getBean(ItemBO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        resp.setContentType("text/plain");
        try (PrintWriter out = resp.getWriter()) {
            if (code == null) {
                List<ItemDTO> allItems = itemBO.getAllItems();
                allItems.forEach(out::println);
            } else {
                try {
                    ItemDTO item = itemBO.getItem(code);
                    out.println(item);
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
        String code = request.getParameter("code");
        String description = request.getParameter("description");
        String qtyOnHand = request.getParameter("qtyOnHand");
        String unitPrice = request.getParameter("unitPrice");

        /*if (!code.matches("C\\d{3}") || description.trim().length() < 2 || Integer.parseInt(request.) ||unitPrice.matches("(?:(?:RS)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,4})?)[., ]")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }*/

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            try {
                itemBO.getItem(code);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (NoSuchElementException e) {
                itemBO.saveItem(code, description, Integer.parseInt(qtyOnHand), Double.parseDouble(unitPrice)) ;
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("Item has been saved successfully");
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

        String code = getParameter(queryString, "code");

        if (code == null) {
            // Could not find an id
            return;
        }

        BufferedReader reader = req.getReader();
        String line = null;
        String requestBody = "";

        while ((line = reader.readLine()) != null) {
            requestBody += line;
        }

        String description = getParameter(requestBody, "description");
        String qtyOnHand = getParameter(requestBody, "qtyOnHand");
        String unitPrice = getParameter(requestBody, "unitPrice");

        resp.setContentType("text/plain");
        try {
            itemBO.getItem(code);
            itemBO.updateItem(description, Integer.parseInt(qtyOnHand), Double.parseDouble(unitPrice),code);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            System.out.println("Item updated successfully");
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
        String code = getParameter(queryString, "code");
        if (code == null){
            return;
        }
        resp.setContentType("text/plain");
        try {
            itemBO.getItem(code);
            itemBO.deleteItem(code);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            System.out.println("Item deleted successfully");
        } catch (NoSuchElementException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


}
