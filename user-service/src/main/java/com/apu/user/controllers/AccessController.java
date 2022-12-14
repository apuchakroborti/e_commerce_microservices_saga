package com.apu.user.controllers;

import com.apu.user.dto.AuthorityModel;
import com.apu.user.exceptions.GenericException;
import com.apu.user.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class AccessController {
    @Autowired
    AuthorityService authorityService;

    /*
     * This API is for adding new authority
     * name must ne given
     */
    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/addNewRoll")
    public AuthorityModel addNewAuthority(@Valid @RequestBody AuthorityModel authorityModel) throws GenericException {
        return authorityService.addNewAuthority(authorityModel);
    }
}
