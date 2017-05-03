package by.kostyl.booking.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
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
public class MyUI extends UI {
	private HotelService hotelService = HotelService.getInstance();
	private Grid<Hotel> hotelGrid = new Grid<>(Hotel.class);

	private TextField filterField = new TextField();
	private HotelForm hotelForm = new HotelForm(this);
	private TextField filterByAddressField = new TextField();
	private Button clearAddressFilterBtn = new Button(VaadinIcons.CLOSE_CIRCLE);
	private Button clearBtn = new Button(VaadinIcons.CLOSE_CIRCLE);
	private CssLayout filtering = buildFilteringLayout();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();

		hotelGrid.setColumns("id", "name", "rating", "description", "address");
		Column<Hotel, String> htmlColumn = hotelGrid.addColumn(
				hotel -> "<a href='" + hotel.getUrl() + "' target='_top'>" + hotel.getUrl() + "</a>",
				new HtmlRenderer());
		htmlColumn.setCaption("Link");
		updateHotels("");
		HorizontalLayout main = new HorizontalLayout(hotelGrid, hotelForm);
		main.setSizeFull();
		hotelGrid.setSizeFull();
		main.setExpandRatio(hotelGrid, 1);

		hotelForm.setVisible(false);
		hotelGrid.asSingleSelect().addValueChangeListener(e -> {
			if (e.getValue() == null) {
				hotelForm.setVisible(false);
			} else
				hotelForm.setHotel(e.getValue());
		});
		Button addNewHotelBtn = new Button("New Hotel");
		addNewHotelBtn.addClickListener(e -> {
			hotelGrid.asSingleSelect().clear();
			hotelForm.setHotel(new Hotel());
		});
		HorizontalLayout toolbar = new HorizontalLayout(filtering, addNewHotelBtn);
		layout.addComponents(toolbar, main);
		setContent(layout);
	}

	public void updateHotels(String t) {
		hotelGrid.setItems(hotelService.findAll(t));
	}

	private CssLayout buildFilteringLayout() {
		filterByAddressField.setPlaceholder("Enter address u want to see...");
		filterByAddressField.addValueChangeListener(e -> updateHotels(e.getValue()));
		clearAddressFilterBtn.addClickListener(e -> filterByAddressField.clear());
		CssLayout lay1 = new CssLayout(filterByAddressField, clearAddressFilterBtn);
		lay1.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filterField.setPlaceholder("Enter ur searchstring...");
		filterField.addValueChangeListener(e -> updateHotels(e.getValue()));
		clearBtn.addClickListener(e -> filterField.clear());
		CssLayout lay2 = new CssLayout(filterField, clearBtn);
		lay2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		CssLayout lay = new CssLayout(lay2, lay1);

		return lay;
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
