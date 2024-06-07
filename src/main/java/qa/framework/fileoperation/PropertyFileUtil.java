package qa.framework.fileoperation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;




public class PropertyFileUtil {
	
	Properties properties;
	String filepath;
	
	public PropertyFileUtil(String filepath) {
		
		BufferedReader reader;
		this.filepath = filepath;
		
		try {
			reader = new BufferedReader(new FileReader(filepath));
			properties = new Properties();
			properties.load(reader);
			reader.close();
			
		} catch(IOException e) {
			
			
		}
		
		
	}
	
	public String getProperty(String propertyname) {
		return properties.getProperty(propertyname);	
	}
	
	public Set<String> getAllKeys(){
		Set<String> keySet = new HashSet<String>();
		properties.keySet().forEach(x -> 
		{ 
			keySet.add(String.valueOf(x));
			});
		
		return keySet;
		
	}
	
	public void setProperty(String propertyname, String value) {
		
		FileWriter fw = null;
		PropertiesConfiguration conf = new PropertiesConfiguration();
		
		try {
			
			conf.setProperty(propertyname, value);
			fw = new FileWriter(filepath);
			conf.write(fw);
			fw.close();
		} catch(ConfigurationException | IOException e) {
			
			e.printStackTrace();
		}
	}

}
