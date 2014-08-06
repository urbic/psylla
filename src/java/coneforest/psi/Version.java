package coneforest.psi;

public class Version
{
	public static String getVersion()
	{
		return
			major+"."+minor+"."+revision+((type==null || type.length()==0)? "": " "+type);
    }

    public static int getMajor()
	{
		return major;
	}

    public static int getMinor()
	{
		return minor;
	}

    public static int getRevision()
	{
		return revision;
	}

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
				(Version.class.getResourceAsStream("version.properties"));
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
