package wsd_api;

import org.json.JSONException;
import org.json.JSONObject;

public class APIResponse {
	
	private boolean success = false;
	private JSONObject message;
	
	public APIResponse(JSONObject message) {
		try {
			this.success = message.getBoolean("success");
		} catch (JSONException e) {}
		this.message = message;
	}
	
	public boolean success() {
		return success;
	}
	
	public int getID() {
		try {
			return message.getInt("ID");
		} catch (JSONException e) {
			return 0;
		}
	}
	
	public String getError() {
		if (!success) {
			try {
				return message.getString("message");
			} catch (JSONException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public String getUsername() {
		try {
			return message.getString("name");
		} catch (JSONException e) {
			return null;
		}
	}
	
	public int getInt(String param) {
		try {
			return message.getInt(param);
		} catch (JSONException e) {
			return 0;
		}
	}
	
	public APIResponse getList(String listName) {
		try {
			if (message.get(listName) instanceof APIResponse) {
				return (APIResponse)message.get(listName);
			} else {
				return null;
			}
		} catch (JSONException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		try {
			return "[APIResponse]" + message.toString(2);
		} catch (JSONException e) {
			return "[APIResponse]{}";
		}		
	}
	
}
