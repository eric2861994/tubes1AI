import java.io.*;
import java.util.*;

public class parser {
	public static void main (String args[]) throws java.io.IOException
	{
		int row=1;
		int column=1;
		int group=0;
		int diag=0;
		int[] groupcounter = { 0,1,7,13,19,25,31 };
		int id =1;
		String value ="";
		String parseresult = "(retract ?f) (assert (phase expand-any)) ";
		Scanner soal = new Scanner(new FileInputStream("C:/Users/Stanley/workspace/Sudoku X/src/soal.txt"));
		while(soal.hasNext())
		{
			String s = soal.next();
			if(s.equals("*"))
			{
				value = "any";
			}
			else
			{
				value = s;
			}
			if(row==column)
			{
				diag = 1;								
			}
			else if(row + column==7)
			{
				diag = 2;
			}
			else
			{
				diag = 0;
			}
			if((row>=1 && row<=2 && column>=1 && column<=3))
			{
				group=1;
			}
			else if((row>=1 && row<=2 && column>=4 && column<=6))
			{
				group=2;
			}
			else if((row>=3 && row<=4 && column>=1 && column<=3))
			{
				group=3;
			}
			else if((row>=3 && row<=4 && column>=4 && column<=6))
			{
				group=4;
			}
			else if((row>=5 && row<=6 && column>=1 && column<=3))
			{
				group=5;
			}
			else if((row>=5 && row<=6 && column>=4 && column<=6))
			{
				group=6;
			}
			else
			{
				group=0;
			}
			id = groupcounter[group];
			parseresult=parseresult + "(assert (possible (row " + row + ") (column " + column + ") (value " + value + ") (group "
					+ group + ") (diag " + diag + ") (id " + id + "))) ";
			if(column==6)
			{
				row++;
				column=0;
			}
			column++;
			groupcounter[group]++;
		}
		
		soal.close();
	}
}
