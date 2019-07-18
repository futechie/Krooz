package com.example.demo.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.model.Constants;
import com.example.demo.model.UserDTO;

@RestController
public class SignupController {

	@Autowired
	UserService userService;
	
	@Autowired
	private TokenStore tokenStore;
	
	@RequestMapping(value="/signup/enrollUser", method=RequestMethod.POST)
	@ResponseBody
	public Constants createUser(@RequestBody UserDTO user) {
		return userService.createUser(user);
	}
		
    @RequestMapping(value = "/logout/revoke-token", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<HttpStatus> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            try {
                String tokenValue = authHeader.replace("bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                System.out.println(accessToken.getTokenType());
                tokenStore.removeAccessToken(accessToken);
                tokenStore.readAccessToken(tokenValue);
                System.out.println(tokenStore.toString());
            } catch (Exception e) {
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }           
        }
        
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
	
}

