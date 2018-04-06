package com.air.server.api.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.air.server.api.AllocationService;
import com.air.server.common.IpPool;
import com.air.server.common.MacIpAllocationTable;
import com.air.server.dto.GenericResponse;
import com.air.server.dto.IPResponse;

@Service("allocationService")
public class AllocationServiceImpl implements AllocationService {

	@Autowired
	private MacIpAllocationTable macIpAllocationTable;

	@Autowired
	private IpPool ipPool;

	@Override
	public Response allocate(String macAddress) {

		final GenericResponse response = new GenericResponse();
		String ipAddress = macIpAllocationTable.getMacIpMap().get(macAddress);
		if (ipAddress == null) {
			if (ipPool.getPool().size() == 0) {
				response.setMessage("No IPs available in the pool");
				return Response.ok(response).build();
			} else {
				String ipAddressToAssign = ipPool.getPool().remove();
				macIpAllocationTable.getMacIpMap().put(macAddress, ipAddressToAssign);
				IPResponse ipResponse = new IPResponse(ipAddressToAssign);
				return Response.ok(ipResponse).build();
			}
		}

		response.setMessage("IP has already been assigned to the user");

		return Response.ok(response).build();
	}

}
