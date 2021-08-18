package nmt.com.servlet;

import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/redis-demo")
public class RedisDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println("EXEMPLE 1 : \t" + value);

        System.err.println("\nEXEMPLE 2\n");
        Jedis jedis2 = new Jedis("localhost");
        System.out.println("\nEXEMPLE 2 : \t" + jedis2.get("counter"));
        jedis.incr("counter");
        System.out.println("EXEMPLE 2 : \t" + jedis2.get("counter"));

        System.err.println("\nEXEMPLE 3\n");

        String cacheKey = "cachekey";
        Jedis jedis3 = new Jedis("localhost");
        // adding a new key
        jedis3.set(cacheKey, "cached value");
        // setting the TTL in seconds
        jedis3.expire(cacheKey, 15);
        System.out.println("TTL:" + jedis3.ttl(cacheKey));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TTL:" + jedis3.ttl(cacheKey));
        // Getting the cache value
        System.out.println("Cached Value:" + jedis3.get(cacheKey));

        // Wait for the TTL done
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // get the expired key
        System.out.println("Expired Key:" + jedis3.get(cacheKey));

        System.err.println("\nEXEMPLE 4\n");
        cacheKey = "languages";
        Jedis jedis4 = new Jedis("localhost");

        jedis4.sadd(cacheKey, "Java");
        jedis4.sadd(cacheKey, "C#");
        jedis4.sadd(cacheKey, "Python");

        System.out.println("Languages: " + jedis4.smembers(cacheKey));
        jedis4.sadd(cacheKey, "Java");
        jedis4.sadd(cacheKey, "Ruby");
        System.out.println("Languages: " + jedis4.smembers(cacheKey));
    }
}
