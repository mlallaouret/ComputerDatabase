package com.excilys.projet.computerdatabase.utils;

public class SqlRequestOptions {

	private String filter;
	
	private String order;
	private String tri;
	
	private enum Order{
		ASC,DESC;
	}
	
	private enum Column{
		NAME("cpu.name"), INTRODUCED("cpu.introduced"), DISCONTINUED("cpu.discontinued"), COMPANY_NAME("cpy.name");
		
		private String field;
		
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
		
		switch(Math.abs(tri)) {
		
		case 2:
			this.tri = Column.NAME.toString();
			break;
			
		case 3:
			this.tri = Column.INTRODUCED.toString();
			break;
			
		case 4:
			this.tri = Column.DISCONTINUED.toString();
			break;
			
		case 5:
			this.tri = Column.COMPANY_NAME.toString();
			break;
			
		default:
			this.tri = Column.NAME.toString();
			break;
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
