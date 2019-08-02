package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ParkingLotDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;

import java.util.List;

public interface ParkingLotService {
    List<ParkingLotDTO> findParkingLotByParkingBoyId();

    List<ParkingLotDTO> findAllParkingLotWithoutMangedBy() throws Exception;

    ParkingLotDTO updateManagedBy(String parkingLotId,ParkingLot parkingLot);

    List<ParkingLotDTO> findAllParkingLotByCreatedBy();

    List<ParkingLot> findParkingLotsByManagerIdAndConditions(String parkingLotName, int lowerLimit, int upperLimit);

    ParkingLotDTO updateParkingLot(String parkingLotId, ParkingLot parkingLot);

    ParkingLot logoutParkingLot(String parkingLotId);

    ParkingLot createParkingLot(ParkingLot parkingLot);

    List<ParkingLot> findAllParingLot();

    ParkingLot findAllParingLotById(String parkingLotId);
}
