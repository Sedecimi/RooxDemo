package com.example.demo.Controller;


import com.example.demo.Entity.CustomerEntity;
import com.example.demo.Exception.UnauthorizationException;
import com.example.demo.Repository.CustomerRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.demo.Constants.Constants.AUTHORIZATION_FAIL_MESSAGE;
import static com.example.demo.Constants.Constants.AUTHORIZATION_LITERAL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * RestController for customer REST mapping
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Read Customer
     * @param request HttpRequest from Servlet
     * @param id for customer we need
     * @return customerEntity by id
     */
    @SneakyThrows
    @GetMapping("/customers/{customerId}/")
    public CustomerEntity getCustomer(HttpServletRequest request,
                                      @RequestParam(value = "customerId") String id) throws Exception {

        //Read current authorized user.
        if(request.getHeader(AUTHORIZATION) == null ||
                request.getHeader(AUTHORIZATION).length() <= AUTHORIZATION_LITERAL.length())
            throw new UnauthorizationException(AUTHORIZATION_FAIL_MESSAGE);
        String userID =  request.getHeader(AUTHORIZATION).substring(AUTHORIZATION_LITERAL.length());

        //Check which id we use for search anf check access.
        if(id.equals("@me") || userID.equals(id)) {
            return customerRepository.findById(Long.valueOf(userID)).get();
        } else {
            throw new UnauthorizationException(AUTHORIZATION_FAIL_MESSAGE);
        }
    }
}
