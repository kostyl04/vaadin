package by.kostyl.booking.ui.views.imports;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

public class Header extends HorizontalLayout {
	
	public Header() {
		
	
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setHeight("100px");
		/*setCaption("HOTEL BOOKING");
		Button hotelList = new Button("Hotel List");
		Button categoryList = new Button("Hotel Categories");
		hotelList.addClickListener(e -> {
			this.getUI().getNavigator().navigateTo("hotels");
		});
		categoryList.addClickListener(e -> {
			this.getUI().getNavigator().navigateTo("categories");
		});*/

		layout.addComponents(createMenu());
		this.addComponent(layout);
	}

	private MenuBar createMenu() {
		MenuBar menu=new MenuBar();
		
		Command navigateToHotels=new MenuBar.Command(){

			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().navigateTo("hotels");
				
			}
			
		};
		Command navigateToCategories=new MenuBar.Command(){
			@Override
			public void menuSelected(MenuItem selectedItem) {
				
				getUI().getNavigator().navigateTo("categories");
				
			}
			
		};
	
		menu.addItem("Hotel List", navigateToHotels);
		menu.addItem("Categories List", navigateToCategories);
		
		return menu;
	}
}
