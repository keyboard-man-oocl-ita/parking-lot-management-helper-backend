package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;

public class ParkingLotDTO {
    private String parkingLotId;

    private String name;

    private String description;

    private int capacity;

    private float latitude;

    private float longtitude;

    private String createBy;

    private String managedBy;

    public String getNameOfmanagedBy() {
        return NameOfmanagedBy;
    }

    public void setNameOfmanagedBy(String nameOfmanagedBy) {
        NameOfmanagedBy = nameOfmanagedBy;
    }

    public String getNameOfcreateBy() {
        return NameOfcreateBy;
    }

    public void setNameOfcreateBy(String nameOfcreateBy) {
        NameOfcreateBy = nameOfcreateBy;
    }

    private String NameOfmanagedBy;

    private String NameOfcreateBy;

    private int residualPosition;

    public ParkingLotDTO(ParkingLot parkingLot) {
        setParkingLotId(parkingLot.getParkingLotId());
        setName(parkingLot.getName());
        setDescription(parkingLot.getDescription());
        setCapacity(parkingLot.getCapacity());
        setLatitude(parkingLot.getLatitude());
        setLongtitude(parkingLot.getLongtitude());
        setCreateBy(parkingLot.getCreateBy());
        setManagedBy(parkingLot.getManagedBy());

    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
    }

    public int getResidualPosition() {
        return residualPosition;
    }

    public void setResidualPosition(int residualPosition) {
        this.residualPosition = residualPosition;
    }
}
