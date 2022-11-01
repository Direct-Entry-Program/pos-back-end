package lk.ijse.dep9.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.ItemDTO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "item-servlet", value = "/items/*")
public class ItemServlet extends HttpServlet2 {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doGet..</h1>");
    }

    private void loadAllItems(HttpServletResponse response) throws IOException {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep9_lms", "root", "mysql")) {
//                Statement stm = connection.createStatement();
//                ResultSet rst = stm.executeQuery("SELECT * FROM item");
//
//                ArrayList<ItemDTO> members = new ArrayList<>();
//
//                while (rst.next()){
//                    String code = rst.getString("code");
//                    String stock = rst.getString("stock");
//                    String unit_price = rst.getString("unit_price");
//                    String description = rst.getString("description");
//                    ItemDTO dto = new ItemDTO(code, stock, unit_price, description);
//                    members.add(dto);
//                }
//
//                Jsonb jsonb = JsonbBuilder.create();
//                response.setContentType("application/json");
//                jsonb.toJson(members, response.getWriter());
//
//
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch items");
//        }

        response.getWriter().printf("WS: Load All items");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doPost..</h1>");
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doPatch..</h1>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doDelete..</h1>");
    }
}
