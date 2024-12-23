package com.example.userService.service.impl;

import com.example.common.entity.User;
import com.example.common.enums.Active;
import com.example.common.enums.Role;
import com.example.common.model.UserDetails;
import com.example.common.repository.UserRepository;
import com.example.userService.client.FileStorageClient;
import com.example.userService.model.RegisterRequest;
import com.example.userService.model.UpdateUserRequest;
import com.example.userService.service.UserService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.userService.constants.Constant.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final FileStorageClient fileStorageClient;

    @Override
    public User saveUser(RegisterRequest request) {
        User toSave = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .active(Active.ACTIVE)
                .build();
        return userRepository.save(toSave);
    }

    @Override
    public User getUserById(String id) {
        return findUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return findUserByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return findUserByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAllByActive(Active.ACTIVE);
    }

    @Override
    public User updateUserById(UpdateUserRequest userRequest, MultipartFile file) {
        User userToUpdate = findUserById(userRequest.getId());

        // set all required user details in the request
        userRequest.setUserDetails(updateUserDetails(userRequest.getUserDetails(), userToUpdate.getUserDetails(), file));

        // then map to the object to be saved
        modelMapper.map(userRequest, userToUpdate);

        return userRepository.save(userToUpdate);
    }

    private User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    private User findUserByUsername (String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    private UserDetails updateUserDetails(UserDetails userRequest, UserDetails userToUpdate, MultipartFile file) {
        userToUpdate = userToUpdate == null ? new UserDetails() : userToUpdate;

        if (file != null) {
            String profilePicture = fileStorageClient.uploadImageToFileSystem(file).getBody();

            // if file upload successful
            if (profilePicture != null) {
                // delete existing image for the user
                fileStorageClient.deleteImageFromFileSystem(userToUpdate.getProfilePicture());

                // update the profile of the user with the newly uploaded picture
                userToUpdate.setProfilePicture(profilePicture);
            }
        }

        return userToUpdate;
    }
}
