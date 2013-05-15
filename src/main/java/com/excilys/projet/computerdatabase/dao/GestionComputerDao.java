package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerDao {

	List<Computer> getComputers(Connection conn, int debut, int nombre,
			SqlRequestOptions sqlRequestOptions) throws SQLException;

	Computer getComputer(Connection conn, int id) throws SQLException;

	Integer getComputerCount(Connection conn, SqlRequestOptions sqlRequestOptions) throws SQLException;

	void deleteComputer(Connection conn, int id) throws SQLException;

	void updateComputer(Connection conn, Computer computer) throws SQLException;

	void insertOrUpdateComputer(Connection conn, Computer computer) throws SQLException;

	boolean isComputerExists(Connection conn, int id) throws SQLException;

}