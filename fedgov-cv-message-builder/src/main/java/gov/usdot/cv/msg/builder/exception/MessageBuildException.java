package gov.usdot.cv.msg.builder.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MessageBuildException extends WebApplicationException {

	private static final long serialVersionUID = 8554885441015667079L;

	public MessageBuildException(String message) {
		super(Response.status(Response.Status.BAD_REQUEST)
				.entity(message).type(MediaType.APPLICATION_JSON).build());
	}

}
