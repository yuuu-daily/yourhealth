package com.example.yourhealth.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.yourhealth.entity.TrainingMenu;
import com.example.yourhealth.entity.UserInf;
import com.example.yourhealth.form.TrainingMenuForm;
import com.example.yourhealth.repository.TrainingMenuRepository;

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
	
	/* トレーニングメニューのindex表示 */
	@GetMapping(path = "trainingMenu")
	public String showTrainingMenu(Principal principal, Model model) {
    	Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        
        model.addAttribute("moji", "トレーニングメニュー一覧");
        
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

	
	/* トレーニングメニューの作成完了表示画面 */
	@PostMapping("complete")
	public String getTrainingMenuComplete(@ModelAttribute TrainingMenuForm form, Model model) {
		model.addAttribute("form", form);
		return "result";
	}
	
	
}