package hospital.controller;

import hospital.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hospital.model.Examination;
import hospital.model.ResponseJson;

import java.util.List;

@RestController
public class ExaminationController {
	private final ExaminationService examinationService ;

	@Autowired
	public ExaminationController(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/examination")
	public String getAllExamination() {
		ObjectMapper mapper = new ObjectMapper();
		List<Examination> lstExamination = examinationService.getAll();
		try {
			String Examinations = mapper.writeValueAsString(lstExamination);
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
	public String getAllExaminationOfDoctor(@RequestParam String id,@RequestParam String dayin) {
		ObjectMapper mapper = new ObjectMapper();
		List<Examination> lstExamination  = examinationService.getExamOfDoctor(id,dayin);
		try {
			String Examinations = mapper.writeValueAsString(lstExamination);
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
	public String findLikeExamination(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Examination> lstExamination  = examinationService.findLike(type, value);
		try {
			String exam = mapper.writeValueAsString(lstExamination);
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
	public String addExamination(@RequestBody String objJson) {
		try {
			System.out.println("daydaydyay" + objJson);
			ObjectMapper mapper = new ObjectMapper();
			Examination Examination = mapper.readValue(objJson, Examination.class);
			System.out.println("in ra Examination");
			System.out.println(Examination.toString());
			int rs = examinationService.add(Examination);
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
	public String deleteExamination(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = examinationService.delete(Integer.parseInt(id));
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
	public String updateExamination(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Examination Examination = mapper.readValue(body, Examination.class);
			int rs = examinationService.update(Integer.parseInt(id), Examination);
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
	public String findExamination(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Examination> lstExamination  = examinationService.find(type, value);
		try {
			String Examinations = mapper.writeValueAsString(lstExamination);
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
