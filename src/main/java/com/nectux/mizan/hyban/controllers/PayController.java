package com.nectux.mizan.hyban.controllers;


import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.ImprimBulletinPaie;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.parametrages.dto.AuthResponse;
import com.nectux.mizan.hyban.parametrages.dto.LoginRequest;
import com.nectux.mizan.hyban.parametrages.dto.ProfileDto;
import com.nectux.mizan.hyban.parametrages.dto.RegisterRequest;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Rubrique;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.utils.ProvisionConge;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayController {

    @Autowired
    private BulletinPaieService bulletinPaieService;

    @Autowired
    private PeriodePaieService periodePaieService;

    @PostMapping ("/employe/{personnalId}")
    public List<BulletinPaie> getCurrentYearBulletins(@PathVariable Long personnalId) {
       PeriodePaie maperiode=periodePaieService.findPeriodeactive();
        return bulletinPaieService.getCurrentYearBulletins(personnalId,maperiode.getAnnee().getAnnee()).getRows();
    }


    @PostMapping ("/{payrollId}")
    public BulletinPaieDTO getCurrentSelectBulletin(@PathVariable Long payrollId) {
        //PeriodePaie maperiode=periodePaieService.findPeriodeactive();
         BulletinPaieDTO bulletinPaieDTO=new BulletinPaieDTO();

        return bulletinPaieService.finImprimbulletin(payrollId);
    }


}
