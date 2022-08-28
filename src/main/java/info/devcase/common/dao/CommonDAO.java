package info.devcase.common.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public <E> List<E> selectList(String statement){
		return sqlSession.selectList(statement);
	}
	
	public <E> List<E> selectList(String statement, Object parameter){
		return sqlSession.selectList(statement, parameter);
	}
	
	public <T> T selectOne(String statement){
		return sqlSession.selectOne(statement);
	}

	public <T> T selectOne(String statement, Object parameter){
		return sqlSession.selectOne(statement, parameter);
	}
	
	public int insert(String statement){
		return sqlSession.insert(statement);
	}

	public int insert(String statement, Object parameter){
		return sqlSession.insert(statement, parameter);
	}

	public int update(String statement){
		return sqlSession.update(statement);
	}

	public int update(String statement, Object parameter){
		return sqlSession.update(statement, parameter);
	}

	public int delete(String statement){
		return sqlSession.delete(statement);
	}

	public int delete(String statement, Object parameter){
		return sqlSession.delete(statement, parameter);
	}
}
