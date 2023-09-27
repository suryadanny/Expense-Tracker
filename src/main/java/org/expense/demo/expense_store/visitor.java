package org.expense.demo.expense_store;


import javax.xml.bind.annotation.XmlRootElement;
// object model of the visitor 
@XmlRootElement
public  class visitor{

	private String name;
	private String organisation;
	private String idtype;
	private String id_number;
	private String entry_time = "string"; 
	private String contact;
	
	
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getIdtype() {
		return idtype;
	}
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	
	
	
	public void set_visitor(String name , String organisation , String idtype ,String id_number ,String time ,String contact)
	{
		this.name = name;
		this.organisation = organisation;
		this.idtype = idtype;
		this.id_number = id_number;
		this.entry_time = time;
		this.contact = contact;
	}
}
