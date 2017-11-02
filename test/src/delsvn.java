
import java.io.File;
import java.io.IOException;

public class DeleteSvnUtil {

	public static void main(String[] args) {
		
		File f = new File("E:\\DEP_TP\\NewGdSfLv");

		try {
			showAllFiles(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final static void showAllFiles(File dir) throws Exception {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				try {

					if (fs[i].getName().equals(".svn")) {
						System.out.println(fs[i].getAbsolutePath());
						System.out.println("fileName:" + fs[i].getName());
						deleteDirectory(fs[i]);
					} else {
						System.out.println("not include .svn");
					}
					showAllFiles(fs[i]);
				} catch (Exception e) {
				}
			}
		}
	}

	static public void deleteDirectory(File dir) throws IOException {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir+ " is not a directory. ");
		}
		File[] entries = dir.listFiles();
		int sz = entries.length;
		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				deleteDirectory(entries<i>);
			} else {
				entries[i].delete();
			}
		}
		dir.delete();
	}

}
----------------------------
@echo On
@Rem 删除SVN版本控制目录
@PROMPT [Com]
@for /r . %%a in (.) do @if exist "%%a\.svn" rd /s /q "%%a\.svn"
@Rem for /r . %%a in (.) do @if exist "%%a\.svn" @echo "%%a\.svn"
@echo Mission Completed.
@pause
 
@echo On
@Rem 删除CVS版本控制目录
@PROMPT [Com]#
@for /r . %%a in (.) do @if exist "%%a\CVS" rd /s /q "%%a\CVS"
@Rem for /r . %%a in (.) do @if exist "%%a\CVS" @echo "%%a\CVS"
@echo Mission Completed.
@pause