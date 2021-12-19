package hospital.controller;

import hospital.service.DoctorDiseasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hospital.model.DoctorDiseases;
import hospital.model.ResponseJson;

import java.util.List;

@RestController
public class DoctorDiseasesController {

	private final DoctorDiseasesService doctorDiseasesService;

	@Autowired
	public DoctorDiseasesController(DoctorDiseasesService doctorDiseasesService) {
		this.doctorDiseasesService = doctorDiseasesService;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/doctordiseases")
	public String findAllDoctorDiseases() {
		ObjectMapper mapper = new ObjectMapper();
		List<DoctorDiseases> lstDoctorDiseases = doctorDiseasesService.getAll();
		try {
			String DoctorDiseasess = mapper.writeValueAsString(lstDoctorDiseases);
			ResponseJson resjson = new ResponseJson(200, "success", DoctorDiseasess);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/doctordiseases/findboth")
	public String findBothDoctorDiseases(@RequestParam String iddoctor, @RequestParam String iddiseases) {
		ObjectMapper mapper = new ObjectMapper();
		List<DoctorDiseases> lstDoctorDiseases = doctorDiseasesService.findAllByIddoctorAndIddiseases(Integer.parseInt(iddoctor), Integer.parseInt(iddiseases));
		try {
			String DoctorDiseasess = mapper.writeValueAsString(lstDoctorDiseases);
			ResponseJson resjson = new ResponseJson(200, "success", DoctorDiseasess);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/doctordiseases")
	public String addDoctorDiseases(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			DoctorDiseases doctorDiseases = mapper.readValue(objJson, DoctorDiseases.class);
			System.out.println("in ra DoctorDiseases");
			System.out.println(doctorDiseases.toString());
			int rs = doctorDiseasesService.add(doctorDiseases);
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
	@RequestMapping(method = RequestMethod.DELETE, path = "/doctordiseases")
	public String deleteDoctorDiseases(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = doctorDiseasesService.delete(Integer.parseInt(id));
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
	@RequestMapping(method = RequestMethod.PUT, path = "/doctordiseases")
	public String updateDoctorDiseases(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			DoctorDiseases DoctorDiseases = mapper.readValue(body, DoctorDiseases.class);
			int rs = doctorDiseasesService.update(Integer.parseInt(id), DoctorDiseases);
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
	@RequestMapping(method = RequestMethod.PUT, path = "/doctordiseases/type")
	public String updateTypeDoctorDiseases(@RequestParam String id, @RequestParam String type) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = doctorDiseasesService.updateType(Integer.parseInt(id), type);
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
	@RequestMapping(method = RequestMethod.GET, path = "/doctordiseases/find")
	public String findDoctorDiseases(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<DoctorDiseases> lstDoctorDiseases = doctorDiseasesService.find(type, value);
		try {
			String DoctorDiseasess = mapper.writeValueAsString(lstDoctorDiseases);
			ResponseJson resjson = new ResponseJson(200, "success", DoctorDiseasess);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}
