package coneforest.psylla;

/**
*	A class holding versioning information.
*/
public class Version
{
	/**
	*	Get the version string.
	*
	*	@return the version string.
	*/
	public static String getVersion()
	{
		final var type=getReleaseType();
		return
			getMajor()+"."+getMinor()+"."+getRevision()
				+((type==null || type.length()==0)? "": "-"+type);
	}

	/**
	*	Get the major version number.
	*
	*	@return the major version number.
	*/
	public static int getMajor()
	{
		return Integer.parseInt(Config.getProperty("project.version.major"));
	}

	/**
	*	Get the minor version number.
	*
	*	@return the minor version number.
	*/
	public static int getMinor()
	{
		return Integer.parseInt(Config.getProperty("project.version.minor"));
	}

	/**
	*	Get the revision version number.
	*
	*	@return the revision version number.
	*/
	public static int getRevision()
	{
		return Integer.parseInt(Config.getProperty("project.version.revision"));
	}

	/**
	*	Get the release type.
	*
	*	@return the release type.
	*/
	public static String getReleaseType()
	{
		return Config.getProperty("project.version.type");
	}
}
