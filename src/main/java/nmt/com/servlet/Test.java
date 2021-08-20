package nmt.com.servlet;

import com.mongodb.*;
import nmt.com.converter.CustomerConverter;
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
public class Test extends HttpServlet implements Runnable{
    Thread thread;

    public void init() throws ServletException {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    @Override
    public void run() {
        create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        create();
    }

    private void create(){
        DBCollection collection = openConnection();
        for (int i = 0; i < 1000000; i++) {
            Customer cus = new Customer();
            cus.setName("Chang " + (i+1));
            cus.setAddress("Hanoi "+ (i+1));
            cus.setDateBirth(new Date(99,7,30));
            cus.setCreatedAt(Timestamp.from(Instant.now()));
            DBObject doc = CustomerConverter.toDBObject(cus);
            collection.insert(doc);
        }
    }

    private DBCollection openConnection(){
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("demo");
        DBCollection collection = database.getCollection("customer");
        return collection;
    }

    public void destroy() {
        System.out.println("Stop!!!");
        thread.stop();
    }
}
