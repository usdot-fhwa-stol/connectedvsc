package gov.usdot.cv.msg.builder.message;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SemiMessage {
	
	private long dialogId;
	private String messageName;
	private String hexString;
	private String readableString;
	
	public SemiMessage() {
		super();
	}

	public SemiMessage(long dialogId, String messageName) {
		super();
		this.dialogId = dialogId;
		this.messageName = messageName;
	}

	public long getDialogId() {
		return dialogId;
	}

	public void setDialogId(long dialogId) {
		this.dialogId = dialogId;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getHexString() {
		return hexString;
	}

	public void setHexString(String hexString) {
		this.hexString = hexString;
	}

	public String getReadableString() {
		return readableString;
	}

	public void setReadableString(String readableString) {
		this.readableString = readableString;
	}

	@Override
	public String toString() {
		return "SemiMessage [dialogId=" + dialogId + ", messageName="
				+ messageName + ", hexString=" + hexString
				+ ", readableString=" + readableString + "]";
	}
	
}
