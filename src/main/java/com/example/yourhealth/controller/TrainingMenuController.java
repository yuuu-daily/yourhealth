package com.example.yourhealth.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.yourhealth.entity.TrainingMenu;
import com.example.yourhealth.entity.UserInf;
import com.example.yourhealth.form.TrainingMenuForm;
import com.example.yourhealth.repository.TrainingMenuRepository;
import com.example.yourhealth.request.TrainingMenuUpdateRequest;
import com.example.yourhealth.service.TrainingMenuService;

import lombok.Data;

@Data
@Controller
public class TrainingMenuController {
	
	protected static Logger log = LoggerFactory.getLogger(MypageController.class);
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private TrainingMenuRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	   * ユーザー情報 Service
	   */
	  @Autowired
	  private TrainingMenuService trainingMenuService;
	
	/* トレーニングメニューのindex表示 */
	@GetMapping(path = "trainingMenu")
	public String showTrainingMenu(Principal principal, Model model) {
    	Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        
        model.addAttribute("toptitle", "Training Menu List");
        
        /**  リポジトリのインターフェースを実装 データの全件取得  **/
        Iterable<TrainingMenu> trainingMenus = repository.findAllByOrderByUpdatedAtAsc();
        model.addAttribute("trainingMenus", trainingMenus);
        
		return "trainingMenu";
	}
	/* トレーニングメニューの作成画面 
	 * Formクラスのインスタンス化
	 * */
	@GetMapping("create")
	public String getTrainigMenu(Model model) {
		model.addAttribute("form", new TrainingMenuForm());
		return "create";
	}
	
	/* カードリストの詳細を表示するメソッド */
	@RequestMapping("/user/menu/{trainingId}")
	public String displayTrainingMenuView(Principal principal, Model model, @PathVariable("trainingId") Long trainingId) {
    	Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        Long userId = user.getUserId();
        
        try {
        	/**  リポジトリのインターフェースを実装 データの取得  **/
//            TrainingMenu userTraining = repository.findByIdAndUserId(trainingId, userId);
            TrainingMenu userTraining = repository.findById(trainingId);
//            List<TrainingMenu> training = user.getTrainingMenuList(trainingId);
            model.addAttribute("training", userTraining);
//            model.addAttribute("training", training);
            return "user/menu";
        } catch (EntityNotFoundException e) {
            return "redirect:trainingMenu";
        }
	}
	
	// 引数でModelクラスのインスタンスを受け取る
    @RequestMapping(value = "/trainingMenu", method = RequestMethod.POST)
    public String create(Principal principal, @Validated @ModelAttribute("form") TrainingMenuForm form, BindingResult result,
            Model model, RedirectAttributes redirAttrs)
            throws IOException {
    	 String category = form.getCategory();
    	 String title = form.getTitle();
    	 String description = form.getDescription();
    	 
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", "登録に失敗しました。");
            return "create";
        }

        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        Long id = user.getUserId();
		// フォームから送信されたデータを受け取ってエンティティに渡す
        TrainingMenu entity = new TrainingMenu(id, category, title, description);
        
        // DBにデータを登録する処理
        repository.saveAndFlush(entity);

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message", "登録に成功しました。");
        
        // 体重管理画面の表示へリダイレクト
        return "redirect:/trainingMenu";
    }
    
    /* メニュー詳細ページの削除処理 */
    @PostMapping(path = "delete", params = "delete")
    String delete(@RequestParam Long id, Model model) {
        trainingMenuService.delete(id);
        return "redirect:/trainingMenu";
    }
    
    /**
     * メニュー編集画面を表示
     * @param id 表示するメニューID
     * @param model Model
     * @return メニュー編集画面
     */
    @PostMapping("/user/menuEdit")
    public String displayEdit(@RequestParam Long id, Model model) {
      TrainingMenu menu = trainingMenuService.findById(id);
      TrainingMenuUpdateRequest menuUpdateRequest = new TrainingMenuUpdateRequest();
      menuUpdateRequest.setId(menu.getId());
      menuUpdateRequest.setCategory(menu.getCategory());
      menuUpdateRequest.setTitle(menu.getTitle());
      menuUpdateRequest.setDescription(menu.getDescription());
      model.addAttribute("userUpdateRequest", menuUpdateRequest);
      return "user/menuEdit";
    }
    
    /**
     * メニュー更新
     * @param trainingMenuRequest リクエストデータ
     * @param model Model
     * @return メニュー情報詳細画面
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute TrainingMenuUpdateRequest trainingMenuUpdateRequest, BindingResult result, Model model) {
      if (result.hasErrors()) {
        List<String> errorList = new ArrayList<String>();
        for (ObjectError error : result.getAllErrors()) {
          errorList.add(error.getDefaultMessage());
        }
        model.addAttribute("validationError", errorList);
        return "user/menuEdit";
      }
      // ユーザー情報の更新
      trainingMenuService.update(trainingMenuUpdateRequest);
      return String.format("redirect:/user/%d", trainingMenuUpdateRequest.getId());
    }
    
    
	
	/* トレーニングメニューの作成完了表示画面 */
	@PostMapping("complete")
	public String getTrainingMenuComplete(@ModelAttribute TrainingMenuForm form, Model model) {
		model.addAttribute("form", form);
		return "result";
	}
	
}