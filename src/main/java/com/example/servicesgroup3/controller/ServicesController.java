package com.example.servicesgroup3.controller;

import com.example.servicesgroup3.model.Services;
import com.example.servicesgroup3.service.ServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/service")
public class ServicesController {
    @Autowired
    private ServicesImpl servicesImpl;

    @PostMapping
    public void createService(@RequestBody Services services) {
        servicesImpl.createService(services);
    }

    @PutMapping("/{id}")
    public void updateService(@PathVariable(value = "id") Long id, @RequestBody Services services) {
        servicesImpl.updateService(id, services);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable(value = "id") Long id) {
        servicesImpl.deleteServices(id);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllServicesByPage (
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "serviceId,asc") String[] sort
    ) {
        return new ResponseEntity<>(servicesImpl.getAllServicesByPage(type,page,size,sort), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchService (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam String keyword
    ) {
        return new ResponseEntity<>(servicesImpl.searchService(page,size,keyword), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Services getService (@PathVariable(value = "id") Long id) {
        return servicesImpl.getServices(id);
    }

}
