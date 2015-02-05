package th.in.whs.ku.calc.shared;

public class CalculateResult {
	private int result;
	private String error;
	
	public CalculateResult(){
	}

	public CalculateResult(int result) {
		this.result = result;
	}
	public CalculateResult(String error) {
		this.error = error;
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
