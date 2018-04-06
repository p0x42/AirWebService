# For IP Allocation
Request Url: http://localhost:8080/air-web-service/allocation/{macAddress}  
Request Method: POST  

For e.g: http://localhost:8080/air-web-service/allocation/02:42:f4:91:d4:07  
Types of Response depending on the case:   
{  
    "ip": "192.168.1.3"  
}  
	or  
{  
    "message": "IP has already been assigned to the user"  
}  
	or  
{  
    "message": "No more IPs available in the pool"  
}  

# For IP Refresh  
Request Url: http://localhost:8080/air-web-service/refresh?macAddress=xxxxx&ipAddress=x.x.x.x  
Request Method: GET  

For e.g: http://localhost:8080/air-web-service/refresh?macAddress=02:42:f4:91:d4:07&ipAddress=192.168.1.3  
Types of Response depending on the case:  
{  
    "message": "IP:192.168.1.3 for macAddress:02:42:f4:91:d4:07 has been refreshed"  
}  
	or  

{  
    "message": "No IP has been allocated to the user with macAddress:02:42:f4:91:d4:07"  
}  
	or  
{  
    "message": "Different IP is assigned to the user with macAddress:02:42:f4:91:d4:07"  
}  


Tested with jdk1.8.0_77 and apache-tomcat-7.0.41  

For Build War:   
mvn clean package  


References: Java time-based map/cache with expiring keys  
https://stackoverflow.com/questions/3802370/java-time-based-map-cache-with-expiring-keys  

