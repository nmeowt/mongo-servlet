package nmt.com.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private String host = "localhost";
    private JedisPool jedisPool;

    public JedisPool getJedisPool(){
        jedisPool = new JedisPool(new JedisPoolConfig(), host);
        System.out.println("Jedis Pool: " + jedisPool);
        return jedisPool;
    }

    public void detroy(JedisPool jedisPool){
        System.out.println("Destroying :" + jedisPool);
        jedisPool.destroy();
    }
}
