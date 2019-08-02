package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user")
@GenericGenerator(name = "jpa-uuid",strategy = "uuid")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "jpa-uuid")
    private String userId;

    @Column(name = "phone_number",length = 16)
    private String phoneNumber;

    private String password;

    private String name;

    @Column(name = "car_license",length = 128,nullable = false)
    private String carLicense;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }
}
