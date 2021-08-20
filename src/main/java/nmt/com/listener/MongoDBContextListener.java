package nmt.com.listener;

import com.mongodb.MongoClient;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.UnknownHostException;

@WebListener
public class MongoDBContextListener implements ServletContextListener {
    private static final String host = "localhost";
    private static final int port = 27017;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MongoClient mongo = new MongoClient(host,port);
        System.out.println("MongoClient initialized successfully");
        sce.getServletContext().setAttribute("MONGO_CLIENT",mongo);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MongoClient mongo = (MongoClient) sce.getServletContext().getAttribute("MONGO_CLIENT");
        mongo.close();
        System.out.println("MongoClient closed successfully");
    }

}
