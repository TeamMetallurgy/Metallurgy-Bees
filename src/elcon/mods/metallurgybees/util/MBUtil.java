package elcon.mods.metallurgybees.util;

public class MBUtil {

	public static String firstUpperCase(String s) {
		return Character.toString(s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}
}
