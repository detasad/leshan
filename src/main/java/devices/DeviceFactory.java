package devices;

import org.eclipse.leshan.client.object.Device;

public class DeviceFactory {

	public static Device getUseCaseDevice(int id, String deviceManufacturerName, String deviceModelNumber, String deviceSerialNumber) {

		switch (id) {

		//Air quality indoor
		case 1:
			return new AirQualityIndexOutDoor(deviceManufacturerName,deviceModelNumber,deviceSerialNumber);

		}
		return null;
	}

}
