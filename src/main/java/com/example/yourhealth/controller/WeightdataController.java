package com.example.yourhealth.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;

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
import com.example.yourhealth.entity.Weightdata;
import com.example.yourhealth.form.WeightdataForm;
import com.example.yourhealth.repository.WeightdataRepository;

@Controller
public class WeightdataController {

	    protected static Logger log = LoggerFactory.getLogger(WeightdataController.class);

	    @Autowired
	    private ModelMapper modelMapper;

	    @Autowired
	    private WeightdataRepository repository;

	    @Autowired
	    private HttpServletRequest request;

	    @GetMapping("data-record")
		public String showWeightrecordView(Model model, Weightdata entity) {
	        Iterable<Weightdata> weightdata = repository.findAllByOrderByUpdatedAtDesc();
	        model.addAttribute("weightdata", weightdata);
			return "weightdata/weight-record";
		}
	    
	    @GetMapping(path = "/weightdata")
	    public String index(Principal principal, Model model) throws IOException {
//	        Authentication authentication = (Authentication) principal;
//	        UserInf user = (UserInf) authentication.getPrincipal();
//
	        Iterable<Weightdata> weightdata = repository.findAllByOrderByUpdatedAtDesc();
//	        List<WeightdataForm> list = new ArrayList<>();
//	        for (Weightdata entity : weightdata) {
//	            UserInf user;
//				WeightdataForm form = getWeightdata(user, entity);
//	            list.add(form);
//	        }
//	        model.addAttribute("list", list);

	        return "weightdata/weightdata";
	    }

	    public WeightdataForm getWeightdata(UserInf user, Weightdata entity) throws FileNotFoundException, IOException {
	        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//	        modelMapper.typeMap(Weightdata.class, WeightdataForm.class).addMappings(mapper -> mapper.skip(WeightdataForm::setUser));

	        WeightdataForm form = modelMapper.map(entity, WeightdataForm.class);

//	        UserForm userForm = modelMapper.map(entity.getUser(), UserForm.class);
//	        form.setUser(userForm);

	        return form;
	    }
	    @GetMapping(path = "/weightdata/weightdata-record")
	    public String newWeightData(Model model) {
	        model.addAttribute("form", new WeightdataForm());
	        return "weightdata/weightdata-record";
	    }

	    @RequestMapping(value = "/weightdata", method = RequestMethod.POST)
	    public String create(Principal principal, @Validated @ModelAttribute("form") WeightdataForm form, BindingResult result,
	            Model model, RedirectAttributes redirAttrs)
	            throws IOException {
	    	BigDecimal weight = form.getWeightData();
	    	
	        if (result.hasErrors()) {
	            model.addAttribute("hasMessage", true);
	            model.addAttribute("class", "alert-danger");
	            model.addAttribute("message", "登録に失敗しました。");
	            return "weightdata/weightdata-record";
	        }

	        Authentication authentication = (Authentication) principal;
	        UserInf user = (UserInf) authentication.getPrincipal();
	        Long id = user.getUserId();
	        Weightdata entity = new Weightdata(id, weight);
	       
	        repository.saveAndFlush(entity);

	        redirAttrs.addFlashAttribute("hasMessage", true);
	        redirAttrs.addFlashAttribute("class", "alert-info");
	        redirAttrs.addFlashAttribute("message", "登録に成功しました。");

	        return "redirect:/data-record";
	    }

}
