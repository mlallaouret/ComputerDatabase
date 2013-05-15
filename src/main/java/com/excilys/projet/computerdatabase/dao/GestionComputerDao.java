package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.util.List;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerDao {

	public abstract List<Computer> getComputers(Connection conn, int debut, int nombre,
			SqlRequestOptions sqlRequestOptions);

	public abstract Computer getComputer(Connection conn, int id);

	public abstract Integer getComputerCount(Connection conn, SqlRequestOptions sqlRequestOptions);

	public abstract void deleteComputer(Connection conn, int id);

	public abstract void updateComputer(Connection conn, Computer computer);

	public abstract void insertOrUpdateComputer(Connection conn, Computer computer);

	public abstract boolean isComputerExists(Connection conn, int id);

}