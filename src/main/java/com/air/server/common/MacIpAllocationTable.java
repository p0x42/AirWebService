package com.air.server.common;

public class MacIpAllocationTable {
	private WeakConcurrentHashMap<String, String> macIpMap;

	public MacIpAllocationTable(long expiryInMillis, IpPool ipPool) {
		macIpMap = new WeakConcurrentHashMap<>(expiryInMillis, ipPool.getPool());
	}

	public WeakConcurrentHashMap<String, String> getMacIpMap() {
		return macIpMap;
	}
}
