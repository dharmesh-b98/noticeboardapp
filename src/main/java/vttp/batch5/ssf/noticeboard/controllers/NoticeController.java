package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.json.JsonObject;
import jakarta.validation.*;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
@RequestMapping("")
public class NoticeController {

    @Value("${application.host.url}")
    private String apiHostUrl;

    @Autowired
    private NoticeService noticeService;

    @GetMapping("")
    public String getView1(Model model){
        Notice notice = new Notice();
        model.addAttribute(notice);
        return "notice";
    }


    @PostMapping("/notice")
    public String noticeSubmit(@Valid @ModelAttribute("notice") Notice notice, BindingResult binding, Model model){
        
        if (binding.hasErrors()){
            return "notice";
        }

        String response =  noticeService.postToNoticeServer(notice, apiHostUrl);
        JsonObject responseJson = noticeService.getJsonResponseFromNoticeServer(response);
        
        if (responseJson.containsKey("id")){
            String successId = noticeService.getSuccessId(responseJson);
            model.addAttribute("successId", successId);
            
            String jsonString = noticeService.convertNoticetoJson(notice).toString();
            noticeService.insertNotices(successId,jsonString);

            return "view2";
        }

        else {
            String failureMsg = noticeService.getFailureMsg(responseJson);
            model.addAttribute("failureMsg", failureMsg);

            return "view3";
        }
    }

    
    @ResponseBody
    @GetMapping("/status")
    public ResponseEntity<String> checkHealth(){
        try{
            String randomkey = noticeService.randomKey();
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{}");
            return response;
        }
        catch (Exception e){
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.APPLICATION_JSON).body("{}");
            return response;
        }
    }



}
