package com.edgeburnmedia.ipapi.ipapi.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.Nullable;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class IpApiResponse {
	private final boolean success;
	@Nullable
	private final String message;
	private final String country;
	private final String regionName;
	private final String city;
	private final String zip;
	private final float lat;
	private final float lon;
	private final ZoneId timezone;
	private final LocalDateTime localtime;
	private final String internetServiceProvider;
	private final String organization;
	private final String asname;
	private final boolean mobile;
	private final boolean proxy;
	private final boolean hosting;
	private final String query;

public IpApiResponse(boolean success, @Nullable String message, String country, String region, String city, String zip, float lat, float lon, ZoneId timezone, String internetServiceProvider, String organization, String asname, boolean mobile, boolean proxy, boolean hosting, String query) {
		this.success = success;
		this.message = message;
		this.country = country;
		this.regionName = region;
		this.city = city;
		this.zip = zip;
		this.lat = lat;
		this.lon = lon;
		this.timezone = timezone;
		this.localtime = LocalDateTime.now(timezone);
		this.internetServiceProvider = internetServiceProvider;
		this.organization = organization;
		this.asname = asname;
		this.mobile = mobile;
		this.proxy = proxy;
		this.hosting = hosting;
		this.query = query;
	}

	public static IpApiResponse getResponse(IpApiRequest request) {
		JsonObject response;
		try {
			URL url = new URL("http://ip-api.com/json/" + request.getIp() + "?fields=" + IpApiRequest.FIELDS);
			InputStreamReader reader = new InputStreamReader(url.openStream());
			response = new Gson().fromJson(reader, JsonObject.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new IpApiResponse(
				response.get("status").getAsString().equals("success"),
				response.get("message") == null ? null : response.get("message").getAsString(),
				response.get("country").getAsString(),
				response.get("regionName").getAsString(),
				response.get("city").getAsString(),
				response.get("zip").getAsString(),
				response.get("lat").getAsFloat(),
				response.get("lon").getAsFloat(),
				ZoneId.of(response.get("timezone").getAsString()),
				response.get("isp").getAsString(),
				response.get("org") == null ? null : response.get("org").getAsString(),
				response.get("asname").getAsString(),
				response.get("mobile").getAsBoolean(),
				response.get("proxy").getAsBoolean(),
				response.get("hosting").getAsBoolean(),
				response.get("query").getAsString()
		);
	}

	/**
	 * Returns true if the request was successful.
	 * @return true if the request was successful.
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Returns the message if the request was not successful.
	 * @return the message if the request was not successful. Null if the request was successful.
	 */
	@Nullable
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the country of the IP address.
	 * @return the country of the IP address.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Returns the region of the IP address. This is usually the state or province.
	 * @return the region of the IP address.
	 */
	public String getRegion() {
		return regionName;
	}

	/**
	 * Returns the city of the IP address.
	 * @return the city of the IP address.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the zip/postal code of the IP address.
	 * @return the zip/postal code of the IP address.
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Gets the postal code of the IP address.
	 * @return the postal code of the IP address.
	 */
	public String getPostalCode() {
		return zip;
	}

	/**
	 * Returns the latitude of the IP address.
	 * @return the latitude of the IP address.
	 */
	public float getLat() {
		return lat;
	}

	/**
	 * Returns the longitude of the IP address.
	 * @return the longitude of the IP address.
	 */
	public float getLon() {
		return lon;
	}

	/**
	 * Returns the timezone of the IP address. This is a {@link ZoneId} object.
	 * @return the timezone of the IP address.
	 */
	public ZoneId getTimezone() {
		return timezone;
	}

	/**
	 * Returns the current time in the timezone of the IP address. This is a {@link LocalDateTime} object.
	 * @return the current time in the timezone of the IP address.
	 */
	public LocalDateTime getLocaltime() {
		return localtime;
	}

	/**
	 * Returns the internet service provider of the IP address.
	 * @return the internet service provider of the IP address.
	 */
	public String getInternetServiceProvider() {
		return internetServiceProvider;
	}

	/**
	 * Returns the organization of the IP address.
	 * @return the organization of the IP address.
	 */
	@Nullable
	public String getOrganization() {
		return organization;
	}

	/**
	 * Returns the AS name of the IP address. AS name (RIR). Empty for IP blocks not being announced in BGP tables.
	 * @return
	 */
	public String getAsname() {
		return asname;
	}

	/**
	 * Returns true if the IP address is a mobile or cellular IP address.
	 * @return true if the IP address is a mobile or cellular IP address.
	 */
	public boolean isMobile() {
		return mobile;
	}

	/**
	 * Returns true if the IP address is a proxy, VPN, or Tor exit node.
	 * @return true if the IP address is a proxy, VPN, or Tor exit node.
	 */
	public boolean isProxy() {
		return proxy;
	}

	/**
	 * Returns true if the IP address is a hosting provider. This is usually a data center.
	 * @return true if the IP address is a hosting provider.
	 */
	public boolean isHosting() {
		return hosting;
	}

	/**
	 * Returns the IP address that was queried. This is the same as the IP address that was passed to the request.
	 * @return the IP address that was queried.
	 */
	public String isQuery() {
		return query;
	}

	public BaseComponent[] toMinecraftChat() {
		return new ComponentBuilder().append("Geolocation for ").color(ChatColor.GRAY).append(query).color(ChatColor.GOLD).append("\n")
			.append("Country: ").color(ChatColor.GRAY).append(getCountry()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Region: ").color(ChatColor.GRAY).append(getRegion()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("City: ").color(ChatColor.GRAY).append(getCity()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Zip: ").color(ChatColor.GRAY).append(getZip()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Timezone: ").color(ChatColor.GRAY).append(getTimezone().toString()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Local Time: ").color(ChatColor.GRAY).append(getLocaltime().getHour() + ":" + getLocaltime().getMinute()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Internet Service Provider: ").color(ChatColor.GRAY).append(getInternetServiceProvider()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Organization: ").color(ChatColor.GRAY).append(getOrganization()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("AS Name: ").color(ChatColor.GRAY).append(getAsname()).color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Mobile: ").color(ChatColor.GRAY).append(isMobile() ? "Yes" : "No").color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Proxy: ").color(ChatColor.GRAY).append(isProxy() ? "Yes" : "No").color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Hosting: ").color(ChatColor.GRAY).append(isHosting() ? "Yes" : "No").color(ChatColor.LIGHT_PURPLE).append("\n")
			.append("Geocooridinates: ").color(ChatColor.GRAY).append(getLat() + ", " + getLon()).color(ChatColor.BLUE).underlined(true).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lon)).event(new HoverEvent(
				HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to open in Google Maps").color(ChatColor.GRAY).create())).append("\n")
			.create();
	}

	@Override
	public String toString() {
		return "IpApiResponse{" +
			"success=" + success +
			", message='" + message + '\'' +
			", country='" + country + '\'' +
			", regionName='" + regionName + '\'' +
			", city='" + city + '\'' +
			", zip='" + zip + '\'' +
			", lat=" + lat +
			", lon=" + lon +
			", timezone=" + timezone +
			", localtime=" + localtime +
			", internetServiceProvider='" + internetServiceProvider + '\'' +
			", organization='" + organization + '\'' +
			", asname='" + asname + '\'' +
			", mobile=" + mobile +
			", proxy=" + proxy +
			", hosting=" + hosting +
			", query='" + query + '\'' +
			'}';
	}
}
