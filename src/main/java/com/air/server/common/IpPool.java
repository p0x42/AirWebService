package com.air.server.common;

import java.util.concurrent.ConcurrentLinkedQueue;

public class IpPool {
	
	private ConcurrentLinkedQueue<String> pool;
	
	public IpPool(ConcurrentLinkedQueue<String> pool){
		this.pool = pool;
	}

	public ConcurrentLinkedQueue<String> getPool() {
		return pool;
	}
	
}
