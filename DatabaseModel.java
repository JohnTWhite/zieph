package WordCount;

public class DatabaseModel {
	
	private String title, authorFirstName, authorLastname, fiction, genere;

	public DatabaseModel(String title, String authorFirstName, String authorLastname, String fiction, String genere) {
		super();
		this.title = title;
		this.authorFirstName = authorFirstName;
		this.authorLastname = authorLastname;
		this.fiction = fiction;
		this.genere = genere;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public String getAuthorLastname() {
		return authorLastname;
	}

	public String getFiction() {
		return fiction;
	}

	public String getGenere() {
		return genere;
	}


}
