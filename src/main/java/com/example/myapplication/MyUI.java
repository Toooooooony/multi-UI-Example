package com.example.myapplication;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@PreserveOnRefresh
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        
        navigator.addView("", new Welcome());
        navigator.addView("page1", new Page1());
        navigator.addView("page2", new Page2());
        
        
      }
    
    
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

class Welcome extends VerticalLayout implements View {
	private Navigator navigator;
    
	  public Welcome() {
		  MenuBar menuBar = new MenuBar();
		  MenuItem submenu1 = menuBar.addItem("Submenu 1", null);
		  submenu1.addItem("Option 1", null);
		  submenu1.addItem("page1", new Command() {
			  public void menuSelected(MenuItem selectedItem) {
				  navigator.navigateTo("page1");
			  }
			});
		  submenu1.addItem("page2", new Command() {
			  public void menuSelected(MenuItem selectedItem) {
				  navigator.navigateTo("page2");
			  }
			});
		  
		  /*Button goToPage1 = new Button("page1");
		  Button goToPage2 = new Button("page2");
		  ClickListener listener = new ClickListener() {
		        @Override
		        public void buttonClick(ClickEvent event) {
		          navigator.navigateTo(event.getButton().getCaption());
		        }
		      };*/
		  //goToPage1.addClickListener(listener);
		  //goToPage2.addClickListener(listener);
		  //goToPage1.setStyleName(BaseTheme.BUTTON_LINK);
		  //goToPage2.setStyleName(BaseTheme.BUTTON_LINK);
		  addComponent(new Label("Welcome"));
		  //addComponent(goToPage1);
		  //addComponent(goToPage2);
		  addComponent(menuBar);
	   
	  }
	  @Override
	  public void enter(ViewChangeEvent event) {
		  navigator = event.getNavigator();
	    Notification.show("Showing Welcome page");
	  }
	}

class Page1 extends VerticalLayout implements View {
	  public Page1() {
	    this.addComponent(new Label("Page 1"));
        TextField tf = new TextField(
        	    "Type, press ENTER, and refresh the browser");
        	tf.setImmediate(true);
        	tf.addValueChangeListener(new ValueChangeListener() {
        	  public void valueChange(ValueChangeEvent event) {
        	    Notification.show(
        	        "Value: " + event.getProperty().getValue());
        	  }
        	});
        	this.addComponent(tf);
	  }
	  @Override
	  public void enter(ViewChangeEvent event) {
	    Notification.show("Showing page 1");
	  }
	}

class Page2 extends VerticalLayout implements View {
	  public Page2() {
	    addComponent(new Label("Page 2"));
	  }
	  @Override
	  public void enter(ViewChangeEvent event) {
	    Notification.show("Showing page 2");
	  }
	}

