package es.panaderiaovarrendeiro.gae.service.notices;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import es.panaderiaovarrendeiro.gae.dao.BaseDao;
import es.panaderiaovarrendeiro.gae.model.Notice;

public class NoticeManagerImpl implements NoticeManager {

	private BaseDao<Notice> noticeDao;
	
	public BaseDao<Notice> getNoticeDao() {
		return noticeDao;
	}

	public void setNoticeDao(BaseDao<Notice> noticeDao) {
		this.noticeDao = noticeDao;
	}

	public Long create(Notice product) {
		product.setLastUpdate(new Date());
		noticeDao.save(product);
		return product.getId();
	}

	public Notice findById(Long noticeId) {
		return noticeDao.get(noticeId);
	}

	public Collection<Notice> findByMap(Map<String, ?> conds) {
		return noticeDao.getObjectsClauseEqual(conds);
	}

	public Collection<Notice> getAllNotices(int page) {
		return noticeDao.getAll();
	}

	public Collection<Notice> getLastUpdated(int number) {
		return noticeDao.getLastUpdated(number);
	}

	public void remove(Notice product) {
		noticeDao.remove(product.getId());
	}

}
