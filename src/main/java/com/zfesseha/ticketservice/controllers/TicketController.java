package com.zfesseha.ticketservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    @RequestMapping(method = RequestMethod.GET, value = "/status")
    public String status() {
        return "Ticket Service is running!";
    }
}
