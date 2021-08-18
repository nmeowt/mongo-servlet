package nmt.com.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisConnector {
    private static final Logger logger = LogManager.getLogger(String.valueOf(RedisConnector.class));
    private static JedisPool jedisPool = null;
    private static final String redisHostName = "localhost";
    private static final int redisPort = 6379;

    public static synchronized JedisPool getRedisConnection(){
        if(jedisPool != null){
            return jedisPool;
        }
        jedisPool = new JedisPool(new JedisPoolConfig(), redisHostName, redisPort);

        logger.info("[REDIS] Redis pool has been initialized at "
                + redisHostName + " and port # " + redisPort);
        return jedisPool;
    }

    private static Jedis getResource(){
        try{
            return jedisPool.getResource();
        } catch (Exception ex) {
            logger.error("Redis is required on " + redisHostName + " and port # " + redisPort + " and does not seem to be active. Ignoring Caching.", ex);
            return null;
        }
    }

    public static boolean addKey(String key, String value, boolean update) {
        logger.debug("Redis: added key " + key);

        boolean ret = false;
        JedisPool pool = getRedisConnection();
        if (pool == null) {
            return false;
        }

        Jedis redis = RedisConnector.getResource();
        if (redis != null) {
            String key_val = redis.get(key);
            if (key_val == null || update) {
                redis.set(key, value);
                ret = true;
            }
            pool.returnResource(redis);
        }

        return ret;
    }

    public static boolean addKeyExpire(String key, String value, boolean update, int lifetime) {
        boolean ret = false;
        JedisPool pool = getRedisConnection();
        if (pool == null) {
            return false;
        }

        Jedis redis = RedisConnector.getResource();
        if (redis != null) {
            String key_val = redis.get(key);
            if (key_val == null || update) {
                redis.setex(key, lifetime, value);
                ret = true;
            }
            pool.returnResource(redis);
        }

        return ret;
    }

    public static String getKey(String key) {
        String value = null;
        JedisPool pool = getRedisConnection();
        if (pool == null) {
            return null;
        }

        Jedis redis = RedisConnector.getResource();
        if (redis != null) {
            value = redis.get(key);
            pool.returnResource(redis);
        }

        return value;
    }

    public static boolean delKey(String key) {
        long retValue = -1;
        JedisPool pool = getRedisConnection();
        if (pool == null) {
            return false;
        }

        Jedis redis = RedisConnector.getResource();
        if (redis != null) {
            retValue = redis.del(key);
            pool.returnResource(redis);
        }

        return (retValue == 0);
    }
}
