package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.ProfileDTO;
import com.br.ccbrec.entities.ProfileSpecification;
import com.br.ccbrec.enums.RoleName;
import com.br.ccbrec.repositories.ProfileSpecificationRepository;
import com.br.ccbrec.repositories.UsersRepository;
import com.br.ccbrec.util.SplitedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;

@Service
public class ProfileService implements IService {
    @Autowired
    private ProfileSpecificationRepository repository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private AuthService authService;

    @Override
    public DTO add(DTO dto) {
        ProfileDTO newDTO = (ProfileDTO) dto;
        MultipartFile file = (MultipartFile) newDTO.getImage();
        String filename = String.format("%s_profile", ((ProfileDTO) dto).getUsername());

        String preSignedUrl = s3Service.uploadFile(file, filename);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileSpecification spec = this.repository.getByUsername(username);

        if (spec == null) {
            spec = new ProfileSpecification();
            spec.setUser(this.usersRepository.findByUsername(username));
            spec.setPhotoExpiresIn(604800);
        }

        spec.setProfilePhotoUrl(preSignedUrl);
        spec.setPhotoUrlGeneratedAt(Calendar.getInstance());
        spec.setProfilePhotoImageName(file.getOriginalFilename());
        this.repository.save(spec);

        return dto;
    }

    @Override
    public void delete(SplitedDate splitedDate) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void update(DTO dto) {
        throw new RuntimeException("Not Implemented");
    }

    public ProfileDTO getProfileLogged(SecurityContext context) {
        String username = context.getAuthentication().getName();
        RoleName role = authService.getMostPrivileges(context);

        ProfileSpecification spec = this.repository.getByUsername(username);
        String photo = "";

        if (spec != null) {
            Calendar urlCreatedAt = spec.getPhotoUrlGeneratedAt();
            int seconds = spec.getPhotoExpiresIn();
            Calendar expiredAt = (Calendar) urlCreatedAt.clone();
            expiredAt.add(Calendar.SECOND, seconds);

            if (Calendar.getInstance().after(expiredAt)) {
                spec.setProfilePhotoUrl(this.s3Service.getPresignedUrlByKey(spec.getProfilePhotoImageName()));
            }

            photo = spec.getProfilePhotoUrl();
        }
        return new ProfileDTO(username, role.getValue(), photo);
    }

}
