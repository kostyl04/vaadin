package by.kostyl.booking.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import by.kostyl.booking.dao.CrudDao;
import by.kostyl.booking.entity.Category;
import by.kostyl.booking.entity.Hotel;

@Service
public class CategoryService {
	@Autowired
	@Qualifier("categorydao")
	private CrudDao categoryDao;

	public List<Category> getCategories() {
		List<Category> list = categoryDao.list(Category.class);
		return list;
	}

	public void saveCategory(Category category) {
		categoryDao.merge(category);
	}

	public void deleteCategory(Category category) {
		categoryDao.delete(Category.class, category.getId());
	}
}
