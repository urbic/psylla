package coneforest.psylla.runtime;

/**
*	Methods for obtaining version information.
*/
public interface Version
{
	/**
	*	Returns the version string.
	*
	*	@return the version string.
	*/
	public static String getVersion()
	{
		final var type=getReleaseType();
		return
			getMajor()+"."+getMinor()+"."+getMicro()
				+((type==null || type.length()==0)? "": "-"+type);
	}

	/**
	*	Returns the major version number.
	*
	*	@return the major version number.
	*/
	public static int getMajor()
	{
		return Integer.parseInt(Config.getProperty("project.version.major"));
	}

	/**
	*	Returns the minor version number.
	*
	*	@return the minor version number.
	*/
	public static int getMinor()
	{
		return Integer.parseInt(Config.getProperty("project.version.minor"));
	}

	/**
	*	Returns the micro version number.
	*
	*	@return the micro version number.
	*/
	public static int getMicro()
	{
		return Integer.parseInt(Config.getProperty("project.version.micro"));
	}

	/**
	*	Returns the version encoded as a string.
	*
	*	@return the version encoded as a string.
	*/
	public static String getCode()
	{
		return Character.toString((char)getMajor())
				+Character.toString((char)getMinor())
				+Character.toString((char)getMicro());
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
