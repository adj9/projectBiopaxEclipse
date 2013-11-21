package cpath.webservice;

View Javadoc

1   /**
2    ** Copyright (c) 2010 Memorial Sloan-Kettering Cancer Center (MSKCC)
3    ** and University of Toronto (UofT).
4    **
5    ** This is free software; you can redistribute it and/or modify it
6    ** under the terms of the GNU Lesser General Public License as published
7    ** by the Free Software Foundation; either version 2.1 of the License, or
8    ** any later version.
9    **
10   ** This library is distributed in the hope that it will be useful, but
11   ** WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
12   ** MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
13   ** documentation provided hereunder is on an "as is" basis, and
14   ** both UofT and MSKCC have no obligations to provide maintenance, 
15   ** support, updates, enhancements or modifications.  In no event shall
16   ** UofT or MSKCC be liable to any party for direct, indirect, special,
17   ** incidental or consequential damages, including lost profits, arising
18   ** out of the use of this software and its documentation, even if
19   ** UofT or MSKCC have been advised of the possibility of such damage.  
20   ** See the GNU Lesser General Public License for more details.
21   **
22   ** You should have received a copy of the GNU Lesser General Public License
23   ** along with this software; if not, write to the Free Software Foundation,
24   ** Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA;
25   ** or find it at http://www.fsf.org/ or http://www.gnu.org.
26   **/
27  
28  package cpath.webservice;
29  
import java.io.IOException;
import java.io.Writer;
import java.util.*;
  
import cpath.config.CPathSettings;
import cpath.service.CPathService;
import cpath.service.ErrorResponse;
import cpath.service.GraphType;
import cpath.service.OutputFormat;
import cpath.service.Status;
import cpath.service.jaxb.*;
import cpath.webservice.args.Get;
import cpath.webservice.args.Traverse;
import cpath.webservice.args.binding.BiopaxTypeEditor;
import cpath.webservice.args.binding.OutputFormatEditor;
  
import org.biopax.paxtools.model.level3.Protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
  
  /**
   * cPathSquared Model Access Web Service.
   * 
   * @author rodche
   */
  @Controller
  public class BiopaxModelController extends BasicController {
  	private static final Logger log = LoggerFactory.getLogger(BiopaxModelController.class);    
  	
      private static final String xmlBase = CPathSettings.xmlBase();
      
      private CPathService service; // main PC db access
 	
   public BiopaxModelController(CPathService service) {
  		this.service = service;
  	}
     
      /**
  	 * This configures the web request parameters binding, i.e., 
  	 * conversion to the corresponding java types; for example,
  	 * "neighborhood" is recognized as {@link GraphType#NEIGHBORHOOD},  
  	 *  "protein" - {@link Protein} , etc.
  	 *  Depending on the editor, illegal query parameters may result 
  	 *  in an error or just NULL value.
  	 * 
  	 * @param binder
  	 */
  	@InitBinder
      public void initBinder(WebDataBinder binder) {
          binder.registerCustomEditor(OutputFormat.class, new OutputFormatEditor());
          binder.registerCustomEditor(Class.class, new BiopaxTypeEditor());
      }
  	
  	
  	/**
  	 * This is a convenience method is to make cpath2 data 
  	 * more suitable for LinkedData / Semantic Web, by 
  	 * resolving cpath2-generated URIs (created in the data 
  	 * warehouse and during validation and normalization). 
  	 * Ideally, all URIs are to be resolvable URLs.
  	 * 
  	 * Normally, one should use #elementById(Get, BindingResult, Writer, HttpServletRequest, HttpServletResponse).
 	 * 
 	 * @param localId - the part of URI following xml:base
 	 * 
 	 * TODO return a summary (view) instead of plain raw BioPAX content.
 	 */
 	@RequestMapping(method=RequestMethod.GET, value="/{localId}")
 	public void cpathIdInfo(@PathVariable String localId, Writer writer, 
 			HttpServletRequest request, HttpServletResponse response) 
 					throws Exception 
 	{
 			/* A hack (specific to our normalizer and also 
 			 * might not work for all client links/browsers...
 			 * a better solution would be never generate tricky URIs,
 			 * containing encoded sharps, colons, spaces, etc.): 
 			 * the 'localId' parameter is usually un-encoded by the frameworks;
 			 * so we need to encode ":","#"," " back to 
 			 * %3A, %23, and "+" respectively, to get the original URI
 			 * (otherwise, if we simply combine localId and xml:base, the 
 			 * the resulting "URI" will be non-existing or wrong one)
 			 */
 			if(localId.startsWith("#"))
 				localId = localId.substring(1);
 				
 			if(localId.contains(":") || localId.contains("#") || localId.contains(" "))
 				localId = localId
 						.replaceAll(":", "%3A")
 							.replaceAll("#", "%23")
 								.replaceAll(" ", "+");
 			Get get = new Get();			
 			get.setUri(new String[]{xmlBase + localId});
 			elementById(get, null, writer, request, response);			
 	}
 
 	// Get by ID (URI) command
     @RequestMapping("/get")
     public void elementById(@Valid Get get, BindingResult bindingResult, 
     	Writer writer, HttpServletRequest request, HttpServletResponse response) 
     		throws IOException {
     	logHttpRequest(request, "format="+get.getFormat(), (get.getUri().length<6)?"uri="+Arrays.toString(get.getUri()):"uri=...(>5)");
     	
     	if(bindingResult != null &&  bindingResult.hasErrors()) {
     		errorResponse(Status.BAD_REQUEST, 
     				errorFromBindingResult(bindingResult), response);
     	} else {
 			OutputFormat format = get.getFormat();
 			String[] uri = get.getUri();

log.debug("Query: /get; format:" + format + ", urn:" + Arrays.toString(uri));
 
 			ServiceResponse result = service.fetch(format, uri);
 			stringResponse(result, writer, response);
 		}
     }  
 


 	@RequestMapping("/top_pathways")
     public @ResponseBody SearchResponse topPathways(
     		@RequestParam(required=false) String[] datasource, @RequestParam(required=false) String[] organism, 
     		HttpServletRequest request, HttpServletResponse response) throws IOException
     {
 		logHttpRequest(request, "organisms="+Arrays.toString(organism), "datasource="+Arrays.toString(datasource));
 		
 		SearchResponse results = service.topPathways(organism, datasource);

 		if(results == null || results.isEmpty()) {
 			errorResponse(Status.NO_RESULTS_FOUND, "no hits", response);
 		} else {
 			return (SearchResponse) results;
 		}
 		
 		return null;
     }
     
     
     @RequestMapping("/traverse")
     public @ResponseBody ServiceResponse traverse(@Valid Traverse query, 
     	BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) 
     		throws IOException 
     {
     	logHttpRequest(request, "path="+query.getPath());
     	
     	if(bindingResult.hasErrors()) {
     		errorResponse(Status.BAD_REQUEST, 
     				errorFromBindingResult(bindingResult), response);
     	} else {
     		ServiceResponse sr = service.traverse(query.getPath(), query.getUri());
     		if(sr instanceof ErrorResponse) {
 				errorResponse(((ErrorResponse) sr).getStatus(), 
 						((ErrorResponse) sr).toString(), response);
 			}
     		else if(sr == null || sr.isEmpty()) {
     			errorResponse(Status.NO_RESULTS_FOUND, "no results found", response);
     		}
     		else {
 				return sr;
 			}
     	}
     	return null;
     }
     
}
