package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,String> {
    List<ParkingLot> findByCreateBy(String userId);

    ParkingLot findByParkingLotId(String paringLotId);

    List<ParkingLot> findByManagedBy(String parkingBoyId);

    List<ParkingLot> findByNameLike(String name);
}
