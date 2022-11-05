package com.example.yourhealth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.yourhealth.entity.User;
import com.example.yourhealth.repository.UserRepository;


@Configuration
public class FormAuthenticationProvider implements AuthenticationProvider {

    protected static Logger log = LoggerFactory.getLogger(FormAuthenticationProvider.class);

    @Autowired
    private UserRepository repository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String name = auth.getName();
        String password = auth.getCredentials().toString();

        log.debug("name={}", name);
        log.debug("password={}", password);

        if ("".equals(name) || "".equals(password)) {
            throw new AuthenticationCredentialsNotFoundException("ログイン情報に不備があります。");
        }

        User entity = repository.findByUsername(name);
        if (entity == null) {
            throw new AuthenticationCredentialsNotFoundException("ログイン情報が存在しません。");
        }
        
        // Userの中のプロパティpasswordを入力されたパスワードと比較
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if (! passwordEncoder.encode(password).equals(entity.getPassword())) {
//        	throw new AuthenticationCredentialsNotFoundException("ログイン情報が存在しません。");
//        }
        
        Long userId = entity.getUserId();
		// IDをキーにユーザー情報を取得
    	User compUser = repository.findByUserId(userId);;
    	BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    	// 入力されたパスワードとDBのパスワード(ハッシュ化済み)を比較
    	if (! bcpe.matches(entity.getPassword(), compUser.getPassword())) {
    		throw new AuthenticationCredentialsNotFoundException("ログイン情報が存在しません。");
    	}

        return new UsernamePasswordAuthenticationToken(entity, password, entity.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}