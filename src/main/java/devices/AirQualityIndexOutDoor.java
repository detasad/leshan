package devices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.argument.Arguments;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class AirQualityIndexOutDoor extends Device {

	String deviceManufacturerName;
	String deviceModelNumber;
	String serialNumber;

	public AirQualityIndexOutDoor() {

	}

	public AirQualityIndexOutDoor(String deviceManufacturerName, String deviceModelNumber, String deviceSerialNumber) {
		super(deviceManufacturerName, deviceModelNumber, deviceSerialNumber);

		this.deviceManufacturerName = deviceManufacturerName;
		this.deviceModelNumber = deviceModelNumber;
		this.serialNumber = deviceSerialNumber;

	}

	@Override
	    public ReadResponse read(ServerIdentity identity, int resourceid) {
		 
		log.info("Read called on resource id " + resourceid + " deviceManufacturerName " + deviceManufacturerName
				+ " deviceModelNumber " + deviceModelNumber + " deviceSerialNumber " + serialNumber);
	        switch (resourceid) {
	        case 0:
	            return ReadResponse.success(resourceid, getManufacturer());
	            
	        case 1:
	        	/*  52 */         return ReadResponse.success(resourceid, getModelNumber());
	        	/*     */       case 2:
	        	/*  54 */         return ReadResponse.success(resourceid, getSerialNumber());
	        	/*     */       case 3:
	        	/*  56 */         return ReadResponse.success(resourceid, getFirmwareVersion());
	        	/*     */       case 9:
	        	/*  58 */         return ReadResponse.success(resourceid, getBatteryLevel());
	        	/*     */       case 10:
	        	/*  60 */         return ReadResponse.success(resourceid, getMemoryFree());
	        	/*     */       case 11:
	        	/*  62 */        
	        	/*  64 */         return ReadResponse.success(resourceid, 1);
	        	/*     */       case 13:
	        	/*  66 */         return ReadResponse.success(resourceid, getCurrentTime());
	        	/*     */       case 14:
	        	/*  68 */         return ReadResponse.success(resourceid, getUtcOffset());
	        	/*     */       case 15:
	        	/*  70 */         return ReadResponse.success(resourceid, getTimezone(), ResourceModel.Type.STRING);
	        	/*     */       case 16:
	        	/*  72 */         return ReadResponse.success(resourceid, getSupportedBinding());
	        	/*     */       case 17:
	        	/*  74 */         return ReadResponse.success(resourceid, getDeviceType());
	        	/*     */       case 18:
	        	/*  76 */         return ReadResponse.success(resourceid, getHardwareVersion());
	        	/*     */       case 19:
	        	/*  78 */         return ReadResponse.success(resourceid, getSoftwareVersion());
	        	/*     */       case 20:
	        	/*  80 */         return ReadResponse.success(resourceid, getBatteryStatus());
	        	/*     */       case 21:
	        	/*  82 */         return ReadResponse.success(resourceid, getMemoryTotal());
	        	/*     */     } 
	        	/*  84 */     return super.read(identity, resourceid);
	   
	       
	    }

	@Override
	public WriteResponse write(ServerIdentity identity, boolean replace, int resourceid, LwM2mResource value) {
		log.info("write called on resource id " + resourceid + " deviceManufacturerName " + deviceManufacturerName
				+ " deviceModelNumber " + deviceModelNumber + " deviceSerialNumber " + serialNumber);
		switch (resourceid) {
		case 15:
			// setCollectionPeriod((Long) value.getValue());
			return WriteResponse.success();
		}
		return WriteResponse.notFound();
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, Arguments params) {

		log.info("execute called on identity  " + identity);
		log.info("execute called on  deviceManufacturerName " + deviceManufacturerName
				+ " deviceModelNumber " + deviceModelNumber + " deviceSerialNumber " + serialNumber);
		switch (resourceid) {
		case 12:
			// start();
			return ExecuteResponse.success();
		}
		return ExecuteResponse.notFound();
	}
	
	  private String getManufacturer() {
		  /* 116 */     return this.getDeviceManufacturerName();
		  /*     */   }
		  /*     */   
		  /*     */   private String getModelNumber() {
		  /* 120 */     return this.getDeviceModelNumber();
		  /*     */   }
		  /*     */   
		  /*     */   private String getSerialNumber() {
		  /* 124 */     return this.serialNumber;
		  /*     */   }
		  /*     */   
		  /*     */   private String getFirmwareVersion() {
		  /* 128 */     return "1.0.0";
		  /*     */   }
		  /*     */   
		  /*     */   private long getErrorCode() {
		  /* 132 */     return 0L;
		  /*     */   }
		  /*     */   
		  /*     */   private int getBatteryLevel() {
		  /* 136 */     return 40 ;
		  /*     */   }
		  /*     */   
		  /*     */   private long getMemoryFree() {
		  /* 140 */     return Runtime.getRuntime().freeMemory() / 1024L;
		  /*     */   }
		  /*     */   
		  /*     */   private Date getCurrentTime() {
		  /* 144 */     return new Date();
		  /*     */   }
		  /*     */   
		  /* 147 */   private String utcOffset = (new SimpleDateFormat("X")).format(Calendar.getInstance().getTime());
		  /*     */   
		  /*     */   private String getUtcOffset() {
		  /* 150 */     return this.utcOffset;
		  /*     */   }
		  /*     */   
		  /*     */   private void setUtcOffset(String t) {
		  /* 154 */     this.utcOffset = t;
		  /*     */   }
		  /*     */   
		  /* 157 */   private String timeZone = TimeZone.getDefault().getID();
		  /* 158 */   Map<Integer, String> timeZoneList = new HashMap<>();
		  /*     */ 
		  /*     */ 
		  /*     */   
		  /*     */   private Map<Integer, String> getTimezone() {
		  /* 163 */     this.timeZoneList.put(Integer.valueOf(0), "+5");
		  /* 164 */     this.timeZoneList.put(Integer.valueOf(1), this.timeZone);
		  /* 165 */     return this.timeZoneList;
		  /*     */   }
		  /*     */   
		  /*     */   private void setTimezone(String t) {
		  /* 169 */     this.timeZone = t;
		  /*     */   }
		  /*     */   public void setSerialNumber(String sn) {
		  /* 172 */     this.serialNumber = sn;
		  /*     */   }
		  /*     */   
		  /*     */   private String getSupportedBinding() {
		  /* 176 */     return "U";
		  /*     */   }
		  /*     */   
		  /*     */   private String getDeviceType() {
		  /* 180 */     return "Demo";
		  /*     */   }
		  /*     */   
		  /*     */   private String getHardwareVersion() {
		  /* 184 */     return "1.0.1";
		  /*     */   }
		  /*     */   
		  /*     */   private String getSoftwareVersion() {
		  /* 188 */     return "1.0.2";
		  /*     */   }
		  /*     */   
		  /*     */   private int getBatteryStatus() {
		  /* 192 */     return 3;
		  /*     */   }
		  /*     */   
		  /*     */   private long getMemoryTotal() {
		  /* 196 */     return Runtime.getRuntime().totalMemory() / 1024L;
		  /*     */   }
		  /*     */ 

}
