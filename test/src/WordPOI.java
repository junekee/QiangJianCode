import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
import org.apache.poi.POIXMLDocument;  
import org.apache.poi.hwpf.HWPFDocument;  
import org.apache.poi.hwpf.usermodel.Range;  
import org.apache.poi.xwpf.usermodel.XWPFDocument;  
import org.apache.poi.xwpf.usermodel.XWPFParagraph;  
import org.apache.poi.xwpf.usermodel.XWPFRun;  
import org.apache.poi.xwpf.usermodel.XWPFTable;  
import org.apache.poi.xwpf.usermodel.XWPFTableCell;  
import org.apache.poi.xwpf.usermodel.XWPFTableRow;  
  
public class WordPOI {  
  
    // ����Docx����Ҫ�滻�������ַ���û���ظ���  
    // �Ƽ�����������ʽ����"\\$\\{[^{}]+\\}"  
    public ArrayList<String> getReplaceElementsInWord(String filePath,  
            String regex) {  
        String[] p = filePath.split("\\.");  
        if (p.length > 0) {// �ж��ļ�������չ��  
            // �Ƚ��ļ���չ��  
            if (p[p.length - 1].equalsIgnoreCase("doc")) {  
                ArrayList<String> al = new ArrayList();  
                File file = new File(filePath);  
                HWPFDocument document = null;  
                try {  
                    InputStream is = new FileInputStream(file);  
                    document = new HWPFDocument(is);  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                Range range = document.getRange();  
                String rangeText = range.text();  
                CharSequence cs = rangeText.subSequence(0, rangeText.length());  
                Pattern pattern = Pattern.compile(regex);  
                Matcher matcher = pattern.matcher(cs);  
                int startPosition = 0;  
                while (matcher.find(startPosition)) {  
                    if (!al.contains(matcher.group())) {  
                        al.add(matcher.group());  
                    }  
                    startPosition = matcher.end();  
                }  
                return al;  
            } else if (p[p.length - 1].equalsIgnoreCase("docx")) {  
                ArrayList<String> al = new ArrayList();  
                XWPFDocument document = null;  
                try {  
                    document = new XWPFDocument(  
                            POIXMLDocument.openPackage(filePath));  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                // ��������  
                Iterator<XWPFParagraph> itPara = document  
                        .getParagraphsIterator();  
                while (itPara.hasNext()) {  
                    XWPFParagraph paragraph = (XWPFParagraph) itPara.next();  
                    String paragraphString = paragraph.getText();  
                    CharSequence cs = paragraphString.subSequence(0,  
                            paragraphString.length());  
                    Pattern pattern = Pattern.compile(regex);  
                    Matcher matcher = pattern.matcher(cs);  
                    int startPosition = 0;  
                    while (matcher.find(startPosition)) {  
                        if (!al.contains(matcher.group())) {  
                            al.add(matcher.group());  
                        }  
                        startPosition = matcher.end();  
                    }  
                }  
                // ������  
                Iterator<XWPFTable> itTable = document.getTablesIterator();  
                while (itTable.hasNext()) {  
                    XWPFTable table = (XWPFTable) itTable.next();  
                    int rcount = table.getNumberOfRows();  
                    for (int i = 0; i < rcount; i++) {  
                        XWPFTableRow row = table.getRow(i);  
                        List<XWPFTableCell> cells = row.getTableCells();  
                        for (XWPFTableCell cell : cells) {  
                            String cellText = "";  
                            cellText = cell.getText();  
                            CharSequence cs = cellText.subSequence(0,  
                                    cellText.length());  
                            Pattern pattern = Pattern.compile(regex);  
                            Matcher matcher = pattern.matcher(cs);  
                            int startPosition = 0;  
                            while (matcher.find(startPosition)) {  
                                if (!al.contains(matcher.group())) {  
                                    al.add(matcher.group());  
                                }  
                                startPosition = matcher.end();  
                            }  
                        }  
                    }  
                }  
                return al;  
            } else {  
                return null;  
            }  
        } else {  
            return null;  
        }  
    }  
  
    // �滻word����Ҫ�滻�������ַ�  
    public static boolean replaceAndGenerateWord(String srcPath,  
            String destPath, Map<String, String> map) {  
        String[] sp = srcPath.split("\\.");  
        String[] dp = destPath.split("\\.");  
        if ((sp.length > 0) && (dp.length > 0)) {// �ж��ļ�������չ��  
            // �Ƚ��ļ���չ��  
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {  
                try {  
                    XWPFDocument document = new XWPFDocument(  
                            POIXMLDocument.openPackage(srcPath));  
                    // �滻�����е�ָ������  
                    Iterator<XWPFParagraph> itPara = document  
                            .getParagraphsIterator();  
                    while (itPara.hasNext()) {  
                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();  
                        List<XWPFRun> runs = paragraph.getRuns();  
                        for (int i = 0; i < runs.size(); i++) {  
                            String oneparaString = runs.get(i).getText(  
                                    runs.get(i).getTextPosition());  
                            System.out.println(oneparaString);
                            for (Map.Entry<String, String> entry : map  
                                    .entrySet()) {  
                                oneparaString = oneparaString.replace(  
                                        entry.getKey(), entry.getValue());  
                            }  
                            runs.get(i).setText(oneparaString, 0);  
                        }  
                    }  
  
                    // �滻����е�ָ������  
                    Iterator<XWPFTable> itTable = document.getTablesIterator();  
                    while (itTable.hasNext()) {  
                        XWPFTable table = (XWPFTable) itTable.next();  
                        int rcount = table.getNumberOfRows();  
                        for (int i = 0; i < rcount; i++) {  
                            XWPFTableRow row = table.getRow(i);  
                            List<XWPFTableCell> cells = row.getTableCells();  
                            for (XWPFTableCell cell : cells) {  
                                String cellTextString = cell.getText();  
                                for (Entry<String, String> e : map.entrySet()) {  
                                    if (cellTextString.contains(e.getKey()))  
                                        cellTextString = cellTextString  
                                                .replace(e.getKey(),  
                                                        e.getValue());  
                                }  
                                cell.removeParagraph(0);  
                                cell.setText(cellTextString);  
                            }  
                        }  
                    }  
                    FileOutputStream outStream = null;  
                    outStream = new FileOutputStream(destPath);  
                    document.write(outStream);  
                    outStream.close();  
                    return true;  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    return false;  
                }  
  
            } else  
            // docֻ������doc���������docx�����  
            if ((sp[sp.length - 1].equalsIgnoreCase("doc"))  
                    && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {  
                HWPFDocument document = null;  
                try {  
                    document = new HWPFDocument(new FileInputStream(srcPath));  
                    Range range = document.getRange();  
                    for (Map.Entry<String, String> entry : map.entrySet()) {  
                        range.replaceText(entry.getKey(), entry.getValue());  
                    }  
                    FileOutputStream outStream = null;  
                    outStream = new FileOutputStream(destPath);  
                    document.write(outStream);  
                    outStream.close();  
                    return true;  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                    return false;  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    return false;  
                }  
            } else {  
                return false;  
            }  
        } else {  
            return false;  
        }  
    }  
  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        String filepathString = "e:\\�����������֪ͨ��.docx";  
        String destpathString = "e:\\aanew.doc";  
        Map param = new HashMap<String, Object>();  
        param.put("noticeno", "2000000");
        param.put("city", "����");
        param.put("applyusername", "������");
        System.out.println("==========\r\n====");
        param.put("itemname", "xxx��������");
        param.put("bzqd", "1��34543543\r2��34543543\r");
        System.out.println(replaceAndGenerateWord(filepathString,  
                destpathString, param));  
    }  
}  