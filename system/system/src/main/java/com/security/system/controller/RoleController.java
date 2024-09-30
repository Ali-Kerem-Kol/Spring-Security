package com.security.system.controller;

import com.security.system.dto.RoleCreateRequest;
import com.security.system.dto.RoleUpdateRequest;
import com.security.system.exception.AlreadyExistsException;
import com.security.system.exception.ResourceNotFoundException;
import com.security.system.model.Role;
import com.security.system.response.ApiResponse;
import com.security.system.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // CRUD - C
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createRole(RoleCreateRequest request) {
        try {
            Role role =roleService.createRole(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Role created successfully.",role));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    // CRUD - R
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Roles found.",roles));
    }

    @GetMapping("/role/id")
    public ResponseEntity<ApiResponse> getRoleById(@RequestParam Long roleId) {
        try {
            Role role = roleService.getRoleById(roleId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Role found.",role));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/role/name")
    public ResponseEntity<ApiResponse> getRoleByName(@RequestParam String name) {
        try {
            Role role = roleService.getRoleByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Role found.",role));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // CRUD - U
    @PutMapping("/role/update/{roleId}")
    public ResponseEntity<ApiResponse> updateRole(@RequestBody RoleUpdateRequest request, @PathVariable Long roleId) {
        try {
            Role role = roleService.updateRole(request, roleId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Role updated successfully.",role));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // CRUD - D
    @DeleteMapping("/role/delete/{roleId}")
    public ResponseEntity<ApiResponse> deleteRoleById(@PathVariable Long roleId) {
        try {
            roleService.deleteRoleById(roleId);
            return ResponseEntity.ok(new ApiResponse("Role deleted successfully.", roleId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Other functions



}
