package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import com.itmuch.lightsecurity.annotation.PreAuthorize;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ClerkDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ClerkService;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ClerkServiceImpl;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ClerkController {
    @Autowired
    private ClerkService clerkService;

    @PostMapping("/clerks")
    public Clerk register(@RequestBody Clerk newUser) {
        return clerkService.register(newUser);
    }

  @PatchMapping("/clerks/{clerkId}")
    public ClerkDTO updateClerkStatus(@PathVariable String clerkId) throws Exception{
        return clerkService.updateClerkStatus(clerkId);
    }

    @PreAuthorize("hasAllRoles('manager')")
    @GetMapping("/clerks")
    public List<ClerkDTO> findAll() {
        return clerkService.fingALl();
    }

    @PreAuthorize("hasAnyRoles('clerk','manager')")
    @GetMapping("/managers")
    public List<ClerkDTO> findClerkByManagedBy() {
        return clerkService.findClerkByManagedBy();
    }

    @PreAuthorize("hasAnyRoles('admin','manager')")
    @GetMapping(value = "/managers", params = {"name"})
    public List<ClerkDTO> findClerksByClerkName(@RequestParam String name) {
        return clerkService.findClerksByClerkName(name);
    }

    @PreAuthorize("hasRole('clerk')")
    @GetMapping(value = "/clerks", params = {"clerkId"})
    public ClerkDTO findClerkByClerkId(@RequestParam String clerkId) {
        return clerkService.findClerkByClerkId(clerkId);
    }

    @PreAuthorize("hasAnyRoles('admin','manager')")
    @GetMapping(value = "/managers", params = {"phoneNumber"})
    public ClerkDTO findClerkByClerkPhoneNumber(@RequestParam String phoneNumber) {
        return clerkService.findClerkByClerkPhoneNumber(phoneNumber);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/clerks", params = {"name", "role"})
    public List<ClerkDTO> findClerkByNameAndRole(@RequestParam String name, @RequestParam int role) {
        return clerkService.findClerkByNameAndRole(name, role);
    }

    @PreAuthorize("hasRole('admin')")
    @PatchMapping(value = "/admin/clerks/{clerksId}")
    public ClerkDTO chmodClerk(@PathVariable String clerksId,@RequestBody Clerk clerk){
        return clerkService.chomdClerk(clerksId,clerk);
    }

    @PreAuthorize("hasRole('manager')")
    @GetMapping(value = "/managers",params = {"phoneNumber","name"})
    public ClerkDTO findClerkByPhoneNumberAndName(@RequestParam String phoneNumber,@RequestParam String name){
        return clerkService.findClerkByPhoneNumberAndName(phoneNumber,name);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/clerks/unassignedParkingBoys")
    public List<ClerkDTO> findParkingBoyWithoutManagedBy(){
        return clerkService.findParkingBoyWithoutManagedBy();
    }

    @PreAuthorize("hasAnyRoles('admin')")
    @GetMapping("/clerks/managers")
    public List<ClerkDTO> findAllManagers(){
        return clerkService.findAllManagers();
    }

    @PreAuthorize("hasAnyRoles('admin')")
    @PatchMapping("/clerks")
    public String batchUpdateClerk(@RequestBody List<Clerk> clerks){
        return clerkService.batchSaveClerks(clerks);
    }

    @PreAuthorize("hasAnyRoles('admin')")
    @GetMapping(value = "/clerks",params = {"managedBy"})
    public List<ClerkDTO> filterClerksByManagedBy(@RequestParam String managedBy){
        return clerkService.filterClerksByManagedBy(managedBy);
    }
}

