package coneforest.psi;

/**
*	A class holding versioning information.
*/
public class Version
{
	/**
	*	Get version string.
	*
	*	@return major version number.
	*/
	public static String getVersion()
	{
		return
			major+"."+minor+"."+revision+((type==null || type.length()==0)? "": " "+type);
	}

	/**
	*	Get major version number.
	*
	*	@return major version number.
	*/
	public static int getMajor()
	{
		return major;
	}

	/**
	*	Get minor version number.
	*
	*	@return minor version number.
	*/
	public static int getMinor()
	{
		return minor;
	}

	/**
	*	Get revision version number.
	*
	*	@return revision version number.
	*/
	public static int getRevision()
	{
		return revision;
	}

	/**
	*	Get release type.
	*
	*	@return release type.
	*/
	public static String getReleaseType()
	{
		return type;
	}

	static
	{
		try
		{
			java.util.Properties versionProperties=new java.util.Properties();
			versionProperties.load
				(Version.class.getResourceAsStream("Version.properties"));
			major=Integer.parseInt(versionProperties.getProperty("version.major"));
			minor=Integer.parseInt(versionProperties.getProperty("version.minor"));
			revision=Integer.parseInt(versionProperties.getProperty("version.revision"));
		}
		catch(java.io.IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	private static int major, minor, revision;
	private static String type=null;
}
