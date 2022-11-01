package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.CustomerDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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
        resp.getWriter().println("<h1>doPost..</h1>");
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

    /*========================================================*/

    private void getCustomerDetails(String customerID , HttpServletResponse response) throws IOException {

        try(Connection connection = pool.getConnection()){

            PreparedStatement stm = connection.prepareStatement("SELECT * FROM member WHERE id=?");
            stm.setString(1,customerID);

            ResultSet rst = stm.executeQuery();

            if (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                response.setContentType("application/json");
                JsonbBuilder.create().toJson(new CustomerDTO(id,name,address),response.getWriter());

            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Not a registered customer");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Can not load customers");
        }


    }
}
