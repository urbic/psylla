package coneforest.psylla.ant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
*	A codec for encoding and decoding objects using the Base64 encoding scheme.
*/
public interface Base64Codec
{
	/**
	*	{@return a Base64-encoded representation of an object}
	*
	*	@param obj an object.
	*/
	public static String encode(final Object obj)
	{
		try
		{
			final var baos=new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(obj);
			return Base64.getEncoder().encodeToString(baos.toByteArray());
		}
		catch(final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	*	{@return an object decoded from a Base64-encoded representation}
	*
	*	@param str a Base-64 encoded representation of an object.
	*/
	public static Object decode(final String str)
	{
		try
		{
			final var bais=new ByteArrayInputStream(Base64.getDecoder().decode(str));
			return (new ObjectInputStream(bais)).readObject();
		}
		catch(final IOException|ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
}
