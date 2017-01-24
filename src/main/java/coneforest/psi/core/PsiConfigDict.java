package coneforest.psi.core;
import coneforest.psi.*;

public class PsiConfigDict
	extends PsiModule
{
	public PsiConfigDict()
	{
		//super("config");

		for(String name: Config.stringPropertyNames())
			put(name, new PsiName(Config.getProperty(name)));

		final java.util.Properties systemProperties=System.getProperties();
		for(String name: systemProperties.stringPropertyNames())
			put(name, new PsiName(systemProperties.getProperty(name)));
	}
}
