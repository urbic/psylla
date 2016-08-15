package coneforest.psi.core;

public class PsiClassLoader
	extends PsiArray
{
	public PsiObject psiExternal(final PsiStringy stringy)
		throws PsiException
	{
		return external(stringy.stringValue());
	}

	public PsiObject external(final Class<? extends PsiObject> objectClass)
		throws PsiException
	{
		try
		{
			return objectClass.newInstance();
		}
		catch(InstantiationException|IllegalAccessException e)
		{
			throw new PsiInvalidExternalException();
		}
	}

	public PsiObject external(final String objectClassName)
		throws PsiException
	{
		try
		{
			return external((Class<? extends PsiObject>)Class.forName(objectClassName, true, classLoader));
		}
		catch(ClassCastException e)
		{
			throw new PsiTypeCheckException();
		}
		catch(ClassNotFoundException e)
		{
			throw new PsiInvalidExternalException();
		}
	}

	private ClassLoader classLoader=new ClassLoader(PsiClassLoader.class.getClassLoader())
		{
			@Override
			public Class loadClass(final String className)
				throws ClassNotFoundException
			{
				try
				{
					return findSystemClass(className);
				}
				catch(Exception e)
				{
				}
				for(PsiObject file: PsiClassLoader.this)
				{
					final Class cl=findClassAtPathElement(className,
							new java.io.File(((PsiStringy)file).stringValue()));
					if(cl!=null)
						return cl;
				}
				throw new ClassNotFoundException();
			}

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
				catch(java.io.IOException e)
				{
					return null;
				}
			}

			private final java.util.Hashtable<String, Class> classes
				=new java.util.Hashtable<String, Class>();
		};
}
