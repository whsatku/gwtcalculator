package th.in.whs.ku.calc.client;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import th.in.whs.ku.calc.shared.CalculateResult;
import th.in.whs.ku.calc.shared.RestCalculatorInterface;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Calculator implements EntryPoint {
	
	private List<TextBox> textboxes = new LinkedList<TextBox>();
	private String type;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		type = Window.Location.getParameter("type");
		
		if(!isSupportedCalc(type)){
			showNoCalc();
			return;
		}
		
		Document.get().getElementById("title").setInnerText(type + " Calculator");
		
		Panel panel = new VerticalPanel();
		RootPanel.get().add(panel);
		
		panel.add(buildInputPanel("A:"));
		panel.add(buildInputPanel("B:"));
		panel.add(buildInputPanel("Result:"));
		
		textboxes.get(2).setReadOnly(true);
		
		Button calcLocal = new Button("Calculate locally");
		panel.add(calcLocal);
		
		calcLocal.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				try{
					textboxes.get(2).setText(String.valueOf(
							th.in.whs.ku.calc.shared.Calculator.computeByString(
								type,
								(int) Integer.valueOf(textboxes.get(0).getText()),
								(int) Integer.valueOf(textboxes.get(1).getText())
							)
					));
				}catch(NumberFormatException e){
					showError("One or more non-number are entered");
				}
			}
		});
		
		Button calcRemote = new Button("Calculate remotely");
		Defaults.setServiceRoot("/");
		calcRemote.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RestCalculatorInterface calculator = GWT.create(RestCalculatorInterface.class);
				calculator.compute(
						type,
						(int) Integer.valueOf(textboxes.get(0).getText()),
						(int) Integer.valueOf(textboxes.get(1).getText()),
						new MethodCallback<CalculateResult>(){

							@Override
							public void onFailure(Method method,
									Throwable exception) {
								GWT.log("error", exception);
							}

							@Override
							public void onSuccess(Method method, CalculateResult response) {
								textboxes.get(2).setText(String.valueOf(response.getResult()));
							}
							
						}
				);
			}
		});
		panel.add(calcRemote);
		
		Anchor home = new Anchor("Change calculator", GWT.getHostPageBaseURL());
		panel.add(home);
	}
	
	private static boolean isSupportedCalc(String type){
		return Arrays.asList(th.in.whs.ku.calc.shared.Calculator.supported).contains(type);
	}
	
	private static DialogBox showNoCalc(){
		DialogBox error = new DialogBox();
		error.setText("No such calculator type");
		error.setModal(true);
		
		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.replace(GWT.getHostPageBaseURL());
			}
		});
		error.setWidget(ok);
		
		error.center();
		return error;
	}
	
	private static DialogBox showError(String errorTxt){
		final DialogBox error = new DialogBox();
		error.setText(errorTxt);
		
		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				error.hide();
			}
		});
		error.setWidget(ok);
		
		error.center();
		return error;
	}
	
	private Panel buildInputPanel(String labelTxt){
		Panel panel = new HorizontalPanel();
		
		Label label = new Label(labelTxt);
		label.setWidth("100px");
		panel.add(label);
		
		TextBox text = new TextBox();
		panel.add(text);
		textboxes.add(text);
		
		return panel;
	}
}
