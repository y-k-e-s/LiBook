package constants;

public enum BookGenre {
	IT("IT"),
	SELF_HELP("Self Help"),
	MANAGEMENT("Management"),
	LANGUAGE("Language"),
	HR("HR"),
	NOT_SPECIFIED("Not Specified");
	
	String name;
	BookGenre(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
