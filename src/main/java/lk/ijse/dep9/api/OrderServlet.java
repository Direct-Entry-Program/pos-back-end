package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.OrderCompleteDTO;
import lk.ijse.dep9.dto.OrderDTO;
import lk.ijse.dep9.dto.OrderDetailsDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "order-servlet", value = "/orders/*")
public class OrderServlet extends HttpServlet2 {
    @Resource(lookup = "java:/comp/env/jdbc/pos")
    private DataSource pool;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            String query = request.getParameter("q");
            String size = request.getParameter("size");
            String page = request.getParameter("page");

            if (query != null && size != null && page != null) {
                if (!size.matches("\\d+") || !page.matches("\\d+")) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page or size");
                }
                else {
//                    searchPaginatedOrders(query, Integer.parseInt(size), Integer.parseInt(page), response);
                    response.getWriter().println("<h1>Search paginated orders</h1>");
                }
            }
        }
        else {
            Matcher matcher = Pattern.compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})/?$")
                    .matcher(request.getPathInfo());
            if (matcher.matches()) {
                    getOrderDetails(matcher.group(1), response);
//                response.getWriter().println("<h1>Get order details</h1>");
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Expected valid UUID");
            }
        }
    }

    private void searchPaginatedOrders(String query, int parseInt, int parseInt1, HttpServletResponse response) {

    }

    private void getOrderDetails(String orderId, HttpServletResponse response) throws IOException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement orderStm = connection.prepareStatement("SELECT * FROM Order WHERE id=?");
            orderStm.setString(1, orderId);
            ResultSet orderRst = orderStm.executeQuery();
            OrderDTO orderDTO = null;
            if (orderRst.next()) {
                String id = orderRst.getString("id");
                String date = orderRst.getString("date");
                String customerId = orderRst.getString("customer_id");
                orderDTO = new OrderDTO(id, date, customerId);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid order id");
                return;
            }
            PreparedStatement orderIdStm = connection.prepareStatement("SELECT  * FROM Order_details WHERE order_id=?");
            orderIdStm.setString(1, orderId);
            ResultSet orderIdRst = orderIdStm.executeQuery();
            OrderDetailsDTO orderDetailsDTO = null;
            if (orderIdRst.next()) {
                String orderId1 = orderIdRst.getString("order_id");
                String itemCode = orderIdRst.getString("item_code");
                String unitPrice = orderIdRst.getString("unit_price");
                String qty = orderIdRst.getString("qty");
                orderDetailsDTO = new OrderDetailsDTO(orderId1, itemCode, unitPrice, qty);
            }
            OrderCompleteDTO dto = new OrderCompleteDTO(orderDTO.getId(), orderDTO.getDate(), orderDTO.getCustomerId(), orderDetailsDTO.getItemCode(), orderDetailsDTO.getUnitPrice(), orderDetailsDTO.getQty());
            response.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(dto, response.getWriter());
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch order details");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Order Servlet - doPost...</h1>");
    }
}
