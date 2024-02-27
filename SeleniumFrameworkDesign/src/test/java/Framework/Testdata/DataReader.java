package Framework.Testdata;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	@Test
	public List<HashMap<String, String>> getJsonDataToMap(String jsonContent) throws IOException {
		//read Json to String
		
		FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\Framework\\Testdata\\PurchaseOrder.json"), StandardCharsets.UTF_8);
		
		//Convert String to HashMap
		ObjectMapper mapper= new ObjectMapper();
		List<HashMap<String,String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
		});
		return data;
		
		
	}

}
