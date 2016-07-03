package coneforest.psi.cam;

public class Plane
{

	public boolean get(int index)
	{
		return data.get(index);
	}

	public void put(int index, boolean b)
	{
		data.set(index, b);
	}

	public final int SIZE=256;

	private java.util.BitSet data=new java.util.BitSet(SIZE*SIZE);
}
