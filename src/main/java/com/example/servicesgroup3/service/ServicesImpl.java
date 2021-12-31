package com.example.servicesgroup3.service;

import com.example.servicesgroup3.model.Services;
import com.example.servicesgroup3.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ServicesImpl {
    @Autowired
    private ServicesRepository servicesRepository;

    // Create a service, only for admin role
    public void createService (Services services) {
        servicesRepository.save(services);
    }

    // Get all service pagination,
    // int page is a current page
    // int size is a number of elements in a page
    public Map<String, Object> getAllServicesByPage(String type ,int page, int size) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<Services> servicesList = new ArrayList<>();
            Pageable paging = PageRequest.of(page,size);

            Page<Services> servicesPage;

            // paging based on the request of page and size from front-end
            if (type == null) {
                servicesPage = servicesRepository.findAll(paging);
            } else {
                servicesPage = servicesRepository.findAllByType(type, paging);
            }

            servicesList = servicesPage.getContent(); // Assign paging content to list and then return to UI

            res.put("services", servicesList);
            res.put("currentPage", servicesPage.getNumber());
            res.put("totalServices", servicesPage.getTotalElements());
            res.put("totalPages", servicesPage.getTotalPages());

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get the specific service
    public Services getServices (Long id) {
        Services services = new Services();
        try {
            services = this.servicesRepository.findById(id)
                    .orElseThrow(() -> new Exception("Service not found:: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }


    // update Service, only for admin
    public void updateService (Long id, Services services) {
        Services services1 = new Services();
        services1.setServiceId(id);
        services1.setName(services.getName());
        services1.setCost(services.getCost());
        services1.setRating(services.getRating());
        this.servicesRepository.save(services1);
    }


    // Delete service, only for admin
    public void deleteServices (Long id) {
        Services services = new Services();
        try {
            services = this.servicesRepository.findById(id)
                    .orElseThrow(() -> new Exception("Service not found:: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.servicesRepository.delete(services);

    }


}
