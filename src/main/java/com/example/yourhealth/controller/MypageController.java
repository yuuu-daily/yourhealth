package com.example.yourhealth.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
		
		/* --- このリクエストでやりたい操作（目標体重のDBからの取得　→　viewへの受け渡し --- */
		@GetMapping("mypage")
		public String editMypageView(Principal principal, Model model) throws IOException {
			// ログインユーザー情報の取得
			Authentication authentication = (Authentication) principal;
	        UserInf user = (UserInf) authentication.getPrincipal();
	        
	        /*  ----- ここから下は保存されている目標体重のデータの取得処理 -----  */
	        
	        // 現在のログインユーザーのIDを特定
	        Long currentUserId = user.getUserId();
	        
	        /* --- ユーザー情報の中の目標体重レコードを取得 --- */
	        
	        // DBからレコード取得 = リポジトリからDBを取得
	        User currentUser = repository.findByUserId(currentUserId);
	        // 誰の目標体重か？ (l41, 42の処理でユーザーの情報を取得)
	        BigDecimal targetWeight = currentUser.getTargetWeightData();
	        String purpose = currentUser.getPurpose();
	        // viewへの受け渡し
	        model.addAttribute("targetWeight", targetWeight);
	        model.addAttribute("purpose", purpose);
			return "mypage";
			
		}
		
		/* Authentication authenticationでログインユーザー情報取得 
		 * フォームから入力されたデータを受け取りDBにデータを更新      */
		@PostMapping(path = "update-target-weight", params = "targetWeight")
	    public String update(Principal principal, @RequestParam("targetWeight") BigDecimal targetWeight, Model model) {
			// ログインユーザー情報の取得
			Authentication authentication = (Authentication) principal;
			UserInf user = (UserInf) authentication.getPrincipal();
	        
	        /* --- 以下、このリクエストでやりたい操作 --- */
	        // 現在のログインユーザーのIDを特定
	        Long currentUserId = user.getUserId();
	        // リポジトリからDBを取得
	        User currentUser = repository.findByUserId(currentUserId);
	        // 目標体重の変更・更新
	        currentUser.setTargetWeightData(targetWeight);
	        repository.saveAndFlush(currentUser);
	        
	        /* --- session の更新 (目標体重を更新した後、更新されたnew目標体重をviewに反映させる処理) --- */
	        authentication = new UsernamePasswordAuthenticationToken(currentUser,currentUser.getPassword(),currentUser.getAuthorities());
	        SecurityContext context = SecurityContextHolder.getContext();
	        context.setAuthentication(authentication);
	        
	        // 結果の受け渡し
	        return "redirect:/data-record";
		}
		
		@PostMapping(path = "update-purpose", params = "purpose")
	    public String update(Principal principal, @RequestParam("purpose") String purpose, Model model) {
			// ログインユーザー情報の取得
			Authentication authentication = (Authentication) principal;
			UserInf user = (UserInf) authentication.getPrincipal();
	        
	        /* --- 以下、このリクエストでやりたい操作 --- */
	        // 現在のログインユーザーのIDを特定
	        Long currentUserId = user.getUserId();
	        // リポジトリからDBを取得
	        User currentUser = repository.findByUserId(currentUserId);
	        // 目的の変更・更新
	        currentUser.setPurpose(purpose);
	        repository.saveAndFlush(currentUser);
	        
	        /* --- session の更新 --- */
	        authentication = new UsernamePasswordAuthenticationToken(currentUser,currentUser.getPassword(),currentUser.getAuthorities());
	        SecurityContext context = SecurityContextHolder.getContext();
	        context.setAuthentication(authentication);
	        
	        // 結果の受け渡し
	        return "redirect:/data-record";
		}
		   	
}