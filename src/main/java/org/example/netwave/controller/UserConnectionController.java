package org.example.netwave.controller;

import org.example.netwave.service.ConnectionService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/connections")
public class UserConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @GetMapping("/getAll")
        public ResponseUtil getAllContacts() {
            return new ResponseUtil(200, "Success",
                    connectionService.getAllContacts());
        }
}

