package cn.rushmedia.jay.tvshow.util;

public class String2Json {
 
	 public static String string2Json(String s) { 
		    StringBuilder sb = new StringBuilder(s.length()+20); 
		    sb.append('\"'); 
		    for (int i=0; i<s.length(); i++) { 
		        char c = s.charAt(i); 
		        switch (c) { 
		        case '\"': 
		            sb.append("\\\""); 
		            break; 
		        case '\\': 
		            sb.append("\\\\"); 
		            break; 
		        case '/': 
		            sb.append("\\/"); 
		            break; 
		        case '\b': 
		            sb.append("\\b"); 
		            break; 
		        case '\f': 
		            sb.append("\\f"); 
		            break; 
		        case '\n': 
		            sb.append("\\n"); 
		            break; 
		        case '\r': 
		            sb.append("\\r"); 
		            break; 
		        case '\t': 
		            sb.append("\\t"); 
		            break; 
		        default: 
		            sb.append(c); 
		        } 
		    } 
		    sb.append('\"'); 
		    return sb.toString(); 
		 } 
	   
}
