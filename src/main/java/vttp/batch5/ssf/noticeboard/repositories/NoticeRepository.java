package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */

	@Autowired
	@Qualifier("notice")
	RedisTemplate<String,Object> template;
	
	//redis-cli command
	//set key value
	public void insertNotices(String key, String value) {
		template.opsForValue().set(key,value);
	}

	//redis-cli command
	//randomkey
	public String randomKey(){
		return template.randomKey();
	}
	
        

}
