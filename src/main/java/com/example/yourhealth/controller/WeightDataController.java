package com.example.yourhealth.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.yourhealth.entity.UserInf;
import com.example.yourhealth.entity.WeightData;
import com.example.yourhealth.form.UserForm;
import com.example.yourhealth.form.WeightDataForm;
import com.example.yourhealth.repository.WeightDataRepository;

import lombok.Data;

@Data
@Controller
public class WeightDataController {

	    protected static Logger log = LoggerFactory.getLogger(WeightDataController.class);

	    @Autowired
	    private ModelMapper modelMapper;

	    @Autowired
	    private WeightDataRepository repository;

	    @Autowired
	    private HttpServletRequest request;
	    
	    /* throws IOException{}がないとエラー出る 
	     * Principal principalでユーザー情報受取る */
	    @GetMapping("data-record")
		public String showWeightRecordView(Principal principal, Model model) throws IOException {
	    	Authentication authentication = (Authentication) principal;
	        UserInf user = (UserInf) authentication.getPrincipal();
	        
	        BigDecimal targetWeight = user.getTargetWeightData();
	        model.addAttribute("targetWeight", targetWeight);
	        
	        /**  リポジトリのインターフェースを実装 データの全件取得  **/
	        Iterable<WeightData> weightData = repository.findAllByOrderByUpdatedAtAsc();
	        
	        // リストを作成し、フォームで入力された{weightData}を要素に追加
	        //List<BigDecimal> list = new ArrayList<>();
	        //for (WeightData entity : weightData) {
	        	// 下の1行いらない
				//WeightDataForm form = getWeightData(user, entity);
	            //list.add(entity.getWeight());
	        //}
	        //model.addAttribute("list", list);
	        
	        // 入力(更新)日時と体重データを紐付けて連想配列作成
	        Map<String, BigDecimal> map = new LinkedHashMap<>();
	        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			
	        for (WeightData entity : weightData) {
	        	String dateToStr = dateFormat.format(entity.getCreatedAt());
	            map.put(dateToStr, entity.getWeight());
	            model.addAttribute("map", map);
	        }
	        
			return "weightData/weight-record";
		}
	    
		// 体重入力画面への遷移ハンドリング
	    @GetMapping(path = "/weightData")
	    public String index(Principal principal, Model model) throws IOException {
	        Authentication authentication = (Authentication) principal;
	        UserInf user = (UserInf) authentication.getPrincipal();

	        return "weightData/weightData";
	    }
	    
	    // getWeiightData()メソッドの定義 エンティティから受け取ったデータをマッピング
	    public WeightDataForm getWeightData(UserInf user, WeightData entity) throws FileNotFoundException, IOException {
	        modelMapper.getConfiguration().setAmbiguityIgnored(true);
	        modelMapper.typeMap(WeightData.class, WeightDataForm.class).addMappings(mapper -> mapper.skip(WeightDataForm::setUser));

	        WeightDataForm form = modelMapper.map(entity, WeightDataForm.class);
	        UserForm userForm = modelMapper.map(entity.getUser(), UserForm.class);
	        form.setUser(userForm);

	        return form;
	    }
	    
	    // Formクラスのインスタンス化
	    @GetMapping(path = "/weightData/weightData-record")
	    public String newWeightData(Model model) {
	        model.addAttribute("form", new WeightDataForm());
	        return "weightData/weightData-record";
	    }
	    
	    // 引数でModelクラスのインスタンスを受け取る
	    @RequestMapping(value = "/weightData", method = RequestMethod.POST)
	    public String create(Principal principal, @Validated @ModelAttribute("form") WeightDataForm form, BindingResult result,
	            Model model, RedirectAttributes redirAttrs)
	            throws IOException {
	    	 BigDecimal weight = form.getWeightData();
	    	
	        if (result.hasErrors()) {
	            model.addAttribute("hasMessage", true);
	            model.addAttribute("class", "alert-danger");
	            model.addAttribute("message", "登録に失敗しました。");
	            return "weightData/weightData";
	        }

	        Authentication authentication = (Authentication) principal;
	        UserInf user = (UserInf) authentication.getPrincipal();
	        Long id = user.getUserId();
			// フォームから送信されたデータを受け取ってエンティティに渡す
	        WeightData entity = new WeightData(id, weight);
	        
	        // DBにデータを登録する処理
	        repository.saveAndFlush(entity);

	        redirAttrs.addFlashAttribute("hasMessage", true);
	        redirAttrs.addFlashAttribute("class", "alert-info");
	        redirAttrs.addFlashAttribute("message", "登録に成功しました。");
	        
	        // 体重管理画面の表示へリダイレクト
	        return "redirect:/data-record";
	    }

}