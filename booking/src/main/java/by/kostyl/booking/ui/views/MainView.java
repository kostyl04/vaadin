package by.kostyl.booking.ui.views;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

import by.kostyl.booking.entity.Hotel;
import by.kostyl.booking.service.HotelService;
import by.kostyl.booking.ui.forms.HotelForm;
import by.kostyl.booking.ui.views.imports.Header;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */

@Theme("mytheme")
@SpringView(name = MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";
	private Header header = new Header();
	

	/*
	 * @Override protected void init(VaadinRequest vaadinRequest) {
	 * 
	 * }
	 */

	

	public MainView() {

	}

	public void filterHotels(String searchString, int type) {
		// hotelGrid.setItems(hotelService.filter(searchString, type));
	}

	

	@PostConstruct
	void init() {
		
		this.addComponents(header,new HorizontalLayout(new Label("Main view")));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
}
