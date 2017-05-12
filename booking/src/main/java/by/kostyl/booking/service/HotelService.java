package by.kostyl.booking.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.navigator.SpringViewProvider;

import by.kostyl.booking.dao.CrudDao;
import by.kostyl.booking.dao.CrudDaoBean;
import by.kostyl.booking.dao.HotelDao;
import by.kostyl.booking.entity.Category;
import by.kostyl.booking.entity.Hotel;

@Service()
public class HotelService {
	@Autowired
	@Qualifier("hoteldao")
	private HotelDao hotelDao;
	@Autowired
	@Qualifier("categorydao")
	private CrudDao categoryDao;

	public List<Hotel> getHotels() {
		List<Hotel> list = hotelDao.list(Hotel.class);
		return list;
	}
	public void saveHotel(Hotel hotel){
		hotelDao.merge(hotel);
	}
	public List<Category> getCategories(){
		
			
			return categoryDao.list(Category.class);
	}
	public void deleteHotel(Hotel hotel){
		hotelDao.delete(Hotel.class, hotel.getId());
	}
	public List<Hotel> filter(String name, String address) {
		return  hotelDao.filter(name,address);
		
	}
	

}
