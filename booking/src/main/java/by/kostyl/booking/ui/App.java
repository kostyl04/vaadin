package by.kostyl.booking.ui;

import java.util.Arrays;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;

import by.kostyl.booking.ui.views.HotelCategoryView;
import by.kostyl.booking.ui.views.HotelView;
@SpringUI(path="/*")
public class App extends UI{
	
	private Navigator navigator;
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    public static class MyUIServlet extends SpringVaadinServlet {
    }
	@WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    	
    }
    @Autowired
    SpringViewProvider viewProvider;
	@Override
	protected void init(VaadinRequest request) {
		this.navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		
	    
		
	}
}
