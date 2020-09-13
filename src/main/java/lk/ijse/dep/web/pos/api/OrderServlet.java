package lk.ijse.dep.web.pos.api;

import lk.ijse.dep.web.pos.business.custom.CustomerBO;
import lk.ijse.dep.web.pos.business.custom.ItemBO;
import lk.ijse.dep.web.pos.business.custom.OrderBO;
import lk.ijse.dep.web.pos.dto.OrderDTO;
import lk.ijse.dep.web.pos.dto.OrderDetailDTO;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@WebServlet(name = "OrderServlet", urlPatterns = "/orders")
public class OrderServlet extends HttpServlet {

    private OrderBO orderBO;


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
        orderBO = ((AnnotationConfigApplicationContext)
                (getServletContext().getAttribute("ctx"))).getBean(OrderBO.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   /*     String id = request.getParameter("id");
        String date = request.getParameter("date");
        String customerId = request.getParameter("customerId");

        System.out.println(id);
        System.out.println(date);
        System.out.println(customerId);

        String[] code = request.getParameterValues("code");
        String[] qty = request.getParameterValues("qty");
        String[] unitPrices = request.getParameterValues("unitPrice");

        System.out.println("------------- ORDER DETAILS -----------------");
        for (int i =0;i<code.length;i++) {
            System.out.println("Code=" + code[i] + ", Qty=" + qty[i] + ", UnitPrice=" + unitPrices[i] );
        }
        System.out.println("Saved Order"); */

        ArrayList<OrderDetailDTO> orderDetailList = new ArrayList<>();
        String orderId = request.getParameter("id");
        String orderDate = request.getParameter("date");
        String customerId = request.getParameter("customerId");

        String[] code = request.getParameterValues("code");
        String[] qty = request.getParameterValues("qty");
        String[] unitPrice = request.getParameterValues("unitPrice");

        if(orderId==null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (PrintWriter out = response.getWriter()) {
            if(!orderBO.getOrder(orderId)){
                for (int i =0;i<code.length;i++) {
                   // System.out.println("Code=" + code[i] + ", Qty=" + qty[i] + ", UnitPrice=" + unitPrices[i] );
                    orderDetailList.add(new OrderDetailDTO(code[i],Integer.parseInt(qty[i]),
                            new BigDecimal(unitPrice[i])));
                }
                orderBO.placeOrder(new OrderDTO(orderId,orderDate,customerId),orderDetailList);
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("Order placed successfully");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }


}
