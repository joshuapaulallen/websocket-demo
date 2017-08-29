package org.yourotherleft.websocketdemo.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class EchoMessage {

	private final String message;
	private final Date date;

	@JsonCreator
	public EchoMessage(@JsonProperty("message") String message, @JsonProperty("date") Date date) {
		this.message = message;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		EchoMessage that = (EchoMessage) o;

		if (message != null ? !message.equals(that.message) : that.message != null) {
			return false;
		}
		return date != null ? date.equals(that.date) : that.date == null;
	}

	@Override
	public int hashCode() {
		int result = message != null ? message.hashCode() : 0;
		result = 31 * result + (date != null ? date.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "EchoMessage{" +
			"message='" + message + '\'' +
			", date=" + date +
			'}';
	}
}
