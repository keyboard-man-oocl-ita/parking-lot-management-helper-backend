package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import com.itmuch.lightsecurity.annotation.PreAuthorize;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ParkingLotDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class ParkingLotController {
    @Autowired
    ParkingLotService parkingLotService;

    @PreAuthorize("hasAnyRoles('admin','manager')")
    @GetMapping("/parkingLots/{parkingLotId}")
    public ParkingLot findParkingLotById(@PathVariable String parkingLotId){
        return parkingLotService.findAllParingLotById(parkingLotId);
    }

    @PreAuthorize("hasAllRoles('clerk')")
    @GetMapping("/parkingBoys/parkingLots")
    public List<ParkingLotDTO> findParkingLotByParkingBoyId() {
        return parkingLotService.findParkingLotByParkingBoyId();
    }

    @PreAuthorize("hasAllRoles('manager')")
    @GetMapping("/parkingLots")
    public List<ParkingLotDTO> findAllParkingLotWithoutMangedBy() throws Exception {
        return parkingLotService.findAllParkingLotWithoutMangedBy();
    }

    @PreAuthorize("hasAllRoles('manager')")
    @PatchMapping("/parkingLots/{parkingLotId}")
    public ParkingLotDTO updateManagedBy(@PathVariable String parkingLotId, @RequestBody ParkingLot parkingLot) {
        return parkingLotService.updateManagedBy(parkingLotId, parkingLot);
    }

    @PreAuthorize("hasAllRoles('manager')")
    @GetMapping(value = "/parkingLotDashboard")
    public List<ParkingLotDTO> findAllParkingLotByCreatedBy(){
        return parkingLotService.findAllParkingLotByCreatedBy();
    }

    @PreAuthorize("hasAnyRoles('manager','admin')")
    @GetMapping(value = "/parkingLots",params = {"parkingLotName","lowerLimit","upperLimit"})
    public List<ParkingLot> findParkingLotsByConditions(String parkingLotName,int lowerLimit,int upperLimit){
        return parkingLotService.findParkingLotsByManagerIdAndConditions(parkingLotName,lowerLimit,upperLimit);
    }

    @PreAuthorize("hasAnyRoles('manager','admin')")
    @PatchMapping(value = "/parkingLots",params = {"parkingLotId"})
    public ParkingLotDTO updateParkingLot(@RequestParam String parkingLotId, @RequestBody ParkingLot parkingLot){
        return parkingLotService.updateParkingLot(parkingLotId,parkingLot);
    }

    @PreAuthorize("hasAnyRoles('manager','admin')")
    @PatchMapping("/parkingLots/{parkingLotId}/logout")
    public ParkingLot logoutParkingLot(@PathVariable String parkingLotId){
        return parkingLotService.logoutParkingLot(parkingLotId);
    }

    @PreAuthorize("hasAnyRoles('manager','admin')")
    @PostMapping("/parkingLots")
    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot){
        return parkingLotService.createParkingLot(parkingLot);
    }

    @PreAuthorize("hasAnyRoles('manager','admin')")
    @GetMapping("/admin/parkingLots")
    public List<ParkingLot> findAllParkingLot(){
        return parkingLotService.findAllParingLot();
    }
}
