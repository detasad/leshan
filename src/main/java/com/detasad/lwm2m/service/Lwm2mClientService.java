package com.detasad.lwm2m.service;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.LwM2m.LwM2mVersion;
import org.eclipse.leshan.core.LwM2mId;
import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.StaticModel;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.request.argument.Arguments;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import devices.AirQualityIndexOutDoor;
import devices.DeviceFactory;
import devices.MyLocation;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class Lwm2mClientService extends BaseInstanceEnabler {

	private MyLocation locationInstance;

	// TODO
	@Value("${lwm2m.server.endpoint}")
	// String endpoint ="coap://leshan.eclipseprojects.io:5683";
	String endpoint;

	@Value("${lwm2m.server.port}")
	// int port = 12345 ;
	int port;

	@Value("${modelPaths}")
	String[] modelPaths;

	@Value("${lwm2m.server.id}")
	// int serverId = 12345 ;
	int serverId;

	@Value("${modelId}")
	int modelId;

	@Value("${lwm2m.server.protocol}")
	String protocol;

	@Value("${device.manufacturer.name}")
	String deviceManufacturerName;

	@Value("${device.model.number}")
	String deviceModelNumber;

	@Value("${device.serial.number}")
	String deviceSerialNumber;

	@Value("${useCaseDeviceLoader}")
	int useCaseDeviceLoader;

	@PostConstruct
	void init() {
		try {
			send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDDFFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send() throws IOException, InvalidModelException, InvalidDDFFileException {



		// Load model
		List<ObjectModel> models = ObjectLoader.loadDefault();
		log.info("Loading models files names " + modelPaths);
		// String[] modelPaths = new String[] {"10266.xml"};
		models.addAll(ObjectLoader.loadDdfResources("/models", modelPaths));

		ObjectsInitializer initializer = new ObjectsInitializer(new StaticModel(models));

		initializer.setInstancesForObject(LwM2mId.SECURITY, Security.noSec(endpoint, port));
		EnumSet<BindingMode> bindingSet;

		
		// initializer.setInstancesForObject(LwM2mId.DEVICE, new Device("Eclipse
		// Leshan", "model12345", "12345"));
		
		Device device = DeviceFactory.getUseCaseDevice(useCaseDeviceLoader, deviceManufacturerName, deviceModelNumber,
				deviceSerialNumber);
		
		
		((AirQualityIndexOutDoor)device).setSerialNumber(deviceSerialNumber);
		
		

		initializer.setInstancesForObject(LwM2mId.SECURITY,
				new LwM2mInstanceEnabler[] { (LwM2mInstanceEnabler) Security.noSec(endpoint, serverId) });
		if (protocol.equalsIgnoreCase("T")) {
			bindingSet = EnumSet.of(BindingMode.T);
			log.info("Setting protocl as TCP");
			initializer.setInstancesForObject(LwM2mId.SERVER,
					new Server(serverId, (long) 5 * 60, bindingSet, false, BindingMode.T));

		} else {
			bindingSet = EnumSet.of(BindingMode.U);
			log.info("Setting protocl as UDP");
			initializer.setInstancesForObject(LwM2mId.SERVER,
					new Server(serverId, (long) 5 * 60, bindingSet, false, BindingMode.U));

		}
		initializer.setInstancesForObject(LwM2mId.DEVICE, device);

		locationInstance = new MyLocation();

		initializer.setInstancesForObject(LwM2mId.LOCATION,
				new LwM2mInstanceEnabler[] { (LwM2mInstanceEnabler) locationInstance });

		// add it to the client
		LeshanClientBuilder builder = new LeshanClientBuilder(deviceSerialNumber);

		builder.setObjects(initializer.createAll());
		//builder.setCoapConfig(NetworkConfig.getStandard());
		LeshanClient client = builder.build();
		client.start();

	}


}
