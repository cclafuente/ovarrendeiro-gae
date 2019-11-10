package es.panaderiaovarrendeiro.gae.service.customers;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import es.panaderiaovarrendeiro.gae.dao.BaseDao;
import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.service.BaseManager;

public class CustomerManagerImpl implements BaseManager<Customer>{

	private BaseDao<Customer> customerDao;

	public BaseDao<Customer> getCustomerDao() {
		return customerDao;
	}
	public void setCustomerDao(BaseDao<Customer> customerDao) {
		this.customerDao = customerDao;
	}

	public Collection<Customer> findAll() {
		return customerDao.getAll();
	}

	public void removeById(Serializable id) {
		customerDao.remove((Long)id);
	}

	public Serializable save(Customer persistentObject) {
		persistentObject.setLastUpdate(new Date());
		customerDao.save(persistentObject);
		return persistentObject.getId();
	}

	public Customer findById(Serializable id){
		return customerDao.get(id);
	}
	
}
