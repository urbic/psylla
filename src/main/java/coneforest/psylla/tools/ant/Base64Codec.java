package coneforest.psylla.tools.ant;

public class Base64Codec
{
	public static String encode(final Object obj)
	{
		try
		{
			final java.io.ByteArrayOutputStream boas
				=new java.io.ByteArrayOutputStream();
			new java.io.ObjectOutputStream(boas).writeObject(obj);
			return java.util.Base64.getEncoder().encodeToString(boas.toByteArray());
		}
		catch(final java.io.IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static Object decode(final String str)
	{
		try
		{
			final java.io.ByteArrayInputStream bias
				=new java.io.ByteArrayInputStream(java.util.Base64.getDecoder().decode(str));
			return (new java.io.ObjectInputStream(bias)).readObject();
		}
		catch(final java.io.IOException|ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
}
