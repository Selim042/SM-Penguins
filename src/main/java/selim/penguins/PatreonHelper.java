package selim.penguins;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.UUID;

public class PatreonHelper {

	private static boolean init = false;
	private static final String PATRON_URL = "http://myles-selim.us/modInfo/patrons.txt";
	private static final HashMap<UUID, PatronType> PATRONS = new HashMap<UUID, PatronType>();

	public static final void main(String... args) {
		init();
		System.out.println("Patron types:");
		for (PatronType t : PatronType.PATRON_TYPES)
			System.out.println(" - " + t.getName() + ":" + Integer.toHexString(t.getColor()));
		System.out.println("Patrons:");
		for (Entry<UUID, PatronType> e : PATRONS.entrySet())
			System.out.println(" - " + e.getKey() + ":" + e.getValue());
	}

	public static void init() {
		if (init)
			return;
		init = true;
		try {
			URL url = new URL(PATRON_URL);
			// BufferedReader reader = new BufferedReader(url.openStream());
			InputStream stream = url.openStream();
			Scanner scanner = new Scanner(stream);
			if (!scanner.hasNext()) {
				scanner.close();
				return;
			}
			String firstLine = scanner.nextLine();
			String[] types = firstLine.split(":");
			// System.out.println(firstLine);
			for (String t : types) {
				// System.out.println(t);
				String[] info = t.split(",");
				if (info.length != 2)
					continue;
				new PatronType(info[0], Integer.decode(info[1]));
			}
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				// System.out.println(line);
				String[] parts = line.split(":");
				if (parts.length < 2)
					continue;
				String uuidS = parts[0];
				String typeS = parts[1];
				UUID uuid = UUID.fromString(uuidS);
				PatronType patronType = PatronType.getOrCreateType(typeS);
				if (uuid == null || patronType == null)
					continue;
				PATRONS.put(uuid, patronType);
			}
			scanner.close();
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public static boolean isPatron(UUID uuid) {
		return PATRONS.containsKey(uuid);
	}

	public static PatronType getPatronType(UUID uuid) {
		if (!isPatron(uuid))
			return null;
		return PATRONS.get(uuid);
	}

	public static int getPatronColor(UUID uuid) {
		if (!isPatron(uuid))
			return -1;
		return getPatronType(uuid).getColor();
	}

	public static class PatronType {

		private static final List<PatronType> PATRON_TYPES = new LinkedList<PatronType>();

		private final String name;
		private final int color;

		protected PatronType(String name, int color) {
			this.name = name;
			this.color = color;
			PATRON_TYPES.add(this);
		}

		public String getName() {
			return this.name;
		}

		public int getColor() {
			return this.color;
		}

		public static PatronType getOrCreateType(String name) {
			for (PatronType pt : PATRON_TYPES)
				if (pt.name.equalsIgnoreCase(name))
					return pt;
			try {
				int color = Integer.decode(name);
				return new PatronType(name, color);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		public static PatronType[] getTypes() {
			return PATRON_TYPES.toArray(new PatronType[0]);
		}

		@Override
		public String toString() {
			return this.getName() + ":" + this.getColor();
		}

	}

}
