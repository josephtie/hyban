package com.nectux.mizan.hyban.parametrages.web;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import com.nectux.mizan.hyban.parametrages.service.TypeContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/parametrages")
public class TypeContratController {
	
	@Autowired
    TypeContratService typeContratService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listetypecontrat", method = RequestMethod.POST)
	public @ResponseBody List<TypeContrat> listeTypeContrat() {
		return typeContratService.findTypeContrats();
	}

}
