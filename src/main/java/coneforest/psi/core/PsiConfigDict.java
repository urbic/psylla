package coneforest.psi.core;
import coneforest.psi.*;

public class PsiConfigDict
	extends PsiModule
{
	public PsiConfigDict()
	{
		for(String name: Config.stringPropertyNames())
			put(name, new PsiName(Config.getProperty(name)));

		final java.util.Properties system=System.getProperties();
		for(String name: system.stringPropertyNames())
			put(name, new PsiName(system.getProperty(name)));
	}
}
