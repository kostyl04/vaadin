package by.kostyl.booking.ui.forms;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import by.kostyl.booking.entity.Category;
import by.kostyl.booking.entity.Hotel;
import by.kostyl.booking.service.CategoryService;
import by.kostyl.booking.ui.views.HotelCategoryView;

public class CategoryForm extends FormLayout {
	private HotelCategoryView ui;
	private Category category;
	private TextField categoryName = new TextField("Category");;
	private Binder<Category> categoryBinder = new Binder<Category>(Category.class);
	private Button saveBtn;
	private CategoryService categoryService;

	public CategoryForm(HotelCategoryView ui, CategoryService service) {
		saveBtn = new Button("save");
		saveBtn.addClickListener(e->saveCategory());
		this.categoryService = service;
		this.setVisible(false);
		this.ui = ui;
		categoryBinder.forField(categoryName).asRequired("cannot be null")
				.withValidator(val -> 0 < val.length(), "please enter correct categoryname")
				.bind(Category::getName, Category::setName);
		VerticalLayout lay=new VerticalLayout(categoryName,saveBtn);
		addComponent(lay);
		setVisible(false);
	}
	private void saveCategory() {
		if(categoryBinder.isValid()){
			categoryService.saveCategory(this.category);
			this.ui.updateSelect();
			setVisible(false);
		}
		
	}
	public void setCategory(Category category){
		this.category=category;
		categoryBinder.setBean(category);
	}

}
