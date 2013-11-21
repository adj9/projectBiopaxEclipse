package cpath.webservice;

View Javadoc

1   package cpath.webservice;
2   
3   import java.awt.image.BufferedImage;
4   import java.io.BufferedReader;
5   import java.io.ByteArrayInputStream;
6   import java.io.ByteArrayOutputStream;
7   import java.io.File;
8   import java.io.IOException;
9   import java.io.InputStreamReader;
10  import java.io.Writer;
11  import java.net.URL;
12  import java.util.*;
13  
14  import javax.imageio.ImageIO;
15  import javax.servlet.http.HttpServletRequest;
16  import javax.servlet.http.HttpServletResponse;
17  
18  import cpath.config.CPathSettings;
19  import cpath.dao.CPathUtils;
20  import cpath.dao.MetadataDAO;
21  import cpath.service.Status;
22  import cpath.warehouse.beans.Metadata;
23  import cpath.warehouse.beans.PathwayData;
24  import cpath.warehouse.beans.Mapping;
25  
26  import org.apache.commons.io.FileUtils;
27  import org.biopax.validator.api.beans.ValidatorResponse;
28  import org.slf4j.Logger;
29  import org.slf4j.LoggerFactory;
30  import org.springframework.core.io.DefaultResourceLoader;
31  import org.springframework.stereotype.Controller;
32  import org.springframework.ui.Model;
33  import org.springframework.web.bind.annotation.ModelAttribute;
34  import org.springframework.web.bind.annotation.PathVariable;
35  import org.springframework.web.bind.annotation.RequestMapping;
36  import org.springframework.web.bind.annotation.RequestMethod;
37  import org.springframework.web.bind.annotation.RequestParam;
38  import org.springframework.web.bind.annotation.ResponseBody;
39  
40  
41  /**
42   * 
43   * @author rodche
44   */
45  @Controller
46  public class MetadataController extends BasicController
47  {
48      
49  	private static final Logger log = LoggerFactory.getLogger(MetadataController.class);   
50  	
51      private final MetadataDAO service; // main PC db access
52      
53      public MetadataController(MetadataDAO service) {
54  		this.service = service;
55  	}
56      
57      
58      /**
59       * Makes current cpath2 instance properies 
60       * available to all (JSP) views.
61       * @return
62       */
63      @ModelAttribute("cpath")
64      public CPathSettings instance() {
65      	return CPathSettings.getInstance();
66      }
67          
68      /* 
69       * As this controller class is mapped to all cpath2 servlets 
70       * (i.e., - to those associated with  /*, *.json, and *.html paths via the web.xml),
71       * we have to avoid ambiguous request mappings and also 
72       * use explicit redirects to .html methods if needed
73       * (i.e, if a method is not supposed to return xml/json objects too)
74       */  
75      
76      
77      //important: home.html (there are several controllers, and /home would create circular redirects)
78      @RequestMapping("/home.html")
79      public String home() {
80      	return "home";
81      }	    
82  
83      
84      /**
85       * Prints the XML schema.
86       * 
87       * @param writer
88       * @throws IOException
89       */
90      @RequestMapping(value="/help/schema")
91      public void getSchema(Writer writer, HttpServletResponse response) 
92      		throws IOException 
93      {
94      	BufferedReader bis = new BufferedReader(new InputStreamReader(
95      		(new DefaultResourceLoader())
96      			.getResource("classpath:cpath/service/schema1.xsd")
97      				.getInputStream(), "UTF-8"));
98      	
99      	response.setContentType("application/xml");
100     	
101     	final String newLine = System.getProperty("line.separator");
102     	String line = null;
103     	while((line = bis.readLine()) != null) {
104     		writer.write(line + newLine);
105     	}
106     }
107     
108 
109     @RequestMapping(value="/help/formats.html")
110     public String getOutputFormatsDescr() 
111     {
112     	return "formats";
113     }
114  
115     
116     //important: datasources.html (there are several controllers, and /datasources would create circular redirects)
117     @RequestMapping("/datasources.html")
118     public String datasources() {
119     	return "datasources";
120     }    
121     
122     
123     @RequestMapping("/metadata/validations") // returns xml or json
124     public @ResponseBody List<ValInfo> queryForValidationInfo() 
125     {
126 		log.debug("Query for all validations summary");
127     	return validationInfo();
128     }
129 
130 
131 	@RequestMapping("/metadata/validations.html") //a JSP view
132     public String queryForValidationInfoHtml(Model model) 
133     {
134 		log.debug("Query for all validations summary (html)");
135     	
136 		//get the list of POJOs:
137     	model.addAttribute("providers", validationInfo());
138     	
139     	return "validations";
140     }
141  
142     
143     @RequestMapping("/metadata/validations/{identifier}.html") //a JSP view
144     public String queryForValidation(
145     		@PathVariable String identifier, Model model, HttpServletRequest request) 
146     {
147 		log.debug("Getting a validation report (html) for:" 
148 				+ identifier);
149 		
150 		logHttpRequest(request);
151 
152     	ValidatorResponse body = service.validationReport(identifier, null);
153 		model.addAttribute("response", body);
154 		
155 		return "validation";
156     }
157 
158     
159     // returns XML or Json 
160     @RequestMapping("/metadata/validations/{identifier}")
161     public @ResponseBody ValidatorResponse queryForValidation(
162     		@PathVariable String identifier, HttpServletRequest request) 
163     {
164 		logHttpRequest(request);
165 		
166     	return service.validationReport(identifier, null);
167     } 
168     
169     
170     // returns XML or Json 
171     @RequestMapping("/metadata/validations/{identifier}/{file}")
172     public @ResponseBody ValidatorResponse queryForValidation(
173     		@PathVariable String identifier, @PathVariable String file,
174     		HttpServletRequest request) 
175     {
176 		logHttpRequest(request);
177     	
178     	return service.validationReport(identifier, file);
179     }
180        
181     
182     @RequestMapping("/metadata/validations/{identifier}/{file}.html") //a JSP view
183     public String queryForValidationByProviderAndFile(
184     		@PathVariable String identifier, @PathVariable String file, 
185     		Model model, HttpServletRequest request) 
186     {
187     	logHttpRequest(request);
188 
189     	ValidatorResponse body = service.validationReport(identifier, file);
190 		model.addAttribute("response", body);
191 		
192 		return "validation";
193     }
194 	
195     
196     @RequestMapping(value = "/metadata/logo/{identifier}")
197     public  @ResponseBody byte[] queryForLogo(@PathVariable String identifier) 
198     		throws IOException 
199     {	
200     	Metadata ds = service.getMetadataByIdentifier(identifier);
201     	byte[] bytes = null;
202     	
203     	if(ds != null) {
204     		bytes = ds.getIcon();
205 			BufferedImage bufferedImage = ImageIO
206 				.read(new ByteArrayInputStream(bytes));
207 			
208 			//resize (originals are around 125X60)
209 			bufferedImage = scaleImage(bufferedImage, 100, 50, null);
210 						
211 			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
212 			ImageIO.write(bufferedImage, "gif", byteArrayOutputStream);
213 			bytes = byteArrayOutputStream.toByteArray();
214 		}
215         
216         return bytes;
217     }
218 
219     
220     @RequestMapping(value = "/favicon.ico")
221     public  @ResponseBody byte[] icon(HttpServletResponse response) 
222     		throws IOException {
223     	
224     	String cpathLogoUrl = CPathSettings.getInstance().getLogoUrl();
225     	
226 		byte[] iconData = null;
227 
228 		try {
229 			BufferedImage image = ImageIO.read(new URL(cpathLogoUrl));
230 			if(image != null) {
231 				image = scaleImage(image, 16, 16, null);
232 				ByteArrayOutputStream baos = new ByteArrayOutputStream();
233 				ImageIO.write(image, "gif", baos);
234 				baos.flush();
235 				iconData = baos.toByteArray();
236 			}
237 		} catch (IOException e) {
238 			errorResponse(Status.INTERNAL_ERROR, 
239 				"Failed to load icon image from " +  cpathLogoUrl, response);
240 			log.error("Failed to load icon image from " +  cpathLogoUrl, e);
241 		}
242 		
243         return iconData;
244     }
245     
246     // to return a xml or json data http response
247     @RequestMapping(value = "/metadata/datasources")
248     public  @ResponseBody List<MetInfo> queryForDatasources() {
249 		log.debug("Getting pathway datasources info.");
250     	
251 		List<MetInfo> list = new ArrayList<MetInfo>();
252 		
253 		for(Metadata m : service.getAllMetadata()) {
254 			MetInfo mi = new MetInfo();
255 			mi.setIdentifier(m.getIdentifier());
256 			mi.setDescription(m.getDescription());
257 			mi.setIcon(m.getIcon());
258 			mi.setName(m.getName());
259 			mi.setType(m.getType().name());
260 			mi.setNotPathwaydata(m.getType().isNotPathwayData());
261 			mi.setUri(m.getUri());
262 			mi.setUrlToHomepage(m.getUrlToHomepage());
263 			mi.getCounts().add(m.getNumPathways());
264 			mi.getCounts().add(m.getNumInteractions());
265 			mi.getCounts().add(m.getNumPhysicalEntities());
266 			list.add(mi);
267 		}
268 		
269     	return list;
270     }
271 
272     
273     /*
274      * Parses cpath2 log files available on the server to 
275      * return a (JSON) map that contains simple counts of how many 
276      * times it was accessed from a particular IP address, 
277      * web service command/path, and which pathway data sources
278      * and how often were associated with the results. 
279      * 
280      * This is internal api (for server owners/developers; 
281      * please do not hit too often...)
282      */
283 	@RequestMapping(value = "/logs/summary", method=RequestMethod.POST)
284     public @ResponseBody Map<String,Integer> stats(Model model, HttpServletRequest request) 
285     		throws IOException 
286     {
287 		logHttpRequest(request);		
288     	// update (unique IP, cmd, datasource, etc.) counts 
289 		// from all there available log files (takes some time to parse)
290     	return CPathUtils.simpleStatsFromAccessLogs();
291     }
292  
293 	
294     @RequestMapping(value = "/downloads.html")
295     public String downloads(Model model, HttpServletRequest request) {
296     	logHttpRequest(request);
297     	
298     	// get the sorted list of files to be shared on the web
299     	String path = CPathSettings.downloadsDir(); 
300     	File[] list = new File(path).listFiles();
301     	
302     	Map<String,String> files = new TreeMap<String,String>();
303     	
304     	for(int i = 0 ; i < list.length ; i++) {
305     		File f = list[i];
306     		String name = f.getName();
307     		long size = f.length();
308     		if(!name.startsWith("."))
309     			files.put(name, FileUtils.byteCountToDisplaySize(size));
310     	}
     	model.addAttribute("files", files.entrySet());
 		
 		return "downloads";
     }
     
     @RequestMapping(value="/idmapping")
     public @ResponseBody Map<String, String> idMapping(@RequestParam String[] id, 
     		HttpServletRequest request, HttpServletResponse response) throws IOException
     {		
 			logHttpRequest(request,"id="+Arrays.toString(id));
 			
 			if(id == null || id.length == 0) {
 				errorResponse(Status.NO_RESULTS_FOUND, "No ID(s) specified.", response);
 				return null;
 			}
			
 			Map<String, String> res = new TreeMap<String, String>();
 
 			for(String i : id) {							
 				Set<String> im = service.mapIdentifier(i, Mapping.Type.UNIPROT, null);
 				if(im.isEmpty())
 					im = service.mapIdentifier(i, Mapping.Type.CHEBI, null);
 				
 				if(im == null) {
 					res.put(i, null);
 				} else {
 					for(String ac : im)
 						res.put(i, ac);
 				}			
 			}		
 
 			return res;
 	}
  
     
     private List<ValInfo> validationInfo() {
     	final List<ValInfo> list = new ArrayList<ValInfo>();
     	
 		for(Metadata m : service.getAllMetadata()) {
 			if(m.getType().isNotPathwayData())
 				continue;
 			
 			ValInfo vi = new ValInfo();
 			vi.setIdentifier(m.getIdentifier());
 			
 			for(PathwayData pd : m.getPathwayData())
 				vi.getFiles().put(pd.getFilename(), pd + "; " + pd.status() + ")");
 			
 			list.add(vi);
 		}

 		return list;
 	}     
     
     /**
      * A POJO for a JSP view (validations).
      * 
      */
     public static final class ValInfo {
     	String identifier;
     	//filename to status/description map
     	Map<String,String> files;
     	
     	public ValInfo() {
 			files = new TreeMap<String,String>();
 		}
     	
     	public String getIdentifier() {
 			return identifier;
 		}

     	public void setIdentifier(String identifier) {
 			this.identifier = identifier;
 		}
     	
     	public Map<String, String> getFiles() {
 			return files;
 		}

     	public void setFiles(Map<String, String> files) {
 			this.files = files;
 		}
     }
     
     /**
      * A POJO for a JSON view (datasources).
      * 
      */   
     public static final class MetInfo {
     	String type;
     	String urlToHomepage;
     	String uri;
     	String description;
     	byte[] icon;
     	String identifier;
     	List<String> name;
     	List<Integer> counts;
     	boolean notPathwaydata;
     	
     	public MetInfo() {
 			counts = new ArrayList<Integer>(3);
 		}
     	
     	public String getType() {
 			return type;
 		}

 		public void setType(String type) {
 			this.type = type;
 		}
 
 		public String getUrlToHomepage() {
 			return urlToHomepage;
 		}

 		public void setUrlToHomepage(String urlToHomepage) {
 			this.urlToHomepage = urlToHomepage;
 		}
 
 		public String getUri() {
 			return uri;
 		}

 		public void setUri(String uri) {
 			this.uri = uri;
 		}

 		public String getDescription() {
 			return description;
 		}

 		public void setDescription(String description) {
 			this.description = description;
 		}

 		public byte[] getIcon() {
 			return icon;
 		}

 		public void setIcon(byte[] icon) {
 			this.icon = icon;
 		}
 
 		public String getIdentifier() {
 			return identifier;
 		}

 		public void setIdentifier(String identifier) {
 			this.identifier = identifier;
 		}
 
 		public List<String> getName() {
 			return name;
 		}

 		public void setName(List<String> name) {
 			this.name = name;
 		}
 
 		public List<Integer> getCounts() {
 			return counts;
 		}

 		public void setCounts(List<Integer> counts) {
 			this.counts = counts;
 		}  
 		
 		public boolean isNotPathwaydata() {
 			return notPathwaydata;
 		}

 		public void setNotPathwaydata(boolean pathwayData) {
 			this.notPathwaydata = pathwayData;
 		}
     }     
}
