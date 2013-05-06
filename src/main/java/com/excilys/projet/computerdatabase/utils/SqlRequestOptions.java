package com.excilys.projet.computerdatabase.utils;

public class SqlRequestOptions {

	private String filter;
	
	private int tri;
	
	public SqlRequestOptions() {
		filter=null;
		tri = 2;
	}
	
	public SqlRequestOptions(String filter, int tri) {
		this.filter = filter;
		this.tri = tri;
	}
	
	
	public String getSqlTri(){
		
		String sqlTri = null;
		switch(tri) {
				
			case -2:
				sqlTri= "cpu.name DESC";
				break;
				
			case 2:
				sqlTri= "cpu.name ASC";
				break;
				
			case -3:
				sqlTri= "cpu.introduced DESC";
				break;
				
			case 3:
				sqlTri= "cpu.introduced ASC";
				break;
				
			case -4:
				sqlTri= "cpu.discontinued DESC";
				break;
				
			case 4:
				sqlTri= "cpu.discontinued ASC";
				break;
				
			case -5:
				sqlTri= "cpy.name DESC";
				break;
				
			case 5:
				sqlTri= "cpy.name ASC";
				break;
				
			default:
				sqlTri= "cpu.name ASC";
				break;
		}
		return sqlTri;
	}
	
	public String getSqlFilter(){
		StringBuilder sb= new StringBuilder("%");
		if(filter!=null) {
			sb.append(filter);
			sb.append("%");
		}
		return sb.toString();
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setTri(int tri) {
		if(tri<-5 || tri>5) {
			this.tri=2;
		} else if(tri > -2 && tri<2){
			this.tri = 2;
		} else {
			this.tri=tri;
		}
		
		
	}
	
	public void setTri(String tri) {
		if(tri ==null) {
			this.tri = 2;
			return;
		}
		
		int t= 2;
		try{
			t= Integer.parseInt(tri);
		} catch (NumberFormatException e){
			this.tri = 2;
			return;
		}
		
		if(t<-5 || t>5) {
			this.tri=2;
		} else if(t > -2 && t<2){
			this.tri = 2;
		} else {
			this.tri=t;
		}
		
		
	}

	public int getTri() {
		return tri;
	}

	public String getFilter() {
		if(filter ==null) {
			return "";
		} else {
			return filter;
		}
	}
}
