import java.io.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class FileEditor
{
	private boolean appendFile=false;
	private String path;
	
	public FileEditor(String f)
	{
		path=f;
	}
	public FileEditor(String f, boolean b)
	{
		path=f;
		appendFile=b;
	}
	public void writingToFile(String line) throws IOException
	{
		FileWriter lineWriter=new FileWriter(path, appendFile);
		PrintWriter linePrinter=new PrintWriter(lineWriter);
		linePrinter.printf("%s"+"%n", line);
		linePrinter.close();
	}
}
