package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.ClerkStatusConfig.*;
import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.ClerkRoleConfig.*;

public class ClerkDTO {

    private String clerkId;

    private String phoneNumber;

    private String name;

    private String email;

    private String role;

    private String  status;

    private String managedBy;

    public ClerkDTO(Clerk clerk) {
        setClerkId(clerk.getClerkId());
        setEmail(clerk.getEmail());
        setManagedBy(clerk.getManagedBy());
        setName(clerk.getName());
        setPhoneNumber(clerk.getPhoneNumber());
        setRole(clerk.getRole());
        setStatus(clerk.getStatus());
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

    public String getRole() {
        return role;
    }

    public void setRole(int role) {
        switchRole(role);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(int status) {
        switchStatus(status);
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
    }

    private void switchStatus(int status){
        switch (status) {
            case FREEZE:
                this.status = FREEZE_STRING;
                break;
            case ACTIVE:
                this.status = ACTIVE_STRING;
                break;
        }
    }

    private void switchRole(int role) {
        switch (role) {
            case PARKINGBOY:
                this.role = PARKINGBOY_STRING;
                break;
            case PARKINGMANAGER:
                this.role = PARKINGMANAGER_STRING;
                break;
            case ADMIN:
                this.role = ADMIN_STRING;
                break;
        }
    }
}
