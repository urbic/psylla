package coneforest.psi;

public class Config
{
	private Config()
	{
	}

	public static String getProperty(final String name)
	{
		return config.getProperty(name);
	}

	public static java.util.Set<String> stringPropertyNames()
	{
		return config.stringPropertyNames();
	}

	private static final java.util.Properties config=new java.util.Properties();

	static
	{
		try
		{
			config.load(Psylla.class.getResourceAsStream("Config.properties"));
		}
		catch(java.io.IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}
