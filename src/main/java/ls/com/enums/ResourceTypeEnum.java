package ls.com.enums;

public enum ResourceTypeEnum {
	OneThreeEight("1","138美容网"),
	FiveEight("2","58同城"),
	Ganji("3","赶集网");
	private String name;
    private String id;
	private ResourceTypeEnum(String id,String name){
		this.id = id;
		this.name=name;
	}
	public static String getName(String id) {
        for (ResourceTypeEnum c : ResourceTypeEnum.values()) {
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
