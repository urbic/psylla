package coneforest.psi.cam;

public class CAM
{

	public final int PLN_SIZE=256;

	private final java.util.BitSet[] pln=
		{
			new java.util.BitSet(PLN_SIZE*PLN_SIZE),
			new java.util.BitSet(PLN_SIZE*PLN_SIZE),
			new java.util.BitSet(PLN_SIZE*PLN_SIZE),
			new java.util.BitSet(PLN_SIZE*PLN_SIZE)
		};
}
