package com.bluewhite.reportexport.entity;

import java.util.Date;

import com.bluewhite.common.utils.excel.Poi;

public class UserPoi {
	
	
	/**
	 */
	@Poi(name = "", column = "A")
	private Integer gender;
	
//	/**
//	 */
//	@Poi(name = "", column = "B")
//	private String id_card;
	
	/**
	 */
	@Poi(name = "", column = "B")
	private String login_name;
	
//	/**
//	 */
//	@Poi(name = "", column = "D")
//	private String phone;
//	
//	/**
//	 */
//	@Poi(name = "", column = "E")
//	private String bank_card1;
//	
//	/**
//	 */
//	@Poi(name = "", column = "F")
//    private Date contract_date;
//	
//	
//	/**
//	 */
//	@Poi(name = "", column = "G")
//    private Date entry;
//	
//	/**
//	 */
//	@Poi(name = "", column = "H")
//    private Date estimate;
//
//	/**
//	 */
//	@Poi(name = "", column = "I")
//    private String information;
//	
//	/**
//	 */
//	@Poi(name = "", column = "J")
//    private String living_address;
//	
//	/**
//	 */
//	@Poi(name = "", column = "K")
//    private String permanent_address;
//	
	/**
	 */
	@Poi(name = "", column = "C")
    private Integer quit;
//	
//	/**
//	 */
//	@Poi(name = "", column = "L")
//    private Date quit_date;
//	
//	/**
//	 */
//	@Poi(name = "", column = "N")
//    private String foreigns;
//	
//	/**
//	 */
//	@Poi(name = "", column = "O")
//    private String telephone;
//	
//	/**
//	 */
//	@Poi(name = "", column = "P")
//    private String company;
//	
//	/**
//	 */
//	@Poi(name = "", column = "Q")
//    private String nexus;
//	
//	/**
//	 */
//	@Poi(name = "", column = "R")
//    private Integer safe;
//	
//	/**
//	 */
//	@Poi(name = "", column = "S")
//    private Integer commitment;
//	
//	/**
//	 */
//	@Poi(name = "", column = "T")
//    private String contacts;
//	
//	/**
//	 */
//	@Poi(name = "", column = "U")
//    private String user_contract_id;
//


	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public Integer getQuit() {
		return quit;
	}

	public void setQuit(Integer quit) {
		this.quit = quit;
	}

	
	

	
	

}
