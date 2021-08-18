package nmt.com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConnection {
    private JedisPool jedisPool;

    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    public void returnResource(Jedis jedis){
        System.out.println("Returning jedis Connection");
        jedisPool.returnResource(jedis);
    }
}
