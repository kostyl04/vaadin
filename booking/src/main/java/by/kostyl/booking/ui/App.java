package by.kostyl.booking.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

public class App extends UI{
	private Navigator navigator;
	@WebServlet(urlPatterns = "/*", asyncSupported = true)
	@VaadinServletConfiguration(ui = App.class, productionMode = false, widgetset = "")
	public static class MyUIServlet extends VaadinServlet {

	}

	@Override
	protected void init(VaadinRequest request) {
		this.navigator = new Navigator(this, this);
		this.navigator.addView("", new HotelView());
		this.navigator.addView("HotelList", new HotelView());
		this.navigator.addView("HotelCategories", new HotelCategoryView());
		HorizontalLayout layout=new HorizontalLayout();
		layout.setWidth("100%");
		layout.setHeight("100px");
		Button hotelList=new Button("Hotel List");
		Button categoryList=new Button("Hotel Categories");
		hotelList.addClickListener(e->{
			navigator.navigateTo("HotelList");
		});
		categoryList.addClickListener(e->{
			navigator.navigateTo("HotelCategories");
		});
		
		layout.addComponents(hotelList,categoryList);
		setContent(layout);
	    
		
	}
}
