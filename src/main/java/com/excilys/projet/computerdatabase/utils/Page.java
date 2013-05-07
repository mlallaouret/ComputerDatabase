package com.excilys.projet.computerdatabase.utils;

import java.util.List;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.GestionComputerService;

public class Page {

	private boolean first;
	private boolean last;
	private SqlRequestOptions sqlRequestOptions;
	private int pageNumber;
	private int total;
	private int displayFrom;
	private int displayTo;
	private List<Computer> computers;
	
	public Page(String filter, String tri, int page, int maxAffichage) {
		sqlRequestOptions = new SqlRequestOptions();
		sqlRequestOptions.setFilter(filter);
		sqlRequestOptions.setTri(tri);
		this.pageNumber = page;
		total = GestionComputerService.getInstance().getComputerCount(sqlRequestOptions);
		
		if(page<0){
			pageNumber=0;
		} else if(page>0 && (total - page*10<0)){
			pageNumber=0;
		}
		
		//Liste des ordinateurs
		computers = GestionComputerService.getInstance().getComputers(pageNumber, maxAffichage, sqlRequestOptions);
		
		//displayFrom
		displayFrom = total == 0 ? 0 : (pageNumber * maxAffichage +1);
		
		//DisplayTo
		if((total - (pageNumber+1)*10)<1) {
			displayTo = total;
		} else {
			displayTo = (pageNumber +1)*10;
		}
		
		//Première page
		if(pageNumber == 0) {
			first = true;
		} else {
			first = false;
		}
		
		//Derniere page
		if(total - (pageNumber+1)*10 >=1){
			last = false;
		} else {
			last = true;
		}
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public int getTotal() {
		return total;
	}
	
	public List<Computer> getComputers() {
		return computers;
	}
	
	
	public SqlRequestOptions getSqlRequestOptions() {
		return sqlRequestOptions;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getDisplayTo(){
		return displayTo;
	}
	
	public int getDisplayFrom(){
		return displayFrom;
	}
	
	public boolean getLast(){
		return last;
	}
	
	public boolean getFirst(){
		return first;
	}
	
}
