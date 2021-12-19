package back;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Diseases;
import model.ResponseJson;
import mylist.DiseasesList;
import mysql.SqlDiseases;

@RestController
public class DiseasesController {
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/diseases")
	public String getAllDiseases() {
		ObjectMapper mapper = new ObjectMapper();
		DiseasesList a = SqlDiseases.getAll();
		try {
			String Diseasess = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", Diseasess);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/diseases")
	public String addDiseases(@RequestBody String objJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Diseases Diseases = mapper.readValue(objJson, Diseases.class);
			int rs = SqlDiseases.add(Diseases);
			if (rs == 0) {
				ResponseJson resjson = new ResponseJson(400, "fail", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			} else {
				ResponseJson resjson = new ResponseJson(200, "success", String.valueOf(rs));
				return mapper.writeValueAsString(resjson);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, path = "/diseases")
	public String deleteDiseases(@RequestParam String id) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			int rs = SqlDiseases.delete(Integer.parseInt(id));
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
	@RequestMapping(method = RequestMethod.PUT, path = "/diseases")
	public String updateDiseases(@RequestParam String id, @RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Diseases Diseases = mapper.readValue(body, Diseases.class);
			int rs = SqlDiseases.update(Integer.parseInt(id), Diseases);
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
	@RequestMapping(method = RequestMethod.GET, path = "/diseases/find")
	public String findDiseases(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		DiseasesList a = SqlDiseases.find(type, value);
		try {
			String Diseasess = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", Diseasess);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/diseases/findlike")
	public String findLikeDiseaes(@RequestParam String type, @RequestParam String value) {
		ObjectMapper mapper = new ObjectMapper();
		DiseasesList a = SqlDiseases.findLike(type, value);
		try {
			String diseasess = mapper.writeValueAsString(a);
			ResponseJson resjson = new ResponseJson(200, "success", diseasess);
			return mapper.writeValueAsString(resjson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("loi cmnr");
			System.out.println(e);
		}
		return "{\"status\":\"400\"}";
	}
}

