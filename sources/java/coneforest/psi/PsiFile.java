package coneforest.psi;

public class PsiFile extends PsiObject
{
	public PsiFile(String name, String mode)
		throws java.io.FileNotFoundException
	{
		file=new java.io.RandomAccessFile(name, mode);
	}

	public String getTypeName()
	{
		return "file";
	}

	public String toString()
	{
		return "-file-";
	}

	java.io.RandomAccessFile file;
}
