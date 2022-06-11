package com.example.yourhealth.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.yourhealth.entity.User;
import com.example.yourhealth.entity.UserInf;
import com.example.yourhealth.repository.UserRepository;

import lombok.Data;

@Data
@Controller
public class MypageController {
	
		protected static Logger log = LoggerFactory.getLogger(MypageController.class);
		
		@Autowired
	    private UserRepository repository;
		
		@Autowired
	    private HttpServletRequest request;
		
		/* DBに保存されている目標体重のviewへの受け渡し */
		@GetMapping("mypage")
		public String editMypageView(Principal principal, Model model) throws IOException {
			Authentication authentication = (Authentication) principal;
	        UserInf user = (UserInf) authentication.getPrincipal();
	        
	        BigDecimal targetWeight = user.getTargetWeightData();
	        model.addAttribute("targetWeight", targetWeight);
			return "mypage";
		}
		
		/* Authentication authenticationでログインユーザーのID取得 
		 * フォームから入力されたデータを受け取りDBにデータを更新      */
		@PostMapping(path = "data-record", params = "targetWeight")
	    public String update(Principal principal, @RequestParam("targetWeight") BigDecimal targetWeight, Model model) {
			Authentication authentication = (Authentication) principal;
	        UserInf user = (UserInf) authentication.getPrincipal();
	       
	        Long currentUserId = user.getUserId();
	        User currentUser = repository.findByUserId(currentUserId);
	        currentUser.setTargetWeightData(targetWeight);
	        repository.save(currentUser);
	        
	        /* session の更新 */
	        
//			Optional<User> userInf = repository.findByUserId(userId);
//	        repository.saveAndFlush(user.getTargetWeightData(), targetWeight);
//			model.addAttribute("targetWeight", targetWeight);
//	        user.setTargetWeightData(targetWeight);
//	        repository.save(user);
	        return "redirect:/data-record";
		}
		
//		public String updateSecurityContext(UserInf user) {
//		    User user = UserInf.builder()
//		    		.name(user.getName())
//		            .username(user.getUsername())
//		            .password(user.getPassword())
//		            .roles(userMapper.findRolesByUserId(user.getId()).toArray(String[]::new))
//		            .build();
//		    SecurityContext context = SecurityContextHolder.getContext();
//		    context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
//		                                                                                                                        
//		    logger.info("security context updated to {}", user);
//		}
		   	
}