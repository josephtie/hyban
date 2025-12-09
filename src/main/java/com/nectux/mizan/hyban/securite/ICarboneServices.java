//package com.nectux.mizan.hyban.securite;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//
//import java.util.Map;
//
//@FeignClient(
//        name = "carbone",
//        url = "${carbone.url}",
//        configuration = CarboneConfig.class
//)
//public interface ICarboneServices {
//
//    @PostMapping(
//            value = "/render",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    CarboneResponse render(
//            @RequestPart("template") byte[] template,
//            @RequestPart("data") String jsonData,
//            @RequestPart(value = "options", required = false) Map<String, Object> options
//    );
//}

