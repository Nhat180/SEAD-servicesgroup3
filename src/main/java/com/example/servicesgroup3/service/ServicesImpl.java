package com.example.servicesgroup3.service;

import com.example.servicesgroup3.model.Services;
import com.example.servicesgroup3.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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
    public Map<String, Object> getAllServicesByPage(String type ,int page, int size, String[] sort, String keyword) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<Services> servicesList = new ArrayList<>();
            List<Sort.Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }


            Pageable paging = PageRequest.of(page,size,Sort.by(orders));

            Page<Services> servicesPage;

            // paging based on the request of page and size from front-end
            if (type == null) {
                if(keyword == null || keyword.equals("")) {
                    servicesPage = servicesRepository.findAll(paging);
                } else {
                    servicesPage = servicesRepository.search(keyword,paging);
                }
                servicesList = servicesPage.getContent();
            } else {
                if (keyword == null || keyword.equals("")) {
                    servicesPage = servicesRepository.findAllByType(type, paging);
                    servicesList = servicesPage.getContent();
                } else {
                    servicesPage = servicesRepository.search(keyword,paging);
                    for (int i = 0; i < servicesPage.getContent().size(); i++) {
                        if (servicesPage.getContent().get(i).getType().equals(type)) {
                            servicesList.add(servicesPage.getContent().get(i));
                        }
                    }
                }
            }


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
        services1.setType(services.getType());
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

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

}
