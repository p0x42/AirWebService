package com.air.server.config;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.air.server.common.IpPool;
import com.air.server.common.MacIpAllocationTable;

@Configuration
@PropertySource(value = { "classpath:base.config" }, ignoreResourceNotFound = true)
public class BaseConfig {

	private static final String EXPIRY_TIME = "expiry_time";
	private static final String IPADDRESS_RANGE = "ipaddress_range";

	@Autowired
	private Environment environment;

	@Bean
	public IpPool ipPool() {
		ConcurrentLinkedQueue<String> pool = new ConcurrentLinkedQueue<>();
		String addressRange = environment.getProperty(IPADDRESS_RANGE);
		String startAddress = addressRange.split(":")[0];
		String endAddress = addressRange.split(":")[1];
		pool.add(startAddress);
		while (!startAddress.equals(endAddress)) {
			startAddress = getNextIPV4Address(startAddress);
			pool.add(startAddress);
		}
		return new IpPool(pool);
	}

	@Bean
	public MacIpAllocationTable macIpAllocationTable(IpPool ipPool) {
		long expiryInMillis = Long.parseLong(environment.getProperty(EXPIRY_TIME));
		return new MacIpAllocationTable(expiryInMillis, ipPool);
	}

	public static String getNextIPV4Address(String ip) {
		String[] nums = ip.split("\\.");
		int i = (Integer.parseInt(nums[0]) << 24 | Integer.parseInt(nums[2]) << 8 | Integer.parseInt(nums[1]) << 16
				| Integer.parseInt(nums[3])) + 1;

		// If you wish to skip over .255 addresses.
		if ((byte) i == -1)
			i++;

		return String.format("%d.%d.%d.%d", i >>> 24 & 0xFF, i >> 16 & 0xFF, i >> 8 & 0xFF, i >> 0 & 0xFF);
	}

}
