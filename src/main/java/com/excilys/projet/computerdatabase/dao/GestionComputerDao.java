package com.excilys.projet.computerdatabase.dao;

import java.util.List;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerDao {

	List<Computer> getComputers(int debut, int nombre,
			SqlRequestOptions sqlRequestOptions);

	Computer getComputer(int id);

	Integer getComputerCount(SqlRequestOptions sqlRequestOptions);

	int deleteComputer(int id);

	int updateComputer(Computer computer);

	int insertComputer(Computer computer);

	boolean isComputerExists(int id);

}