package by.kostyl.booking.ui.views;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

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
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

import by.kostyl.booking.entity.Category;
import by.kostyl.booking.entity.Hotel;
import by.kostyl.booking.entity.PaymentService;
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
@SpringView(name = HotelView.VIEW_NAME)
public class HotelView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "hotels";
	private Header header = new Header();
	@Autowired
	private HotelService hotelService;
	private Grid<Hotel> hotelGrid = new Grid<>(Hotel.class);
	private Button editBtn = new Button("Edit");
	private Button bulkUpdateBtn = new Button("BulkUpdate");
	private TextField filterField = new TextField();
	private HotelForm hotelForm;
	private TextField filterByAddressField = new TextField();
	private Button clearAddressFilterBtn = new Button(VaadinIcons.CLOSE_CIRCLE);
	private Button clearBtn = new Button(VaadinIcons.CLOSE_CIRCLE);
	private CssLayout filtering = buildFilteringLayout();
	private PopupView popup;
	/*
	 * @Override protected void init(VaadinRequest vaadinRequest) {
	 * 
	 * }
	 */

	public void updateHotels() {
		hotelGrid.setItems(hotelService.getHotels());
		filterField.clear();
		filterByAddressField.clear();
	}

	public HotelView() {

	}

	private void filter() {
		hotelGrid.setItems(hotelService.filter(filterField.getValue(), filterByAddressField.getValue()));
	}

	private CssLayout buildFilteringLayout() {
		filterByAddressField.setPlaceholder("Enter address u want to see...");
		filterByAddressField.addValueChangeListener(e -> filter());
		clearAddressFilterBtn.addClickListener(e -> filterByAddressField.clear());
		CssLayout lay1 = new CssLayout(filterByAddressField, clearAddressFilterBtn);
		lay1.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filterField.setPlaceholder("Enter ur searchstring...");
		filterField.addValueChangeListener(e -> filter());
		clearBtn.addClickListener(e -> filterField.clear());
		CssLayout lay2 = new CssLayout(filterField, clearBtn);
		lay2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		CssLayout lay = new CssLayout(lay2, lay1);

		return lay;
	}

	@PostConstruct
	void init() {
		final VerticalLayout layout = new VerticalLayout();
		this.hotelForm = new HotelForm(this, hotelService);
		hotelGrid.setSelectionMode(SelectionMode.MULTI);
		hotelGrid.setColumns("id", "name", "rating", "description", "address", "operatesFrom");
		Column<Hotel, String> htmlColumn = hotelGrid.addColumn(
				hotel -> "<a href='" + hotel.getUrl() + "' target='_top'>" + hotel.getUrl() + "</a>",
				new HtmlRenderer());
		htmlColumn.setCaption("Link");
		Column<Hotel, String> column = hotelGrid.addColumn(hotel -> hotel.getCategory()==null?"No category": hotel.getCategory().getName());
		column.setCaption("Category");
		updateHotels();
		HorizontalLayout main = new HorizontalLayout(hotelGrid, hotelForm);
		main.setSizeFull();
		hotelGrid.setSizeFull();
		main.setExpandRatio(hotelGrid, 1);

		hotelForm.setVisible(false);
		editBtn.addClickListener(e -> {
			Set<Hotel> set = hotelGrid.getSelectedItems();
			if (!set.isEmpty()) {
				hotelForm.setHotel(set.iterator().next());
			}
		});

		hotelGrid.asMultiSelect().addValueChangeListener(e -> {
			
			if (hotelGrid.getSelectedItems().size() > 1) {
				bulkUpdateBtn.setEnabled(true);
				hotelForm.setVisible(false);
				editBtn.setEnabled(false);
			} else if (hotelGrid.getSelectedItems().size() == 1) {
				editBtn.setEnabled(true);
				bulkUpdateBtn.setEnabled(false);
				hotelForm.setVisible(false);
			} else {
				bulkUpdateBtn.setEnabled(false);
				hotelForm.setVisible(false);
				editBtn.setEnabled(false);
			}

		});
		Button addNewHotelBtn = new Button("New Hotel");
		addNewHotelBtn.addClickListener(e -> {
			hotelGrid.asMultiSelect().clear();
			hotelForm.setHotel(new Hotel("", "", 1, 123L, new Category(), "", "",new PaymentService(0, false, true)));

		});
		HorizontalLayout toolbar = new HorizontalLayout(filtering, addNewHotelBtn, editBtn, bulkUpdateBtn);
		bulkUpdateBtn.setEnabled(false);
		editBtn.setEnabled(false);
		bulkUpdateBtn.addClickListener(e -> {
			this.popup.setPopupVisible(true);
		});
		layout.addComponents(toolbar, main);
		this.addComponents(header, layout);
		buildPopup();
		toolbar.addComponent(this.popup);

	}

	private void buildPopup() {

		NativeSelect<String> selectField = new NativeSelect();
		selectField
				.setItems(Arrays.asList("name", "address", "url", "description", "category", "rating", "operatesFrom"));
		selectField.setSelectedItem("name");

		TextField change = new TextField();
		NativeSelect<Category> categorySelect = new NativeSelect<>(null, hotelService.getCategories());
		NativeSelect<Integer> ratingSelect = new NativeSelect<>(null, Arrays.asList(1, 2, 3, 4, 5));
		categorySelect.setVisible(false);
		categorySelect.addValueChangeListener(e -> {
			if (e.getValue() != null)
				change.setValue(String.valueOf(e.getValue().getId()));

		});
		DateField operatesFrom = new DateField("from");
		operatesFrom.setVisible(false);
		operatesFrom.setRangeEnd(LocalDate.now());
		operatesFrom.addValueChangeListener(e -> {
			if (e.getValue() != null)
				change.setValue(String
						.valueOf(Duration.between(e.getValue().atTime(0, 0), LocalDate.now().atTime(0, 0)).toDays()));

		});
		ratingSelect.setVisible(false);
		ratingSelect.addValueChangeListener(e -> {
			if (e.getValue() != null)
				change.setValue(String.valueOf(e.getValue()));

		});
		categorySelect.setItemCaptionGenerator(Category::getName);
		Button update = new Button("update");
		Label label = new Label("Update Hotels");
		VerticalLayout lay = new VerticalLayout(label, selectField, change, categorySelect, ratingSelect, operatesFrom,
				update);
		lay.setWidth("250px");
		lay.setHeight("400px");
		this.popup = new PopupView(null, lay);
		update.addClickListener(e -> {
			if (!selectField.isEmpty() && !change.isEmpty()) {
				String field = selectField.getValue();
				if (field.equals("category")) {
					field = "category_id";
				}
				if (field.equals("operatesFrom")) {
					field = "OPERATES_FROM";
				}
				hotelService.bulkUpdate(field, change.getValue(), this.hotelGrid.getSelectedItems());
				this.popup.setPopupVisible(false);
				this.updateHotels();
			}
		});
		selectField.addValueChangeListener(e -> {
			change.clear();
			change.setVisible(false);
			categorySelect.setVisible(false);
			ratingSelect.setVisible(false);
			operatesFrom.setVisible(false);
			if (e.getValue() != null) {
				if (e.getValue().equals("category")) {
					categorySelect.setVisible(true);
				} else if (e.getValue().equals("rating")) {
					ratingSelect.setVisible(true);
				} else if (e.getValue().equals("operatesFrom")) {
					operatesFrom.setVisible(true);
				} else {
					change.setVisible(true);
				}
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// this.getUI().getPage().setUriFragment("/hotellist");
		// updateHotels();
	}
}
