package coneforest.psylla.core;

import coneforest.psylla.runtime.Config;
import java.util.Properties;

public class PsyConfigDict
	extends PsyModule
{
	public PsyConfigDict()
	{
		for(final var name: Config.stringPropertyNames())
			put(name, new PsyName(Config.getProperty(name)));

		final Properties systemProperties=System.getProperties();
		for(final var name: systemProperties.stringPropertyNames())
			put(name, new PsyName(systemProperties.getProperty(name)));
	}
}
