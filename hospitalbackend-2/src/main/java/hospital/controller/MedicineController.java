package hospital.controller;

import hospital.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hospital.model.Medicine;
import hospital.model.ResponseJson;

import java.util.List;

@RestController
public class MedicineController {
	private final MedicineService medicineService;

	@Autowired
	public MedicineController(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/medicine")
	public String getAllMedicine() {
		ObjectMapper mapper = new ObjectMapper();
		List<Medicine> lstMedicine = medicineService.getAll();
		try {
			String Medicines = mapper.writeValueAsString(lstMedicine);
			ResponseJson resjson = new ResponseJson(200, "success", Medicines);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{}";
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/medicine/findlike")
	public String findLikeMedicine(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Medicine> lstMedicine = medicineService.findLike(type, value);
		try {
			String medicine = mapper.writeValueAsString(lstMedicine);
			ResponseJson resjson = new ResponseJson(200, "success", medicine);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/medicine")
	public String addMedicine(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Medicine Medicine = mapper.readValue(objJson, Medicine.class);
			System.out.println("in ra Medicine");
			System.out.println(Medicine.toString());
			int rs = medicineService.add(Medicine);
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
	@RequestMapping(method = RequestMethod.DELETE, path = "/medicine")
	public String deleteMedicine(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = medicineService.delete(Integer.parseInt(id));
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
	@RequestMapping(method = RequestMethod.PUT, path = "/medicine")
	public String updateMedicine(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Medicine Medicine = mapper.readValue(body, Medicine.class);
			int rs = medicineService.update(Integer.parseInt(id), Medicine);
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
	@RequestMapping(method = RequestMethod.GET, path = "/medicine/find")
	public String findMedicine(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		List<Medicine> lstMedicine = medicineService.find(type, value);
		try {
			String Medicines = mapper.writeValueAsString(lstMedicine);
			ResponseJson resjson = new ResponseJson(200, "success", Medicines);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}
