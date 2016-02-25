package wsd_api;

import java.io.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import net.tanesha.recaptcha.ReCaptchaResponse;

public class WSDAPI {
	
	private static final String endpoint = "http://www.whysodanish.com/WebAPI/api.php";
	private static URL url;
	private static CookieManager cookieManager = new CookieManager();
	private static WSDAPI api = null;
	private HttpURLConnection conn;
	private File savefile;
	private String savedsession = "";
	
	/**
	 * Gets the singleton instance for this API.
	 * @return WSDAPI instance.
	 */
	public static WSDAPI getInstance() {
		if (api == null) {
			api = new WSDAPI();
		}
		return api;
	}
	
	private WSDAPI() {
		try {
			url = new URL(endpoint);
			savefile = new File(System.getenv("APPDATA") + "/WSD/saveconfig.tmp");
			if (!savefile.exists()) {
				new File(System.getenv("APPDATA") + "/WSD/").mkdirs();
				savefile.createNewFile();
			}
			CookieHandler.setDefault(cookieManager);
			FileReader r = new FileReader(savefile);
			for (int c = r.read(); c != -1; c = r.read()) {
				savedsession += (char)c;
			}
			r.close();
			cookieManager.getCookieStore().add(new URI(endpoint), new HttpCookie("ForumSecurity", savedsession));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private String read(String postParams) {
		String ret = "";
		try {
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			if (savedsession != null) {
				conn.setRequestProperty("Cookie", "ForumSecurity=" + savedsession);
			}
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", Integer.toString(postParams.getBytes("UTF-8").length));
			conn.getOutputStream().write(postParams.getBytes("UTF-8"));
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			for (int c = in.read(); c != -1; c = in.read()) {
				ret += (char)c;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private void saveSession(String session) {
		try {
			PrintWriter p = new PrintWriter(savefile);
			p.print(session);
			p.close();
		} catch (FileNotFoundException e) {}
	}
	
	/**
	 * Attempts to login using supplied username and password.
	 * @param username
	 * @param password
	 * @return response with user info if success, otherwise error message.
	 */
	public APIResponse login(String username, String password) {
		try {
			JSONObject response = new JSONObject(read("method=login&username=" +
					URLEncoder.encode(username, "UTF-8") + "&password=" +
					URLEncoder.encode(password, "UTF-8")));
			if (response.has("session") && !response.getString("session").isEmpty()) {
				saveSession(response.getString("session"));
			}
			return new APIResponse(response);
		} catch (UnsupportedEncodingException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Logs out the currently logged in user, and returns success if we could log out.
	 * @return response with success and message.
	 */
	public APIResponse logout() {
		try {
			saveSession("");
			return new APIResponse(new JSONObject(read("method=logout")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Receives public information shown on the profile of an user.
	 * @param userId
	 * @return info on profile
	 */
	public APIResponse getProfile(int userId) {
		try {
			return new APIResponse(new JSONObject(read("method=profile&id=" + userId)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempts to register a new account using the specified parameters, as seen on http://www.whysodanish.com/Forum/register.php
	 * @param username
	 * @param password
	 * @param email
	 * @return response whether the account could be registered, if captcha is valid.
	 */
	public APIResponse register(String username, String password, String email, ReCaptchaResponse captcha) {
		if (captcha.isValid()) {
			try {
				return new APIResponse(new JSONObject(read("method=register&username=" +
						URLEncoder.encode(username, "UTF-8") + "&password=" +
						URLEncoder.encode(password, "UTF-8") + "&email=" +
						URLEncoder.encode(email, "UTF-8"))));
			} catch (UnsupportedEncodingException | JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Gets user information of the currently logged in user.
	 * @return response with user info if success, otherwise error message.
	 */
	public APIResponse getUser() {
		try {
			JSONObject response = new JSONObject(read("method=getUser"));
			if (response.has("session") && !response.getString("session").isEmpty()) {
				saveSession(response.getString("session"));
			}
			return new APIResponse(response);
		} catch (JSONException e) {
		}
		return null;
	}
	
	/**
	 * Non-yielding method for checking if you are currently logged in.
	 * @return boolean showing if you are currently logged in.
	 */
	public boolean isOnline() {
		for(HttpCookie c : cookieManager.getCookieStore().getCookies()) {
			if (c.getPath().equals(endpoint) && c.getName().equals("ForumSecurity") && c.getValue().length() > 12) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Mock-up function for generating a random password fulfilling the password requirements.
	 * @return password
	 */
	public String generatePassword() {
		int l_len = (int)(4 + Math.random() * 3);
		int u_len = (int)(3 + Math.random() * 3);
		int d_len = (int)(3 + Math.random() * 3);
		int l_cur = 0;
		int u_cur = 0;
		int d_cur = 0;
		String r = "";
		while (l_cur + u_cur + d_cur < l_len + u_len + d_len) {
			switch ((int)(Math.random() * 3)) {
			case 0:
				if (l_cur < l_len) {
					l_cur++;
					r += (char)(97 + (int)(Math.random() * (123 - 97)));
				}
				break;
			case 1:
				if (u_cur < u_len) {
					u_cur++;
					r += (char)(65 + (int)(Math.random() * (91 - 65)));
				}
				break;
			case 2:
				if (d_cur < d_len) {
					d_cur++;
					r += (char)(48 + (int)(Math.random() * (58 - 48)));
				}
				break;
			}
		}
		return r;
	}
	
}
