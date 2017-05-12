package by.kostyl.booking.ui.views;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MultiSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

import by.kostyl.booking.entity.Category;
import by.kostyl.booking.entity.Hotel;
import by.kostyl.booking.service.CategoryService;
import by.kostyl.booking.service.HotelService;
import by.kostyl.booking.ui.forms.CategoryForm;
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
@SpringView(name = HotelCategoryView.VIEW_NAME)
public class HotelCategoryView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "categories";
	private Header header = new Header();
	@Autowired
	private CategoryService categoryService;
	private ListSelect<Category> categorySelect;
	private Button addCategoryBtn;
	private Button editCategoryBtn;
	private Button delCategoryBtn;
	private CategoryForm categoryForm;

	public HotelCategoryView() {

	}

	

	@Override
	public void enter(ViewChangeEvent event) {

	}

	@PostConstruct
	void init() {
		
		// building components
		categoryForm = new CategoryForm(this,categoryService);
		HorizontalLayout layout=createContent();
		
		addComponents(header,layout);

		
	}



	private HorizontalLayout createContent() {
		categorySelect=new ListSelect<Category>();
		updateSelect();
		this.categorySelect.setWidth("220px");
		editCategoryBtn=new Button("Edit");
		editCategoryBtn.setEnabled(false);
		editCategoryBtn.addClickListener(e->editBtnClick());
		delCategoryBtn=new Button("Delete");
		delCategoryBtn.setEnabled(false);
		delCategoryBtn.addClickListener(e->delBtnClick());
		addCategoryBtn=new Button("Add");
		addCategoryBtn.addClickListener(e->{
			categoryForm.setCategory(new Category(""));
			categoryForm.setVisible(true);
		});
		categorySelect.addValueChangeListener(e->menuLogic(e.getValue().size()));
		CssLayout menu=new CssLayout(editCategoryBtn,delCategoryBtn,addCategoryBtn);
		menu.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		VerticalLayout l=new VerticalLayout(menu,categorySelect);
		HorizontalLayout layout=new HorizontalLayout(l,categoryForm);
		return layout;
	}
	private void menuLogic(int size){
		if(size==0){
			editCategoryBtn.setEnabled(false);
			delCategoryBtn.setEnabled(false);
			
		}else if(size==1){
			editCategoryBtn.setEnabled(true);
			delCategoryBtn.setEnabled(true);
		}else if(size>1){
			editCategoryBtn.setEnabled(false);
			delCategoryBtn.setEnabled(true);
		}
	}
	private void delBtnClick(){
		categorySelect.getValue().forEach(category->{
			categoryService.deleteCategory(category);
		});
		updateSelect();
	}
	private void editBtnClick(){
		categoryForm.setCategory(categorySelect.getValue().iterator().next());
		categoryForm.setVisible(true);
	}

	public void updateSelect() {
		
		this.categorySelect.setItems(categoryService.getCategories());
	}
}
