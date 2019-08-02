package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.OrderStatusConfig.*;

public class OrderDTO {


    public String orderId;


    private String clerkId;
    private String parkingLotId;

    private String userPhoneNumber;

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    private String parkingLotName;

    private String userId;

    private String carLicense;

    private long createdTime;

    private long endTime;

    private String strategyId;

    private String status;

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public OrderDTO(Order order) {
        setUserId(order.getUserId());
        setClerkId(order.getClerkId());
        setParkingLotId(order.getParkingLotId());
        setOrderId(order.getOrderId());
        setCarLicense(order.getCarLicense());
        setCreatedTime(order.getCreatedTime());
        setEndTime(order.getEndTime());
        setStrategyId(order.getStrategyId());
        setStatus(order.getStatus());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(int statusCode) {
        switch (statusCode) {
            case SNATCHING:
                this.status = SNATCHING_STRING;
                break;
            case RECEIPTED:
                this.status = RECEIPTED_STRING;
                break;
            case PARKED:
                this.status = PARKED_STRING;
                break;
            case FINISHED:
                this.status = FINISHED_STRING;
                break;
            case FETCHING:
                this.status = FECTHING_STRING;
                break;
            case ASK_FOR_FETCH:
                this.status = ASK_FOR_FETCH_STRING;
                break;
        }
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }
}
