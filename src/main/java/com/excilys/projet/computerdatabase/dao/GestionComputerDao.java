package com.excilys.projet.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerDao {

	List<Computer> getComputers(int debut, int nombre,
			SqlRequestOptions sqlRequestOptions) throws SQLException;

	Computer getComputer(int id) throws SQLException;

	Integer getComputerCount(SqlRequestOptions sqlRequestOptions) throws SQLException;

	int deleteComputer(int id) throws SQLException;

	int updateComputer(Computer computer) throws SQLException;

	int insertOrUpdateComputer(Computer computer) throws SQLException;

	boolean isComputerExists(int id) throws SQLException;

}