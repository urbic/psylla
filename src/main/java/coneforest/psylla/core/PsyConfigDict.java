package coneforest.psylla.core;
import coneforest.psylla.*;

public class PsyConfigDict
	extends PsyModule
{
	public PsyConfigDict()
	{
		//super("config");

		for(String name: Config.stringPropertyNames())
			put(name, new PsyName(Config.getProperty(name)));

		final java.util.Properties systemProperties=System.getProperties();
		for(String name: systemProperties.stringPropertyNames())
			put(name, new PsyName(systemProperties.getProperty(name)));
	}
}
