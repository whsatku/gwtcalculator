package th.in.whs.ku.calc.client;

import th.in.whs.ku.calc.shared.Calculator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainMenu implements EntryPoint {
	
	private Panel panel;

	@Override
	public void onModuleLoad() {
		RootPanel root = RootPanel.get("app");
		panel = new VerticalPanel();
		root.add(panel);
		
		buildButtons();
	}
	
	private void buildButtons(){
		for(final String item : Calculator.supported){
			Button button = new Button(item);
			button.setWidth("100%");
			
			button.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Window.Location.assign(GWT.getHostPageBaseURL() + "calculator.html?type=" + item);
				}
				
			});
			
			panel.add(button);
		}
	}

}
