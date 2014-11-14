package core.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/version")
public class version {

		@GET
		@Produces(MediaType.TEXT_HTML)
		public String returnTitle(){
			return "<p>Central Portal Server v0.8<p>";
		}
		

}
