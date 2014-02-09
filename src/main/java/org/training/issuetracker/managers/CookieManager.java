package org.training.issuetracker.managers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that manages cookies
 * @author Dzmitry Salnikau
 * @since 07.02.2014
 */
public final class CookieManager {
	
	public final static String NAME_LOGIN = "login";
	
	/**
	 * Creates cookie with received name and value
	 * Lifetime - as default in config.properties
	 * @param response - HttpServletResponse
	 * @param name - name of the cookie
	 * @param value - value of this cookie
	 */
	public void setCookieValue(HttpServletResponse response, String name, String value){
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(Integer.valueOf(ConfigurationManager.getInstance().getProperty(
				ConfigurationManager.COOKIE_LIFE_TIME)));
		response.addCookie(cookie);
	}
	
	/**
	 * Returns the value of the cookie with the specified name
	 * @param request - HttpServletRequest
	 * @param name - name of the cookie
	 * @return String - value of the cookie or null
	 */
	public String getCookieValue(HttpServletRequest request, String name){
		Cookie[] cookies = request.getCookies();
		String cookieValue = null;
		if(cookies != null){
			for(Cookie cookie: cookies){
				if(name.equals(cookie.getName())){
					cookieValue = cookie.getValue();
				}
			}
		}
		return cookieValue;
	}
	
	/**
	 * Creates cookie with zero-lifetime and received name
	 * Thus cookie with received name is removed
	 * @param response - HttpServletResponse
	 * @param name - name of the cookie
	 */
	public void removeCookieValue(HttpServletResponse response, String name){
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}