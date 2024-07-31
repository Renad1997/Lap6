package com.example.lap6.Controller;

import com.example.lap6.Api.ApiResponse;
import com.example.lap6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/Employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<Employee>();

 @GetMapping("/get")
 public ResponseEntity getEmployees() {
     return ResponseEntity.status(200).body(employees);
 }
@PostMapping("/add")
 public ResponseEntity addEmployee(@Valid @RequestBody Employee employee , Errors errors) {
     if(errors.hasErrors()){
         String message=errors.getFieldError().getDefaultMessage();
         return ResponseEntity.status(400).body(employee);
     }
     employees.add(employee);
     return ResponseEntity.status(200).body(new ApiResponse("Employee Added successfully"));
}
@PutMapping("/update/{index}")
public ResponseEntity updateEmployee(@PathVariable int index ,@Valid @RequestBody Employee employee , Errors errors) {
     if(errors.hasErrors()){
         String message=errors.getFieldError().getDefaultMessage();
         return ResponseEntity.status(400).body(message);
     }
     employees.set(index,employee);
     return ResponseEntity.status(200).body(new ApiResponse("Employee Updated successfully"));
}
@DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id) {
     for(Employee employee : employees){
         if(employee.getId() == id){
             employees.remove(id);
             return ResponseEntity.status(200).body(new ApiResponse("Employee Deleted successfully"));
         }
     }
     return ResponseEntity.status(404).body(new ApiResponse("Employee Not Found"));

}

@GetMapping("/get/search/{position}")
public ResponseEntity SearchEmployeesPosition(@PathVariable String position){
     ArrayList<Employee> empPosition = new ArrayList<>();
     for(Employee employee : employees){
         if(employee.getPosition().equalsIgnoreCase(position)){
             empPosition.add(employee);
         }
     }
     if(empPosition.isEmpty()){
         return ResponseEntity.status(404).body(new ApiResponse("Employee Position Not Found"));
     }if(empPosition.equals("supervisor")|| empPosition.equals("coordinator")){
        return ResponseEntity.status(200).body(empPosition);
    }
     return ResponseEntity.status(404).body(new ApiResponse("Employee Position should be supervisor or coordinator "));
     }

@GetMapping("/Age/{min}/{max}")
 public ResponseEntity getEmployeesAgeRange(@PathVariable int min ,@PathVariable int max){
     ArrayList<Employee> empAgeRange = new ArrayList<>();
     for(Employee employee : employees){
         if(employee.getAge() >= min && employee.getAge() <= max){
             empAgeRange.add(employee);
         }
     }
     if(empAgeRange.isEmpty()){
         return ResponseEntity.status(404).body(new ApiResponse("Employee Age Range Not Found"));

     }
     return ResponseEntity.status(200).body(empAgeRange);
 }
@PostMapping("/Annual/{id}")
 public ResponseEntity ApplyAnnualLeave(@PathVariable int id){

     for(Employee employee : employees){
         if(employee.getId()==id) {
             if(employee.getOnLeave().equals(false)){
                 if(employee.getAnnualLeave()>=1){
                     employee.setOnLeave(true);
                     employee.setAnnualLeave(employee.getAnnualLeave()-1);
                     return ResponseEntity.status(200).body("Employee Annual Leave Successfully");
                 }
             }
         }
         }
     return ResponseEntity.status(404).body(new ApiResponse("Employee Not Found"));
     }

  @GetMapping("/no/annual")
 public ResponseEntity EmployeesNoAnnualLeave(){
     ArrayList<Employee> empNoAnnualLeave = new ArrayList<>();
     for(Employee employee : employees){
         if(employee.getAnnualLeave() == 0){
             empNoAnnualLeave.add(employee);

             }
         }
     if(empNoAnnualLeave.isEmpty()){
      return ResponseEntity.status(400).body(new ApiResponse("No Employees used their annual leave"));}
      return ResponseEntity.status(200).body(empNoAnnualLeave);
     }

@PutMapping("/promote/{supervisorId}/{coordinatorId}")
public ResponseEntity PromoteEmployee(@PathVariable int supervisorId, @PathVariable int coordinatorId) {
    for (Employee employee : employees) {
        if (employee.getId() == supervisorId) {
            if (employee.getPosition().equalsIgnoreCase("supervisor")) {
                for (Employee emp : employees) {
                    if (emp.getId() == coordinatorId) {
                        if (emp.getAge() >= 30) {
                            if (employee.getOnLeave().equals(false)) {
                                emp.setPosition("supervisor");
                                return ResponseEntity.status(200).body("Employee Promote Successfully");
                            }
                        }
                    }
                }
            }
        }
    }

    return ResponseEntity.status(400).body(new ApiResponse("Employee Id Not Found"));
}

 }

























