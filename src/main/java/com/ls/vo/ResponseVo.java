package com.ls.vo;                                                                                                                          
                                                                                                                                            
import org.apache.commons.lang.StringUtils;                                                                                                 
                                                                                                                                            
                                                                                                                                            
public class ResponseVo {                                                                                                                   
	                                                                                                                                        
	public enum MessageType {                                                                                                               
		SUCCESS,                                                                                                                            
		FAIL                                                                                                                                
	}                                                                                                                                       
	                                                                                                                                        
	private String type;                                                                                                                    
	private String message;                                                                                                                 
	private String mode;                                                                                                                    
	private Object object;                                                                                                                  
	                                                                                                                                        
	public String getType() {                                                                                                               
	                                                                                                                                        
		return type;                                                                                                                        
	}                                                                                                                                       
	                                                                                                                                        
	public void setType(String type) {                                                                                                      
	                                                                                                                                        
		this.type = type;                                                                                                                   
	}                                                                                                                                       
	                                                                                                                                        
	public String getMessage() {                                                                                                            
	                                                                                                                                        
		return message;                                                                                                                     
	}                                                                                                                                       
	                                                                                                                                        
	public void setMessage(String message) {                                                                                                
	                                                                                                                                        
		this.message = message;                                                                                                             
	}                                                                                                                                       
	                                                                                                                                        
	                                                                                                                                        
	public String getMode() {                                                                                                               
	                                                                                                                                        
		return mode;                                                                                                                        
	}                                                                                                                                       
                                                                                                                                            
	                                                                                                                                        
	public void setMode(String mode) {                                                                                                      
	                                                                                                                                        
		this.mode = mode;                                                                                                                   
	}                                                                                                                                       
                                                                                                                                            
	public static ResponseVo newResponse() {                                                                                                
		return new ResponseVo();                                                                                                            
	}                                                                                                                                       
	                                                                                                                                        
	public static ResponseVo newSuccessMessage(String message) {                                                                            
		                                                                                                                                    
		if (StringUtils.isEmpty(message)){                                                                                                  
			message = "<b>操作成功</b>";                                                                                                        
		}                                                                                                                                   
		                                                                                                                                    
		ResponseVo vo = new ResponseVo();                                                                                                   
		vo.setType(MessageType.SUCCESS.name());                                                                                             
		vo.setMessage(message);                                                                                                             
		                                                                                                                                    
		return vo;                                                                                                                          
	}                                                                                                                                       
                                                                                                                                            
	public static ResponseVo newFailMessage(String message) {                                                                               
		                                                                                                                                    
		if (StringUtils.isEmpty(message)){                                                                                                  
			message = "<b>操作失败</b>";                                                                                                        
		}                                                                                                                                   
		                                                                                                                                    
		ResponseVo vo = new ResponseVo();                                                                                                   
		vo.setType(MessageType.FAIL.name());                                                                                                
		vo.setMessage(message);                                                                                                             
		                                                                                                                                    
		return vo;                                                                                                                          
	}                                                                                                                                       
                                                                                                                                            
	@Override                                                                                                                               
	public String toString() {                                                                                                              
                                                                                                                                            
		return "ResponseVo [type=" + type + ", message=" + message + ", mode=" + mode + "]";                                                
	}                                                                                                                                       
                                                                                                                                            
	public Object getObject() {                                                                                                             
		return object;                                                                                                                      
	}                                                                                                                                       
                                                                                                                                            
	public void setObject(Object object) {                                                                                                  
		this.object = object;                                                                                                               
	}                                                                                                                                       
	                                                                                                                                        
	                                                                                                                                        
}                                                                                                                                           
                                                                                                                                            