package hospital.controller;

import hospital.service.MedicinePerExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hospital.model.Medicineperexam;
import hospital.model.ResponseJson;

import java.util.List;

@RestController
public class MedicineperexamController {
	private final MedicinePerExamService medicinePerExamService;

	@Autowired
	public MedicineperexamController(MedicinePerExamService medicinePerExamService) {
		this.medicinePerExamService = medicinePerExamService;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/medicineperexam")
	public String getAllMedicineperexam() {
		ObjectMapper mapper = new ObjectMapper();
		List<Medicineperexam> lstMedicineperexam = medicinePerExamService.getAll();
		try {
			String Medicineperexams = mapper.writeValueAsString(lstMedicineperexam);
			ResponseJson resjson = new ResponseJson(200, "success", Medicineperexams);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/medicineperexam")
	public String addMedicineperexam(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Medicineperexam Medicineperexam = mapper.readValue(objJson, Medicineperexam.class);
			System.out.println("in ra Medicineperexam");
			System.out.println(Medicineperexam.toString());
			int rs = medicinePerExamService.add(Medicineperexam);
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
	@RequestMapping(method = RequestMethod.DELETE, path = "/medicineperexam")
	public String deleteMedicineperexam(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = medicinePerExamService.delete(Integer.parseInt(id));
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
	@RequestMapping(method = RequestMethod.PUT, path = "/medicineperexam")
	public String updateMedicineperexam(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Medicineperexam Medicineperexam = mapper.readValue(body, Medicineperexam.class);
			int rs = medicinePerExamService.update(Integer.parseInt(id), Medicineperexam);
			if (rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			} else {
				ResponseJson resjson = new ResponseJson(200, "success", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi update medicine per examination");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/medicineperexam/find")
	public String findMedicineperexam(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Medicineperexam> lstMedicineperexam  = medicinePerExamService.find(type, value);
		try {
			String Medicineperexams = mapper.writeValueAsString(lstMedicineperexam);
			ResponseJson resjson = new ResponseJson(200, "success", Medicineperexams);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}
