package hospital.controller;

import hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hospital.model.Patient;
import hospital.model.ResponseJson;

import java.util.List;

@RestController
public class PatientController {
	private final PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/patient")
	public String getAllPatient() {
		ObjectMapper mapper = new ObjectMapper();
		List<Patient> lstPatient = patientService.getAll();
		try {
			String Patients = mapper.writeValueAsString(lstPatient);
			ResponseJson resjson = new ResponseJson(200, "success", Patients);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/patient/findlike")
	public String findLikePatient(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Patient> lstPatient = patientService.findLike(type, value);
		try {
			String patient = mapper.writeValueAsString(lstPatient);
			ResponseJson resjson = new ResponseJson(200, "success", patient);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/patient")
	public String addPatient(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Patient Patient = mapper.readValue(objJson, Patient.class);
			System.out.println("in ra Patient");
			System.out.println(Patient.toString());
			int rs = patientService.add(Patient);
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
	@RequestMapping(method = RequestMethod.DELETE, path = "/patient")
	public String deletePatient(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = patientService.delete(Integer.parseInt(id));
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
	@RequestMapping(method = RequestMethod.PUT, path = "/patient")
	public String updatePatient(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Patient Patient = mapper.readValue(body, Patient.class);
			int rs = patientService.update(Integer.parseInt(id), Patient);
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
	@RequestMapping(method = RequestMethod.GET, path = "/patient/find")
	public String findPatient(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Patient> lstPatient  = patientService.find(type, value);
		try {
			String Patients = mapper.writeValueAsString(lstPatient);
			ResponseJson resjson = new ResponseJson(200, "success", Patients);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}
