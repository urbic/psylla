package coneforest.psi.core;
import coneforest.psi.*;

public class PsiConfigDict
	extends PsiModule
{
	public PsiConfigDict()
	{
		try
		{
			final java.util.Properties config=new java.util.Properties();
			config.load(Psylla.class.getResourceAsStream("Config.properties"));
			for(String name: config.stringPropertyNames())
				put(name, new PsiName(config.getProperty(name)));

			final java.util.Properties system=System.getProperties();
			for(String name: system.stringPropertyNames())
				put(name, new PsiName(system.getProperty(name)));
		}
		catch(java.io.IOException e)
		{
			throw new AssertionError();
		}
	}
}
