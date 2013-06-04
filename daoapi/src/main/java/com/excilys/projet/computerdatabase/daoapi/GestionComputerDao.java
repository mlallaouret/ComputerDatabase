package com.excilys.projet.computerdatabase.daoapi;

import java.util.List;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerDao {

	List<Computer> getComputers(int debut, int nombre,
			SqlRequestOptions sqlRequestOptions);

	Computer getComputer(int id);

	Integer getComputerCount(SqlRequestOptions sqlRequestOptions);

	boolean deleteComputer(Computer computer);

	boolean updateComputer(Computer computer);

	boolean insertComputer(Computer computer);

	boolean ComputerExists(int id);

}