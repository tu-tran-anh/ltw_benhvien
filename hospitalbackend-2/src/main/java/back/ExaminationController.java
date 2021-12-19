package back;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Examination;
import model.ResponseJson;
import mylist.DoctorList;
import mylist.ExaminationList;
import mysql.SqlDoctor;
import mysql.SqlExamination;

@RestController
public class ExaminationController {
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/examination")
	public String dung() {
		ObjectMapper mapper = new ObjectMapper();
		ExaminationList a = SqlExamination.getAll();
		try {
			String Examinations = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", Examinations);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/examinationofdoctor")
	public String dung5(@RequestParam String id,@RequestParam String dayin) {
		ObjectMapper mapper = new ObjectMapper();
		ExaminationList a = SqlExamination.getExamOfDoctor(id,dayin);
		try {
			String Examinations = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", Examinations);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/examination/findlike")
	public String dung6(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		ExaminationList a = SqlExamination.findLike(type, value);
		try {
			String exam = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", exam);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/examination")
	public String dung1(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Examination Examination = mapper.readValue(objJson, Examination.class);
			System.out.println("in ra Examination");
			System.out.println(Examination.toString());
			int rs = SqlExamination.add(Examination);
			if (rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			} else {
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
	@RequestMapping(method = RequestMethod.DELETE, path = "/examination")
	public String dung2(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = SqlExamination.delete(Integer.parseInt(id));
			if (rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			} else {
				ResponseJson resjson = new ResponseJson(200, "success", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, path = "/examination")
	public String dung3(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Examination Examination = mapper.readValue(body, Examination.class);
			int rs = SqlExamination.update(Integer.parseInt(id), Examination);
			if (rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			} else {
				ResponseJson resjson = new ResponseJson(200, "success", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}
		} catch (Exception e) {
			System.out.println("loi update examination");
			System.out.println(e);
			// TODO: handle exception
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/examination/find")
	public String dung4(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		ExaminationList a = SqlExamination.find(type, value);
		try {
			String Examinations = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", Examinations);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}
