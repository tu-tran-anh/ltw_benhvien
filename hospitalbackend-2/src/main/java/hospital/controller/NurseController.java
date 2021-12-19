package hospital.controller;

import hospital.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hospital.model.Nurse;
import hospital.model.ResponseJson;

import java.util.List;

@RestController
public class NurseController {
	private final NurseService nurseService;

	@Autowired
	public NurseController(NurseService nurseService) {
		this.nurseService = nurseService;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/nurse")
	public String getAllNurse() {
		ObjectMapper mapper = new ObjectMapper();
		List<Nurse> lstNurse = nurseService.getAll();
		try {
			String Nurses = mapper.writeValueAsString(lstNurse);
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
	public String findlikeNurse(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Nurse> lstNurse = nurseService.findLike(type, value);
		try {
			String nurse = mapper.writeValueAsString(lstNurse);
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
	public String addNurse(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Nurse Nurse = mapper.readValue(objJson, Nurse.class);
			System.out.println("in ra Nurse");
			System.out.println(Nurse.toString());
			int rs = nurseService.add(Nurse);
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
	public String deleteNurse(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = nurseService.delete(Integer.parseInt(id));
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
	public String updateNurse(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Nurse Nurse = mapper.readValue(body, Nurse.class);
			int rs = nurseService.update(Integer.parseInt(id), Nurse);
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
	public String findNurse(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Nurse> lstNurse = nurseService.find(type, value);
		try {
			String Nurses = mapper.writeValueAsString(lstNurse);
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
