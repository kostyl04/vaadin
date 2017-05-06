package by.kostyl.booking.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

public class Header extends HorizontalLayout{
	public Header(){
		
		HorizontalLayout layout=new HorizontalLayout();
		layout.setWidth("100%");
		layout.setHeight("100px");
		setCaption("HOTEL BOOKING");
		Button hotelList=new Button("Hotel List");
		Button categoryList=new Button("Hotel Categories");
		hotelList.addClickListener(e->{
			this.getUI().getNavigator().navigateTo("HotelList");
		});
		categoryList.addClickListener(e->{
			this.getUI().getNavigator().navigateTo("HotelCategories");
		});
		
		layout.addComponents(hotelList,categoryList);
		this.addComponent(layout);
	}
}
