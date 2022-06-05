package com.application.bekend.controller;
import com.application.bekend.DTO.ActivationDTO;
import com.application.bekend.DTO.MyUserDTO;
import com.application.bekend.DTO.UserTokenStateDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.VerificationToken;
import com.application.bekend.security.TokenUtils;
import com.application.bekend.security.auth.JwtAuthenticationRequest;
import com.application.bekend.service.AuthService;
import com.application.bekend.service.MyUserService;
import com.application.bekend.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.Timestamp;


@RestController
@RequestMapping("api/identity")
public class AuthentificationController {

    private final AuthService authService;
    private final MyUserService myUserService;
    private final VerificationTokenService verificationTokenService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @Autowired
    public AuthentificationController(AuthService authService, MyUserService myUserService, VerificationTokenService verificationTokenService, AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
        this.authService = authService;
        this.myUserService = myUserService;
        this.verificationTokenService = verificationTokenService;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest){

        MyUser loggedInUser = this.myUserService.findUserByEmailorUsername(authenticationRequest.getEmail(), "");
        
        if(loggedInUser.isDeleted()) {
        	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loggedInUser.getUsername(),
                            authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUser user  = (MyUser) authentication.getPrincipal();


            String jwt = tokenUtils.generateToken(user);
            long expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody MyUserDTO myUserDTO){
        MyUser user = this.authService.findMyUserByEmailOrUsername(myUserDTO.getEmail(), myUserDTO.getUsername());
        if(user != null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // ukoliko ne postoji korisnik sa istim email-om ili username-om, idemo na registraciju
        this.authService.register(myUserDTO);

        return new ResponseEntity<>(true,HttpStatus.CREATED);
    }

    @PostMapping("/email/verification")
    public ResponseEntity indentifyUser(@RequestBody ActivationDTO activationDTO) throws UserPrincipalNotFoundException {
        MyUser user = this.myUserService.findUserById(activationDTO.getUserId());
        if(user == null){
            throw new UserPrincipalNotFoundException("User not found");
        }

        // proveravamo da nije istekao expiru date
        VerificationToken verificationToken = this.verificationTokenService.findByToken(activationDTO.getToken());

        if(verificationToken.getUser().getEmail().equals(user.getEmail())){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if(verificationToken.getExpiryDate().after(timestamp)){
                this.myUserService.activateUser(user.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
