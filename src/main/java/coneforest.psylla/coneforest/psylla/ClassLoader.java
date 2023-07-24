package coneforest.psylla;

import coneforest.psylla.core.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

abstract public class ClassLoader
	extends java.lang.ClassLoader
{
	public ClassLoader()
	{
		super();
	}

	public void psyExternal(final PsyTextual oClassName)
		throws PsyInvalidExternalException
	{
		try
		{
			loadClass(oClassName.stringValue());
		}
		catch(final ClassNotFoundException ex)
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
		catch(final ClassNotFoundException ex)
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
		catch(final IOException ex)
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
				catch(final IOException ex)
				{
					return null;
				}
			}
		}
		catch(Exception ex)
		{
			return null;
		}
		return null;
	}

	private final Hashtable<String, Class> classes=new Hashtable<>();
}
