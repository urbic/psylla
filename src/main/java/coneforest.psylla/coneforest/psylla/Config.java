package coneforest.psylla;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
*	Methods for obtaining configuration properties stored in the {@code
*	Config.properties} resource.
*/
public class Config
{
	private Config()
	{
	}

	/**
	*	Returns the value associated with the given name. If not found, returns
	*	{@code null}.
	*
	*	@param name the property name.
	*	@return the value with the specified name.
	*/
	public static String getProperty(final String name)
	{
		return config.getProperty(name);
	}

	/**
	*	Returns an unmodifiable set of property names.
	*
	*	@return an unmodifiable set of property names.
	*/
	public static Set<String> stringPropertyNames()
	{
		return config.stringPropertyNames();
	}

	private static final Properties config=new Properties();

	static
	{
		try
		{
			config.load(Config.class.getResourceAsStream("Config.properties"));
		}
		catch(final IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}