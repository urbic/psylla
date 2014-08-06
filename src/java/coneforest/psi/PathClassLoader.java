package coneforest.psi;

public class PathClassLoader
	extends ClassLoader
{
	public PathClassLoader()
	{
		super(PathClassLoader.class.getClassLoader());
	}

	public PathClassLoader(java.io.File[] path)
	{
		this();
		this.path=path;
	}

	public PathClassLoader(String[] stringArrayPath)
	{
		this();
		path=new java.io.File[stringArrayPath.length];
		for(int i=0; i<stringArrayPath.length; i++)
			path[i]=new java.io.File(stringArrayPath[i]);
	}

	public PathClassLoader(String stringPath)
	{
		this(stringPath.split(java.io.File.pathSeparator));
	}

	@Override
	public Class loadClass(String className)
		throws ClassNotFoundException
	{
		try
		{
			return findSystemClass(className);
		}
		catch(Exception e)
		{
		}
		for(java.io.File file: path)
		{
			Class cl=findClassAtPathElement(className, file);
			if(cl!=null)
				return cl;
		}
		throw new ClassNotFoundException();
	}

	public Class findClassAtPathElement(String className, java.io.File file)
	{
		byte classByte[];
		Class result=null;

		result=(Class)classes.get(className);
        if(result!=null)
			return result;

		try
		{
			java.io.InputStream is=null;
			if(file.isFile())
			{
				java.util.jar.JarFile jar=new java.util.jar.JarFile(file);
				java.util.jar.JarEntry entry
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

			java.io.ByteArrayOutputStream byteStream=new java.io.ByteArrayOutputStream();
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

	private java.io.File[] path;

	private java.util.Hashtable<String, Class> classes
		=new java.util.Hashtable<String, Class>();
}
