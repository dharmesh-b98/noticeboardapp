package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.constants.Constant;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {
	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type

	@Autowired
    NoticeRepository noticeRepository;

    RestTemplate template = new RestTemplate();



    public String randomKey(){
        return noticeRepository.randomKey();
    }



    public void insertNotices(String key, String value){
        noticeRepository.insertNotices(key, value);
    }

	

	public JsonObject getJsonResponseFromNoticeServer(String responseBody){
		JsonReader reader = Json.createReader(new StringReader(responseBody));
        JsonObject responseBodyJson = reader.readObject();
		return responseBodyJson;
	}



    public String getSuccessId(JsonObject successJson){
        String id = successJson.getString("id");
        return id;
    }



    public String getFailureMsg(JsonObject failureJson){
        String message = failureJson.getString("message");
        return message;
    }




    public String postToNoticeServer(Notice notice, String apiHostUrl){
        apiHostUrl = apiHostUrl + Constant.postEndpoint;
        
        String noticeJson = convertNoticetoJson(notice).toString();
        RequestEntity<String> requestEntity = RequestEntity.post(apiHostUrl)
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .accept(MediaType.APPLICATION_JSON)
                                                            .body(noticeJson);

        try{
            ResponseEntity<String> responseEntity = template.exchange(requestEntity, String.class);
            return responseEntity.getBody();
        }
        catch(HttpClientErrorException e){
            String response = e.getResponseBodyAsString();
            return response;
        }
    }



    public JsonObject convertNoticetoJson(Notice notice){
        String title = notice.getTitle();
        String poster = notice.getPoster();
        Long postDate = notice.getPostDate().getTime();
        List<String> categoryList = notice.getCategories();
        String text = notice.getText();

        JsonArrayBuilder categoryJsonArray = Json.createArrayBuilder();
        for (String category: categoryList){
            categoryJsonArray = categoryJsonArray.add(category);
        }

        JsonObjectBuilder job = Json.createObjectBuilder();
        JsonObject noticeJson = job.add("title", title)
                                    .add("poster", poster)
                                    .add("postDate", postDate)
                                    .add("categories",categoryJsonArray)
                                    .add("text", text)
                                    .build();

        return noticeJson;
    }
}
