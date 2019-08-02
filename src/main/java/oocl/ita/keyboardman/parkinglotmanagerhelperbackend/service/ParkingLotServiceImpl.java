package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import com.itmuch.lightsecurity.jwt.UserOperator;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.OrderDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ParkingLotDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.exception.NoAuthException;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ClerkRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.OrderRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.OrderStatusConfig.PARKED;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClerkRepository clerkRepository;

    @Autowired
    private UserOperator userOperator;

    @Override
    public List<ParkingLotDTO> findParkingLotByParkingBoyId() {
        String parkingBoyId = userOperator.getUser().getUsername();
        List<ParkingLot> parkingLots = parkingLotRepository.findByManagedBy(parkingBoyId);
        List<ParkingLotDTO> parkingLotDTOS = formatParkingLotDTO(parkingLots);
        return parkingLotDTOS;
    }

    private List<ParkingLotDTO> formatParkingLotDTO(List<ParkingLot> parkingLots) {
        List<ParkingLotDTO> parkingLotDTOS = new ArrayList<>();
        for (ParkingLot parkingLot : parkingLots) {
            parkingLotDTOS.add(createParkingLotDTO(parkingLot));
        }
        return parkingLotDTOS;
    }

    private ParkingLotDTO createParkingLotDTO(ParkingLot parkingLot) {
        ParkingLotDTO parkingLotDTO = new ParkingLotDTO(parkingLot);
        parkingLotDTO.setResidualPosition(residualPosition(parkingLot));
        parkingLotDTO.setNameOfcreateBy(clerkRepository.findById(parkingLot.getCreateBy()).get().getName());
        if (parkingLot.getManagedBy() != null) {
            parkingLotDTO.setNameOfmanagedBy(clerkRepository.findById(parkingLot.getManagedBy()).get().getName());

        }
        return parkingLotDTO;
    }

    @Override
    public List<ParkingLotDTO> findAllParkingLotWithoutMangedBy() throws Exception {

        if (userOperator.getUser() != null) {

            return formatParkingLotDTO(parkingLotRepository.findAll().stream().filter(e -> (e.getManagedBy() == null && e.getStatus()!= 0)).collect(Collectors.toList()));

        } else throw new NoAuthException();

    }

    @Override
    public ParkingLotDTO updateManagedBy(String parkingLotId, ParkingLot parkingLot) {

        ParkingLot parkingLot1 = parkingLotRepository.findByParkingLotId(parkingLotId);
        parkingLot1.setManagedBy(parkingLot.getManagedBy());
        return createParkingLotDTO(parkingLotRepository.save(parkingLot1));

    }

    @Override
    public List<ParkingLotDTO> findAllParkingLotByCreatedBy() {
        String managerId = userOperator.getUser().getUsername();
        return formatParkingLotDTO(parkingLotRepository.findByCreateBy(managerId));
    }

    @Override
    public List<ParkingLot> findParkingLotsByManagerIdAndConditions(String parkingLotName, int lowerLimit, int upperLimit) {
        List<ParkingLot> parkingLots = new ArrayList<>();
        if (!parkingLotName.isEmpty()) {
            parkingLots = parkingLotRepository.findByNameLike("%" + parkingLotName + "%");
        }else {
            parkingLots = parkingLotRepository.findAll();
        }

        if (lowerLimit != -1 && upperLimit != -1){
            parkingLots = parkingLots.stream().filter(parkingLot -> (parkingLot.getCapacity() <= upperLimit && parkingLot.getCapacity() >= lowerLimit)).collect(Collectors.toList());
        }
        else if (lowerLimit != -1 && upperLimit == -1){
            parkingLots = parkingLots.stream().filter(parkingLot -> parkingLot.getCapacity() >= lowerLimit).collect(Collectors.toList());
        }
        else if (lowerLimit == -1 && upperLimit != -1){
            parkingLots = parkingLots.stream().filter(parkingLot -> parkingLot.getCapacity() <= upperLimit).collect(Collectors.toList());
        }
        return parkingLots;
    }

    @Override
    public ParkingLotDTO updateParkingLot(String parkingLotId, ParkingLot parkingLot) {
        ParkingLot parkingLot1 = parkingLotRepository.findByParkingLotId(parkingLotId);
        parkingLot1.setCapacity(parkingLot.getCapacity());
        parkingLot1.setName(parkingLot.getName());
        return createParkingLotDTO(parkingLotRepository.save(parkingLot1));
    }

    @Override
    public ParkingLot logoutParkingLot(String parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findByParkingLotId(parkingLotId);
        parkingLot.setStatus(parkingLot.getStatus()==0?1:0);
        return parkingLotRepository.save(parkingLot);
    }

    @Override
    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        String createBy = userOperator.getUser().getUsername();
        parkingLot.setCreateBy(createBy);
        parkingLot.setLongtitude(0);
        parkingLot.setLatitude(0);
        return parkingLotRepository.save(parkingLot);
    }

    @Override
    public List<ParkingLot> findAllParingLot() {
        return parkingLotRepository.findAll();
    }

    @Override
    public ParkingLot findAllParingLotById(String parkingLotId) {
        return parkingLotRepository.findById(parkingLotId).get();
    }

    public int residualPosition(ParkingLot parkingLot) {
        int usedPosition = orderRepository.findByParkingLotIdAndStatus(parkingLot.getParkingLotId(), PARKED).size();
        return parkingLot.getCapacity() - usedPosition;
    }
}
