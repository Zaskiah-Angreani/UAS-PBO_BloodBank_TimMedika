package org.bloodblank.donordarahapi.controller;

import org.bloodblank.donordarahapi.entity.Request;
import org.bloodblank.donordarahapi.service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/dummy")
    public Request createDummyRequest() {

        Request request = new Request();

        request.setPasien("Budi");
        request.setGolDarah("A");
        request.setKantong(2);
        request.setRumahSakit("RS Adam Malik");
        request.setStatus("PENDING");
        request.setTanggal("2026-06-13");
        request.setDetail("Operasi");

        return requestService.save(request);
    }
}