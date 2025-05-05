//package org.example.netwave.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.example.netwave.dto.ApiResponse;
//import org.example.netwave.dto.PackageActivationRequest;
//import org.example.netwave.entity.ActivePackage;
//import org.example.netwave.service.ActivePackageService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//        import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/packages")
//@RequiredArgsConstructor
//public class ActivePackageController {
//
//    private final ActivePackageService activePackageService;
//
//    @PostMapping("/activate")
//    public ResponseEntity<ApiResponse<ActivePackage>> activatePackage(@RequestBody PackageActivationRequest request) {
//        try {
//            ActivePackage activatedPackage = activePackageService.activatePackage(request);
//
//            return ResponseEntity.ok(new ApiResponse<>(
//                    true,
//                    "Package activated successfully",
//                    activatedPackage
//            ));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(
//                            false,
//                            "Failed to activate package: " + e.getMessage(),
//                            null
//                    ));
//        }
//    }
//
//    @GetMapping("/active/{phoneNumber}")
//    public ResponseEntity<ApiResponse<List<ActivePackage>>> getActivePackages(@PathVariable String phoneNumber) {
//        try {
//            List<ActivePackage> activePackages = activePackageService.getActivePackagesByPhoneNumber(phoneNumber);
//
//            return ResponseEntity.ok(new ApiResponse<>(
//                    true,
//                    "Active packages retrieved successfully",
//                    activePackages
//            ));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(
//                            false,
//                            "Failed to retrieve active packages: " + e.getMessage(),
//                            null
//                    ));
//        }
//    }
//
//    @GetMapping("/history/{phoneNumber}")
//    public ResponseEntity<ApiResponse<List<ActivePackage>>> getPackageHistory(@PathVariable String phoneNumber) {
//        try {
//            List<ActivePackage> packageHistory = activePackageService.getAllPackagesByPhoneNumber(phoneNumber);
//
//            return ResponseEntity.ok(new ApiResponse<>(
//                    true,
//                    "Package history retrieved successfully",
//                    packageHistory
//            ));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(
//                            false,
//                            "Failed to retrieve package history: " + e.getMessage(),
//                            null
//                    ));
//        }
//    }
//}
package org.example.netwave.controller;

import lombok.RequiredArgsConstructor;
import org.example.netwave.dto.ApiResponse;
import org.example.netwave.dto.PackageActivationRequest;
import org.example.netwave.entity.ActivePackage;
import org.example.netwave.service.ActivePackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/packages")
@RequiredArgsConstructor
public class ActivePackageController {

    private final ActivePackageService activePackageService;

    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<ActivePackage>> activatePackage(@RequestBody PackageActivationRequest request) {
        try {
            ActivePackage activatedPackage = activePackageService.activatePackage(request);

            ApiResponse<ActivePackage> response = new ApiResponse<>(
                    true,
                    "Package activated successfully",
                    activatedPackage
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<ActivePackage> response = new ApiResponse<>(
                    false,
                    "Failed to activate package: " + e.getMessage(),
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active/{phoneNumber}")
    public ResponseEntity<ApiResponse<List<ActivePackage>>> getActivePackages(@PathVariable String phoneNumber) {
        try {
            List<ActivePackage> activePackages = activePackageService.getActivePackagesByPhoneNumber(phoneNumber);

            ApiResponse<List<ActivePackage>> response = new ApiResponse<>(
                    true,
                    "Active packages retrieved successfully",
                    activePackages
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<ActivePackage>> response = new ApiResponse<>(
                    false,
                    "Failed to retrieve active packages: " + e.getMessage(),
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history/{phoneNumber}")
    public ResponseEntity<ApiResponse<List<ActivePackage>>> getPackageHistory(@PathVariable String phoneNumber) {
        try {
            List<ActivePackage> packageHistory = activePackageService.getAllPackagesByPhoneNumber(phoneNumber);

            ApiResponse<List<ActivePackage>> response = new ApiResponse<>(
                    true,
                    "Package history retrieved successfully",
                    packageHistory
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<ActivePackage>> response = new ApiResponse<>(
                    false,
                    "Failed to retrieve package history: " + e.getMessage(),
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}