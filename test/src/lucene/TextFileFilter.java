package lucene;

import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

   public boolean accept(File pathname) {
      return pathname.getName().toLowerCase().endsWith(".txt");
   }
}