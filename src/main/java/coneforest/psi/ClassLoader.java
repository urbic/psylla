package coneforest.psi;
import coneforest.psi.core.*;

abstract public class ClassLoader
	extends java.lang.ClassLoader
{
	public ClassLoader()
	{
		super(ClassLoader.class.getClassLoader());
	}

	public void psiExternal(final PsiStringy oClassName)
		throws PsiException
	{
		try
		{
			loadClass(oClassName.stringValue());
		}
		catch(final ClassNotFoundException e)
		{
			throw new PsiInvalidExternalException();
		}
	}

	@Override
	public Class loadClass(final String className)
		throws ClassNotFoundException
	{
		try
		{
			return findSystemClass(className);
		}
		catch(final Exception e)
		{
		}
		for(PsiObject file: getPsiClassPath())
		{
			final Class cl=findClassAtPathElement(className,
					new java.io.File(((PsiStringy)file).stringValue()));
			if(cl!=null)
				return cl;
		}
		throw new ClassNotFoundException();
	}

	abstract protected PsiIterable<PsiStringy> getPsiClassPath();

	private Class findClassAtPathElement(final String className, final java.io.File file)
	{
		final byte classByte[];
		Class result=(Class)classes.get(className);
		if(result!=null)
			return result;

		try
		{
			java.io.InputStream is=null;
			if(file.isFile())
			{
				final java.util.jar.JarFile jar=new java.util.jar.JarFile(file);
				final java.util.jar.JarEntry entry
					=jar.getJarEntry(className.replace('.', '/')+".class");
				if(entry==null)
					return null;
				is=jar.getInputStream(entry);
			}
			else if(file.isDirectory())
				is=new java.io.FileInputStream(file.getPath()
						+java.io.File.separator
						+className.replace('.', java.io.File.separatorChar)+".class");
			else
				return null;

			final java.io.ByteArrayOutputStream byteStream=new java.io.ByteArrayOutputStream();
			int nextValue=is.read();
			while(nextValue!=-1)
			{
				byteStream.write(nextValue);
				nextValue=is.read();
			}

			classByte=byteStream.toByteArray();
			result=defineClass(className, classByte, 0, classByte.length, null);
			classes.put(className, result);
			return result;
		}
		catch(final java.io.IOException e)
		{
			return null;
		}
	}

	private final java.util.Hashtable<String, Class> classes
		=new java.util.Hashtable<String, Class>();
}
