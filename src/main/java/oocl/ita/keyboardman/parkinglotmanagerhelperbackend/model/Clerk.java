package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "clerk")
@GenericGenerator(name = "jpa-uuid",strategy = "uuid")
public class Clerk {
    @Id
    @Column(name = "clerk_id")
    @GeneratedValue(generator = "jpa-uuid")
    private String clerkId;

    @Column(name = "phone_number",length = 16,nullable = false)
    private String phoneNumber;

    @Column()
    private String password;

    private String name;

    private String email;

    @Column(nullable = false)
    private int role;

    @Column(nullable = false)
    private int status;

    @Column(name = "managed_by")
    private String managedBy;

    public Clerk() {
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
    }
}
