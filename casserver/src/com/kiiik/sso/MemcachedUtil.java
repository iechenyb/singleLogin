package com.kiiik.sso;

import java.sql.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedUtil {
	protected static MemCachedClient mcc = new MemCachedClient();
	protected static MemcachedUtil memCached = new MemcachedUtil();
	static {
		String[] servers = { "127.0.0.1:11211" };
		Integer[] weights = { 3 };
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000 * 60 * 60 * 6);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);
		pool.initialize();
		mcc.setCompressEnable(true);
		mcc.setCompressThreshold(64 * 1024);
	}
	protected MemcachedUtil() {
	}
	public static MemcachedUtil getInstance() {
		return memCached;
	}
	public boolean add(String key, Object value) {
		return mcc.add(key, value);
	}
	public boolean add(String key, Object value, Date expiry) {
		return mcc.add(key, value, expiry);
	}

	public boolean replace(String key, Object value) {
		return mcc.replace(key, value);
	}
	public boolean replace(String key, Object value, Date expiry) {
		return mcc.replace(key, value, expiry);
	}
    public boolean delete(String key){
    	return mcc.delete(key);
    }
	public Object get(String key) {
		return mcc.get(key);
	}
	public static void main(String[] args) {
		mcc.set("hello", "new values");
		mcc.replace("hello", "haha");
		//mcc.set("hello", "new values1");
		System.out.println("=:"+mcc.get("hello"));
		//mcc.delete("hello");
		//System.out.println("=:"+mcc.get("hello"));
		System.out.println(mcc.get("f70312d882a146088c7b51d43aa086e9"));
	}
}
