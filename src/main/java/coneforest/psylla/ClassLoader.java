package coneforest.psylla;
import coneforest.psylla.core.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.net.URL;

abstract public class ClassLoader
	extends java.lang.ClassLoader
{
	public ClassLoader()
	{
		super();
	}

	public void psyExternal(final PsyStringy oClassName)
		throws PsyException
	{
		try
		{
			loadClass(oClassName.stringValue());
		}
		catch(final ClassNotFoundException e)
		{
			throw new PsyInvalidExternalException();
		}
	}

	@Override
	public Class findClass(final String className)
		throws ClassNotFoundException
	{
		try
		{
			return findSystemClass(className);
		}
		catch(final ClassNotFoundException e)
		{
			// NOP
		}
		Class result=(Class)classes.get(className);
		if(result!=null)
			return result;

		try
		{
			final var url=findResource(className.replace('.', '/')+".class");
			if(url==null)
				throw new ClassNotFoundException();

			final var classBytes=url.openStream().readAllBytes();
			result=defineClass(className, classBytes, 0, classBytes.length, null);
			classes.put(className, result);
			return result;
		}
		catch(final IOException e)
		{
			throw new ClassNotFoundException();
		}
	}

	abstract protected Iterable<String> getClassPath()
		throws Exception;

	@Override
	protected java.net.URL findResource(final String name)
	{
		try
		{
			final var cp=getClassPath();
			for(final String item: cp)
			{
				//final String item=oItem.stringValue();
				final var itemPath=Path.of(item);
				try
				{
					if(Files.isRegularFile(itemPath))
					{
						final var jar=new JarFile(itemPath.toFile());
						final var entry=jar.getJarEntry(name);
						if(entry==null)
							return null;
						return new java.net.URL("jar:"+itemPath.toUri().toURL()+"!/"+entry);
					}
					else if(Files.isDirectory(itemPath))
					{
						// TODO: if directory does not exist
						return Path.of(itemPath.toString(), name).toUri().toURL();
					}
				}
				catch(final IOException e)
				{
					return null;
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}

	private final java.util.Hashtable<String, Class> classes
		=new java.util.Hashtable<String, Class>();
}
