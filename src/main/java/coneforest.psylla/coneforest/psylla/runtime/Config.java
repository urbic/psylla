package coneforest.psylla.runtime;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
*	Methods for obtaining configuration properties stored in the {@code Config.properties} resource.
*/
public class Config
{
	private static final Properties CONFIG=new Properties();

	private Config()
	{
	}

	/**
	*	{@return the value associated with the given name} If not found, returns {@code null}.
	*
	*	@param name the property name.
	*/
	public static String getProperty(final String name)
	{
		return CONFIG.getProperty(name);
	}

	/**
	*	{@return an unmodifiable set of property names}
	*/
	public static Set<String> stringPropertyNames()
	{
		return CONFIG.stringPropertyNames();
	}

	static
	{
		try(final var resourceStream=Config.class.getResourceAsStream("Config.properties"))
		{
			CONFIG.load(resourceStream);
		}
		catch(final IOException ex)
		{
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}
}
