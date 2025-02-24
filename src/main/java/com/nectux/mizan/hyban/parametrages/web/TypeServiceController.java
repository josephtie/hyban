package com.nectux.mizan.hyban.parametrages.web;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.TypeService;
import com.nectux.mizan.hyban.parametrages.service.TypeServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/parametrages")
public class TypeServiceController {
	
	@Autowired
    TypeServiceService typeServiceService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listetypeservice", method = RequestMethod.POST)
	public @ResponseBody List<TypeService> listeTypeService() {
		return typeServiceService.findTypeServices();
	}

}
