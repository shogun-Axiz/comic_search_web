package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import dao.ComicDao;
import entity.Comic;
import util.DbUtil;

public class ComicService {

	public List<Comic> authentication(String title, String authorName, String publisher, Integer categoryId,
			Integer price1, Integer price2, Date releaseDate1, Date releaseDate2) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try{
			ComicDao comicDao = new ComicDao(conn);
			return comicDao.find(title, authorName, publisher, categoryId, price1, price2, releaseDate1, releaseDate2);
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }
		return Collections.emptyList();
	}

	public List<Comic> select(UUID comicId) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try{
			ComicDao comicDao = new ComicDao(conn);
			List<Comic> comic = comicDao.findById(comicId);

			return comic;
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return null;
	}

	public int registration(Comic regist) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try  {
			ComicDao comicDao = new ComicDao(conn);
			return comicDao.registration(regist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return 0;
	}

	public int update(Comic update) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try  {
			ComicDao comicDao = new ComicDao(conn);
			return comicDao.update(update);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return 0;
	}

	public int delete(UUID comicId) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try {
			ComicDao charDao = new ComicDao(conn);
			return charDao.delete(comicId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}

		return 0;
	}

}
