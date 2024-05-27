package com.ns.book.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import com.ns.book.service.AuthorityService;
import com.ns.book.viewmodel.AuthoritiesGetVm;
import com.ns.book.viewmodel.AuthorityGetVm;
import com.ns.book.viewmodel.AuthorityPostVm;
import com.ns.book.viewmodel.AuthorityPutVm;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthoritiesGetVm index() {
        return authorityService.getAuthorities();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorityGetVm getAuthorityById(@PathVariable Long id) {
        return authorityService.getAuthority(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorityGetVm createAuthority(AuthorityPostVm authorityPostVm) {
        return authorityService.createAuthority(authorityPostVm);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthority(AuthorityPutVm authorityPutVm) {
        authorityService.updateAuthority(authorityPutVm);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public AuthoritiesGetVm getAuthorityByName(@RequestParam String authorName) {
        return authorityService.getAuthorityByName(authorName);
    }
}
