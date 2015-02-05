package th.in.whs.ku.calc.shared;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.google.gwt.http.client.Request;

@Path("/api/calculator")
public interface RestCalculatorInterface extends RestService {
	
	@POST
	public Request compute(
			@FormParam("type") String type,
			@FormParam("a") int a,
			@FormParam("b") int b,
			MethodCallback<CalculateResult> callback
	);
}
