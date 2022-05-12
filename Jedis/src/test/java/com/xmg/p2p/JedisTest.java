package com.xmg.p2p;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class JedisTest
{
    /**
     * Jedis的helloWord程序
     */
    @Test
    public void testHello()
    {
        Jedis jedis = new Jedis ("localhost",6379);
        jedis.set("foo","bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }

    /**
     * hash数据结构的使用
     */
    @Test
    public void testHash()
    {
        Jedis jedis = new Jedis ("localhost",6379);
        //HMSET user:1001 name "Mary Jones" password "hidden" email "mjones@example.com"
        Map<String,String> user = new HashMap<>();
        user.put("name","Mary Jones");
        user.put("password","hidden");
        user.put("email","mjones@example.com");
        jedis.hmset("user:1001",user);
        //hgetall user:1001
        Map<String, String> map = jedis.hgetAll("user:1001");
        System.out.println(map);
    }

    /**
     * Jedis连接池
     */
    @Test
    public void testJedisPool(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig,"localhost",6379);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            System.out.println(jedis.get("foo"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Jedis如何保存对象
     */
    @Test
    public void testSaveObject(){
        //对象需要实现序列化接口
        User u = new User(1L,"xmg");
        Jedis jedis = new Jedis("localhost");
        //保存进缓存前需要先序列化
        jedis.set("user:1".getBytes(),SerializeUtil.serialize(u));
        //从缓存中拿出之后需要反序列化
        User u2 = (User) SerializeUtil.unserialize(jedis.get("user:1".getBytes()));
        System.out.println(u2);
    }
}

