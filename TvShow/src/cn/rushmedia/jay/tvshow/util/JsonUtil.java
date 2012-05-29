package cn.rushmedia.jay.tvshow.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.User;

public class JsonUtil {
	    final static int BUFFER_SIZE = 4096; 
	    public JSONArray getSource(String path)throws Exception{
	    	SourceDownLoader sc = new SourceDownLoader();
			JSONArray array = sc.getall(path);
	    	return array;
	    }
	    public String getStringSource(String path)throws Exception{
	    	SourceDownLoader sc = new SourceDownLoader();
			String array = sc.getallString(path);
	    	return array;
	    }
		public static String InputStreamTOString(InputStream in) throws Exception{  
			          ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
			          byte[] data = new byte[BUFFER_SIZE];  
			          int count = -1;  
			          while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
			            outStream.write(data, 0, count);  
                        data = null; 
                        String result=new String(outStream.toByteArray(),"utf-8");
                        return result;
		}
		public ArrayList<Program>  getMovie(String path) throws Exception{
			ArrayList<Program> arrayList = new ArrayList<Program>();
			SourceDownLoader sc = new SourceDownLoader();
			JSONArray array = sc.getall(path);
			for (int i = 0; i < array.length(); i++) {
				Program movie= new Program();
			JSONObject jsobj=array.getJSONObject(i);
			if(!jsobj.isNull("id")){
				 int programid = jsobj.getInt("id");
				 movie.setId(programid);
			}else if(jsobj.isNull("id")){
				continue;
			}
	   //	long created_at=jsobj.getLong("created_at");
			if(!jsobj.isNull("actor")){
				String actor = jsobj.getString("actor");
				movie.setActor(actor);
			}else if(jsobj.isNull("actor")){
				movie.setActor("导演未知");
			}
			if(!jsobj.isNull("keyword")){
				String keyword = jsobj.getString("keyword");
				movie.setKey(keyword);
			}else if(jsobj.isNull("keyword")){
				movie.setKey("未知");
			}
			if(!jsobj.isNull("director")){
				String director = jsobj.getString("director");
				movie.setDirector(director);
			}else if(jsobj.isNull("director")){
				movie.setDescription("精彩不容错过");
			}
			if(!jsobj.isNull("title")){
				String title = jsobj.getString("title");
				movie.setTitle(title);
			}else if(jsobj.isNull("title")){
				movie.setTitle("未知电影");
			}
			if(!jsobj.isNull("description")){
				String description = jsobj.getString("description");
				movie.setDescription(description);
			}
			if(!jsobj.isNull("image")){
				String imagePath=jsobj.getString("image");
				movie.setImagePath(imagePath);
//				int id= jsobj.getInt("id");
//				int topic =jsobj.getInt("topic");
			}
			
			
			//if(!jsobj.isNull("user")){
				//JSONObject  jb = jsobj.getJSONObject("user");
				//Users user = new Users();
//			public Movie(Users user, int topics, String key, int created_at,
//					String director, int posts, String actor, String title, int id,
//					String imagePath, boolean favorite, String description)
				 //user.setCommentmys(jb.getInt("mycomments"));
				 //user.setFollowers(jb.getInt("followings"));
			arrayList.add(movie);
			}		
			return arrayList;
		}
		}  
		    

