package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot_order")
@GenericGenerator(name = "jpa-uuid",strategy = "uuid")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(generator = "jpa-uuid")
    private String orderId;

    @Column(name = "clerk_id")
    private String clerkId;

    @Column(name = "parking_lot_id")
    private String parkingLotId;

    @Column(name = "user_id",nullable = false)
    private String userId;

    @Column(name = "car_license",length = 128,nullable = false)
    private String carLicense;

    @Column(name = "created_time",nullable = false)
    private long createdTime;

    @Column(name = "end_time")
    private long endTime;

    private String strategyId;

    @Column(nullable = false)
    private int status;

    public Order() {
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

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
