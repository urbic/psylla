package coneforest.psi;

public class UTF8String implements Iterable<Integer>
{

	public UTF8String()
	{
	}

	public UTF8String(String string)
	{
		value=string.getBytes(java.nio.charset.StandardCharsets.UTF_8);
	}

	public UTF8String(UTF8String ustring)
	{
		value=java.util.Arrays.copyOf(ustring.value, ustring.value.length);
	}
	
	public int length()
	{
		int l=0;
		for(int offset: offsets())
			l++;
		return l;
	}

	public int get(int index)
		throws IndexOutOfBoundsException
	{
		if(index<0||index>=byteLength())
			throw new IndexOutOfBoundsException();
		int l=0;
		for(int uChar: this)
		{
			if(l++==index)
				return uChar;
		}
		throw new IndexOutOfBoundsException();
	}

	private int byteLength()
	{
		return value.length;
	}

	public String toString()
	{
		return new String(value, java.nio.charset.StandardCharsets.UTF_8);
	}

	public java.util.Iterator<Integer> iterator()
	{
		return new java.util.Iterator<Integer>()
			{
				public Integer next()
				{
					int offset=offsetIterator.next();
					int bsl=byteSequenceLength(value[offset]);
					int uChar=0;
					if(bsl==1)
						uChar=(int)(value[offset]&0xFF);
					else
					{
						uChar=(int)value[offset]&(0xFF>>(bsl+1));
						for(int i=1; i<bsl; i++)
							uChar=(uChar<<6)|(value[offset+i]&0x3F);
					}
					return uChar;
				}

				public boolean hasNext()
				{
					return offsetIterator.hasNext();
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				private java.util.Iterator<Integer> offsetIterator=offsets().iterator();
			};
	}

	public Iterable<Integer> offsets()
	{
		return new Iterable<Integer>()
			{
				public java.util.Iterator<Integer> iterator()
				{
					return new java.util.Iterator<Integer>()
						{
							public Integer next()
							{
								int offset=byteOffset;
								byteOffset+=byteSequenceLength(value[byteOffset]);
								return offset;
							}

							public boolean hasNext()
							{
								return byteOffset<byteLength();
							}

							public void remove()
							{
								throw new UnsupportedOperationException();
							}

							private int byteOffset=0;
						};
				}
			};
	}

	private static byte[] toBytes(int uChar)
	{
		int capacity=bytesCount(uChar);
		byte[] result=new byte[capacity];
		result[0]=(byte)(capacity==1? uChar: (uChar>>(6*(capacity-1)))|(0xFF<<(8-capacity)));
		for(int i=1; i<capacity; i++)
			result[i]=(byte)((uChar>>(6*(capacity-i-1)))&0x3F|0x80);
		return result;
	}

	private static int bytesCount(int uChar)
	{
		if((uChar>>7)==0)
			return 1;
		else if((uChar>>11)==0)
			return 2;
		else if((uChar>>16)==0)
			return 3;
		else if((uChar>>21)==0)
			return 4;
		else if((uChar>>26)==0)
			return 5;
		else
			return 6;
	}

	public static int byteSequenceLength(byte b)
	{
		if((b&0x80)==0)
			return 1;
		else if((b&0xC0)==0x80)
			return 0;
		else if((b&0xE0)==0xC0)
			return 2;
		else if((b&0xF0)==0xE0)
			return 3;
		else if((b&0xF8)==0xF0)
			return 4;
		else if((b&0xFC)==0xF8)
			return 5;
		else
			return 6;
	}

	private byte[] value={};
	
	public static void main(String[] args)
	{
		System.out.println(new UTF8String("мама мыла раму").length());
		System.out.println(new UTF8String("мама мыла раму").byteLength());
		System.out.println(new UTF8String("мама мыла раму"));
		System.out.println(new UTF8String("…က").get(1));
		for(byte c: toBytes(0x2260))
			System.out.println(Integer.toHexString(c&0xFF));
	}

}
