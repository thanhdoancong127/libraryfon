package com.ns.book.viewmodel;

import org.springframework.web.multipart.MultipartFile;

public record AuthorityPutVm(
    Long id,
    String name,
    String email,
    String description,
    String bio,
    MultipartFile image
) {

}
