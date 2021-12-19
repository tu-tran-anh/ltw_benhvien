package hospital.controller;
import hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import hospital.model.Doctor;
import hospital.model.ResponseJson;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DoctorController {

	private final DoctorService doctorService;

	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}


	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/doctor")
	public String getAllDoctor() {
		ObjectMapper mapper = new ObjectMapper();
		List<Doctor> lstDoctor = new ArrayList<>();
		doctorService.getAll().forEach(lstDoctor::add);
		try {
			String doctors = mapper.writeValueAsString(lstDoctor);
			ResponseJson resjson = new ResponseJson(200, "success", doctors);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/doctorheal")
	public String findDoctorHeal(@RequestParam String id) {
		ObjectMapper mapper = new ObjectMapper();
		List<Doctor> lstDoctor = new ArrayList<>();
		doctorService.getDoctorHeal(id).forEach(lstDoctor::add);
		try {
			String doctors = mapper.writeValueAsString(lstDoctor);
			ResponseJson resjson = new ResponseJson(200, "success", doctors);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/doctor")
	public String addDoctor(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Doctor doctor = mapper.readValue(objJson, Doctor.class);
			int rs = doctorService.add(doctor);
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
	@RequestMapping(method = RequestMethod.DELETE, path = "/doctor")
	public String deleteDoctor(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = doctorService.delete(Integer.parseInt(id));
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
	@RequestMapping(method = RequestMethod.PUT, path = "/doctor")
	public String updateDoctor(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Doctor doctor = mapper.readValue(body, Doctor.class);
			int rs = doctorService.update(Integer.parseInt(id), doctor);
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
	@RequestMapping(method = RequestMethod.GET, path = "/doctor/find")
	public String findDoctor(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Doctor> lstDoctor = new ArrayList<>();
		doctorService.find(type,value).forEach(lstDoctor::add);

		try {
			String doctors = mapper.writeValueAsString(lstDoctor);
			ResponseJson resjson = new ResponseJson(200, "success", doctors);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/doctor/findlike")
	public String findLikeDoctor(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Doctor> lstDoctor = new ArrayList<>();
		doctorService.findLike(type,value).forEach(lstDoctor::add);
		try {
			String doctors = mapper.writeValueAsString(lstDoctor);
			ResponseJson resjson = new ResponseJson(200, "success", doctors);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}
