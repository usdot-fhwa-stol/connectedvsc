package gov.usdot.cv.msg.builder.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MessageEncodeException extends WebApplicationException {

	private static final long serialVersionUID = -3522396012125388779L;

	public MessageEncodeException(String message) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(message).type(MediaType.APPLICATION_JSON).build());
	}

}
