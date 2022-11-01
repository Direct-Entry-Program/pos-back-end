package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.CustomerDTO;

import javax.sql.DataSource;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(name = "customer-servlet", value = "/customers/*")
public class CustomerServlet extends HttpServlet2 {

    @Resource(lookup = "java:/comp/env/jdbc/pos")
    private DataSource pool;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
//            loadAllCustomers(response);
        resp.getWriter().println("<h1>doGet..</h1>");
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>doPatch..</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo()==null || req.getPathInfo().equals("/")){
            if (!req.getContentType().startsWith("application/json")){
                throw new JsonbException("Invalid Json");
            }
            CustomerDTO customer = JsonbBuilder.create().fromJson(req.getReader(), CustomerDTO.class);

            if (customer.getName() == null || !customer.getName().matches("[A-Za-z ]+")){
                throw new JsonbException("Name is invalid or empty");
            } else if (customer.getAddress() ==null || !customer.getAddress().matches("[A-Za-z0-9;,./\\-]+")) {
                throw new JsonbException("Address is invalid or empty");
            }

            try(Connection connection = pool.getConnection()){
                PreparedStatement stm = connection.prepareStatement("INSERT INTO Customer (id, name, address) VALUES (?,?,?)");

                stm.setString(1,UUID.randomUUID().toString());
                stm.setString(2,customer.getName());
                stm.setString(3,customer.getAddress());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }else {
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"Not Implemented yet");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>doDelete..</h1>");
    }

    public void loadAllCustomers(HttpServletResponse response) throws IOException{

        try {

            Connection connection = pool.getConnection();

            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");



            ArrayList<CustomerDTO> customers = new ArrayList<>();

            while (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");


                CustomerDTO dto = new CustomerDTO(id,name,address);
                customers.add(dto);
            }


            connection.close();

            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(customers,response.getWriter());




        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to load customers");
        }

    }




}
