package back;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Nurse;
import model.ResponseJson;
import mylist.MedicineList;
import mylist.NurseList;
import mysql.SqlMedicine;
import mysql.SqlNurse;

@RestController
public class NurseController {
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/nurse")
	public String dung() {
		ObjectMapper mapper = new ObjectMapper();
		NurseList a = SqlNurse.getAll();
		try {
			String Nurses = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", Nurses);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/nurse/findlike")
	public String dung6(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		NurseList a = SqlNurse.findLike(type, value);
		try {
			String nurse = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", nurse);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/nurse")
	public String dung1(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Nurse Nurse = mapper.readValue(objJson, Nurse.class);
			System.out.println("in ra Nurse");
			System.out.println(Nurse.toString());
			int rs = SqlNurse.add(Nurse);
			if(rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}else {
				ResponseJson resjson = new ResponseJson(200, "success", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, path = "/nurse")
	public String dung2(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = SqlNurse.delete(Integer.parseInt(id));
			if(rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}else {
				ResponseJson resjson = new ResponseJson(200, "success", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "{\"status\":\"400\"}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, path = "/nurse")
	public String dung3(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Nurse Nurse = mapper.readValue(body, Nurse.class);
			int rs = SqlNurse.update(Integer.parseInt(id), Nurse);
			if(rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}else {
				ResponseJson resjson = new ResponseJson(200, "success", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "{\"status\":\"400\"}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/nurse/find")
	public String dung4(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		NurseList a = SqlNurse.find(type, value);
		try {
			String Nurses = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", Nurses);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}
