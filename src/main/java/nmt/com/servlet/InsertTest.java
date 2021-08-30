package nmt.com.servlet;

import com.mongodb.MongoClient;
import nmt.com.dao.MongoDBCustomerDAO;
import nmt.com.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@WebServlet("/insert-test")
public class InsertTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
        create(customerDAO);
    }

    private void create(MongoDBCustomerDAO customerDAO){
        for (int i = 0; i < 2000000; i++) {
            Customer cus = new Customer();
            cus.setCode(i+1);
            cus.setName("Chang " + (i+1));
            cus.setAddress("Hanoi "+ (i+1));
            cus.setDateBirth(new Date(99,7,30));
            cus.setCreatedAt(Timestamp.from(Instant.now()));
            customerDAO.createCustomer(cus);
        }
    }
}
