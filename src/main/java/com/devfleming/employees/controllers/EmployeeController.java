package com.devfleming.employees.controllers;

import com.devfleming.employees.domain.dto.EmployeeDto;
import com.devfleming.employees.domain.dto.ResponseDto;
import com.devfleming.employees.domain.entities.Employee;
import com.devfleming.employees.domain.usecases.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.Show;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devfleming.employees.constants.EmployeeConstants.*;

/**
 * Employee Controller, responsible for expose endpoints to the outside and manage CRUD operations related to employee's entity.
 * This controller handle HTTP requests to create, read, update and inactivate the employee entity.
 * @author Rafael Fleming
 */
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@ControllerAdvice
@Tag(
        name = "REST API responsible for CRUD operations",
        description = "This endpoint contains Create, read and update operations."
)
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Request the creation of a new employee.
     * @param employeeDto Employee DTO containing the employee information required to create a new employee.
     * @return Entity response with status "201 CREATED", and a response DTO containing the status code and a message
     * saying "Employee created successfully"
     */
    @Operation(summary = "Create new employee", description = "How to register a new employee using the create endpoint.")
    @ApiResponse(responseCode = "201", description = "Employee created successfully")
    @PostMapping("/employee/create-employee")
    public ResponseEntity<ResponseDto> createNewEmployee(@RequestBody EmployeeDto employeeDto){
        employeeService.createNewEmployee(employeeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    /**
     * Request the update of a saved employee.
     * @param employeeId The ID of the employee saved in database.
     * @param employeeDto Employee DTO containing the employee information required to update employee saved.
     * @return Entity response with status "200 OK", and a response DTO containing the status code and a message
     *      * saying "Request processed successfully"
     */
    @Operation(summary = "Update employee", description = "How to update an employee entity using the update endpoint.")
    @ApiResponse(responseCode = "200", description = "Request processed successfully")
    @PutMapping("/employee/profile/{employeeId}")
    public ResponseEntity<ResponseDto> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employeeDto){
        employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200, MESSAGE_200));
    }

    /**
     * Fetch a single employee with its ID.
     * @param employeeId The ID of the employee saved in database.
     * @return Response with the employee saved, and the Http Status 200.
     */
    @Operation(summary = "Fetch employee by ID", description = "How to fetch an employee using the fetch endpoint.")
    @GetMapping("/employee")
    public ResponseEntity<Employee> fetchEmployeeById(@RequestParam Long employeeId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.fetchEmployeeById(employeeId));
    }

    /**
     * Fetch a single employee with its email.
     * @param email The email of the employee saved in database.
     * @return Response with the employee saved, and the Http Status 200.
     */
    @Operation(summary = "Fetch employee by e-mail", description = "How to fetch an employee using the fetch endpoint.")
    @GetMapping("/employee/email")
    public ResponseEntity<Employee> fetchEmployeeByEmail(@RequestParam String email){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.fetchEmployeeByEmail(email));
    }

    /**
     * Fetch a single employee with its cellphone.
     * @param cellphone The cellphone of the employee saved in database.
     * @return Response with the employee saved, and the Http Status 200.
     */
    @Operation(summary = "Fetch employee by cellphone", description = "How to fetch an employee using the fetch endpoint.")
    @GetMapping("/employee/cellphone")
    public ResponseEntity<Employee> fetchEmployeeByCellphone(@RequestParam String cellphone){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.fetchEmployeeByCellphone(cellphone));
    }

    /**
     * Fetch all employees saved in database with no filters.
     * @return List of all employees, and the Http Status 200.
     */
    @Operation(summary = "Fetch employees list", description = "How to fetch employees list using the fetch endpoint.")
    @GetMapping("/employee/list")
    public ResponseEntity<List<Employee>> fetchEmployeesList(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.fetchEmployeesList());
    }

    /**
     * Inactivate an employee, changing its active status.
     * @param employeeId The ID of the employee saved in database.
     * @return Response with the Http Status 200 and a 200 Message ("Request processed successfully")
     */
    @Operation(summary = "Inactivate employee", description = "How to inactivate an employee using the inactivate endpoint.")
    @ApiResponse(responseCode = "200", description = "Request processed successfully")
    @PostMapping("/employee/inactivate")
    public ResponseEntity<ResponseDto> inactivateEmployee(@RequestParam Long employeeId){
        employeeService.inactivateEmployee(employeeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200,MESSAGE_200));
    }
}
