package com.ls.enums;

public enum StatusEnum {

	Yixiang("1","意向客户"),
	Neixun("2","内训"),
	Jipinban("3","精品班"),
	Yuanzhangban("4","院长班");
	private String name;
    private String id;
	private StatusEnum(String id,String name){
		this.id = id;
		this.name=name;
	}
	public static String getName(String id) {
        for (StatusEnum c : StatusEnum.values()) {
            if (c.getId().equals(id)) {
                return c.name;
            }
        }
        return null;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
     

}
