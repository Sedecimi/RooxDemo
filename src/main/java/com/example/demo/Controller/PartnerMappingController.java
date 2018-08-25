package com.example.demo.Controller;


import com.example.demo.Entity.PartnerMappingEntity;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Exception.UnauthorizationException;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.PartnerMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.example.demo.Constants.Constants.AUTHORIZATION_FAIL_MESSAGE;
import static com.example.demo.Constants.Constants.AUTHORIZATION_LITERAL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * RestController for partner mapping
 */
@RestController
public class PartnerMappingController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PartnerMappingRepository partnerMappingRepository;

    /**
     * CRUD Create operation for partnerMapping
     * @return PartnerMappingEntity we created
     */

    @PostMapping("/customers/{customerId}/partners")
    public PartnerMappingEntity createPartnerMapping(HttpServletRequest request,
                                                     @PathVariable(value = "customerId") String id,
                                                     @Valid  @RequestBody PartnerMappingEntity partnerMappingEntity) throws Exception {
        checkAuthorizationToken(request,id);
        return customerRepository.findById(Long.valueOf(id)).map(customerEntity -> {
                    partnerMappingEntity.setCustomer(customerEntity);
                    return partnerMappingRepository.save(partnerMappingEntity);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

    }

    /**
     * CRUD Read operation for partnerMapping
     * @param request HttpRequest for getting headers
     * @param id  customerID for creation
     * @return PartnerMappingEntitys by CustomerId
     */
    @GetMapping("/customers/{customerId}/partners")
    public Page<PartnerMappingEntity> readPartnerMapping(HttpServletRequest request,
                                                         @PathVariable(value = "customerId") String id,
                                                         Pageable pageable) throws Exception {
        checkAuthorizationToken(request, id);
        String userID =  request.getHeader(AUTHORIZATION).substring(AUTHORIZATION_LITERAL.length());
        return partnerMappingRepository.findByCustomerId(Long.valueOf(userID), pageable);
    }


    /**
     * CRUD Update operation for partnerMapping
     * @return PartnerMappingEntity which was update or error
     */
    @PutMapping("/customers/{customerId}/partners/{partnerId}")
    public  PartnerMappingEntity updatePartnerMappingEntity(HttpServletRequest request,
                                                            @PathVariable(value = "customerId") String id,
                                                            @PathVariable(value = "partnerId") Long partnerId,
                                                            @Valid  @RequestBody PartnerMappingEntity partnerMappingEntity ) throws Exception {
        checkAuthorizationToken(request, id);
        if(!customerRepository.existsById(Long.valueOf(id))) {
            throw new ResourceNotFoundException("Customer with id " + id + " not found");
        }

        return partnerMappingRepository.findById(partnerId)
                .map(partner ->
                    partnerMappingRepository.save(partnerMappingEntity))
                .orElseThrow(() -> new ResourceNotFoundException("Partner with id " + partnerId + " not found"));
    }

    /**
     * CRUD Delete operation for partnerMapping
     * @return success or fail
     */
    @DeleteMapping("/customers/{customerId}/partners/{partnerId}")
    public ResponseEntity<?> deletePartnerMapping(HttpServletRequest request,
                                                  @PathVariable(value = "customerId") String id,
                                                  @PathVariable(value = "partnerId") Long partnerId
                                                ) throws Exception {
       checkAuthorizationToken(request, id);

       if(!partnerMappingRepository.existsById(partnerId))
           throw new ResourceNotFoundException("PartnerId " + partnerId + " not found");


       return partnerMappingRepository.findById(partnerId).map(partnerMapping -> {
           partnerMappingRepository.delete(partnerMapping);
           return ResponseEntity.ok().build();
       }).orElseThrow(() -> new ResourceNotFoundException("PartnerId " + partnerId + " not found"));
    }


    /**
     * Check authorization and access.
     * @param request HttpRequest with authorization header
     * @param id customerID
     * @throws Exception of fail authorization
     */
    private void checkAuthorizationToken(HttpServletRequest request, String id) throws Exception {
        if(request.getHeader(AUTHORIZATION) == null ||
                request.getHeader(AUTHORIZATION).length() <= AUTHORIZATION_LITERAL.length() ||
                !id.equals(request.getHeader(AUTHORIZATION).substring(AUTHORIZATION_LITERAL.length())))
            throw new UnauthorizationException(AUTHORIZATION_FAIL_MESSAGE);
    }
}
