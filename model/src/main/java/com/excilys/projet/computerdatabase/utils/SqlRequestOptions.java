package com.excilys.projet.computerdatabase.utils;

public class SqlRequestOptions {

	private String filter;
	
	private String order;
	private String tri;
	private final String[] CRITERIA = {Column.NAME.toString(), Column.INTRODUCED.toString(), Column.DISCONTINUED.toString(),
			Column.COMPANY_NAME.toString(), Column.NAME.toString()};
	
	private enum Order{
		ASC,DESC
	}
	
	private enum Column{
		NAME("cpu.name"), INTRODUCED("cpu.introduced"), DISCONTINUED("cpu.discontinued"), COMPANY_NAME("cpu.company.name");
		
		private final String field;
		
		private Column(String field){
			this.field = field;
		}
		
		@Override
		public String toString(){
			return field;
		}
	}
	
	public SqlRequestOptions() {
		filter=null;
		tri = Column.NAME.toString();
	}
	
	public SqlRequestOptions(String filter, int tri) {
		this.filter = filter;
		
		setSort(tri);
	}
	
	
	private void setSort(int tri){
		if(tri<0){
			order = Order.DESC.toString();
		} else {
			order = Order.ASC.toString();
		}
		
		if((tri>5 || tri<-5) || (tri<2 && tri>-2)) {
			this.tri = CRITERIA[0];
		} else {
			this.tri = CRITERIA[Math.abs(tri)-2];
		}
	}
	
	public String getSqlOrder(){
		return order;
	}
	
	public String getSqlTri(){
		
		return this.tri;
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

	public String getFilter() {
		if(filter ==null) {
			return "";
		} else {
			return filter;
		}
	}
}
