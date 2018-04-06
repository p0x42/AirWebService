package com.air.server.api.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.air.server.api.HeartBeatService;
import com.air.server.common.MacIpAllocationTable;
import com.air.server.dto.GenericResponse;

@Service("heartBeatService")
public class HeartBeatServiceImpl implements HeartBeatService {

	@Autowired
	private MacIpAllocationTable macIpAllocationTable;

	@Override
	public Response refresh(String macAddress, String ipAddress) {
		final GenericResponse response = new GenericResponse();
		
		String assignedIpAddress = macIpAllocationTable.getMacIpMap().get(macAddress);
		
		if (assignedIpAddress == null) {
			response.setMessage("No IP has been allocated to the user with macAddress:" + macAddress);
			return Response.ok(response).build();
		}
		
		if (assignedIpAddress.equals(ipAddress)) {
			macIpAllocationTable.getMacIpMap().remove(macAddress);
			macIpAllocationTable.getMacIpMap().put(macAddress, assignedIpAddress);
			response.setMessage("IP:" + assignedIpAddress + " for macAddress:" + macAddress + " has been refreshed");
		} else {
			response.setMessage("Different IP is assigned to the user with macAddress:" + macAddress);
		}
		
		return Response.ok(response).build();
	}

}
