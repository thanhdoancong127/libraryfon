package com.ns.book.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ns.book.entity.AuthorEntity;
import com.ns.book.repository.AuthorityRepository;
import com.ns.book.viewmodel.AuthoritiesGetVm;
import com.ns.book.viewmodel.AuthorityGetVm;
import com.ns.book.viewmodel.AuthorityPostVm;
import com.ns.book.viewmodel.AuthorityPutVm;
import com.ns.common.exception.NotFoundException;
import com.ns.common.util.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Value("${book.image.path}")
    private String bookImagePath;

    @Value("${book.image.public.path}")
    private String bookImagePublicPath;

    public AuthoritiesGetVm getAuthorities() {
        return new AuthoritiesGetVm(authorityRepository.findAll().stream().map(AuthorityGetVm::fromEntity).collect(Collectors.toList()));
    }

    public AuthorityGetVm getAuthority(Long id) {
        return authorityRepository.findById(id).map(AuthorityGetVm::fromEntity)
            .orElseThrow(() -> new NotFoundException("AUTHORITY_NOT_FOUND", id.toString()));
    }

    public AuthorityGetVm createAuthority(AuthorityPostVm authorityPostVm) {
        // Handle image upload, save to disk, and get the image URL
        String imageUrl = postAuthorityImagePath(authorityPostVm.image());
        return AuthorityGetVm.fromEntity(authorityRepository.save(authorityPostVm.toEntity(imageUrl)));
    }

    public void updateAuthority(AuthorityPutVm authorityPutVm) {
        AuthorEntity authority = authorityRepository.findById(authorityPutVm.id())
            .orElseThrow(() -> new NotFoundException("AUTHORITY_NOT_FOUND", authorityPutVm.id().toString()));

        authority.setBio(authorityPutVm.bio());
        authority.setDescription(authorityPutVm.description());
        authority.setEmail(authorityPutVm.email());
        authority.setName(authorityPutVm.name());
        if(authorityPutVm.image() != null && authorityPutVm.image().getSize() > 0){
            String imageUrl = postAuthorityImagePath(authorityPutVm.image());
            authority.setImageUrl(imageUrl);
        }
        authorityRepository.save(authority);
    }

    private String postAuthorityImagePath(MultipartFile image) {
        String fileName = FileUtils.storeImageToResource(bookImagePath, image);
        return bookImagePublicPath + fileName;
    }

    public AuthoritiesGetVm getAuthorityByName(String authorName) {
        return new AuthoritiesGetVm(authorityRepository.findByNameContainingIgnoreCase(authorName).stream().map(AuthorityGetVm::fromEntity).collect(Collectors.toList()));
    }
}
