package th.in.whs.ku.calc.server;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;

import th.in.whs.ku.calc.shared.CalculateResult;
import th.in.whs.ku.calc.shared.Calculator;

@Path("calculator")
public class RestCalculator {
	ObjectMapper mapper = new ObjectMapper();
	
	@POST
	@Path("{type}")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public String compute(
			@PathParam("type") String type,
			@FormParam("a") int a,
			@FormParam("b") int b
	){
		CalculateResult out;
		try{
			out = new CalculateResult(Calculator.computeByString(type, a, b));
		}catch(RuntimeException e){
			out = new CalculateResult(e.getMessage());
		}
		
		try {
			return mapper.writeValueAsString(out);
		} catch (IOException e) {
			return "{\"error\": \"json error\"}";
		}
	}
}
