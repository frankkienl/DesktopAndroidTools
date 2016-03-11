package nl.frankkie.desktopandroidtools;

import axml.test.AXMLPrinter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author fbouwens
 */
public class ApkManifestReader {

    public static void main(String[] args) {

        if (args == null || args.length == 0 || "".equals(args[0])) {
            go();
        } else {
            //args[0] should be file
            String manifest = showManifest(args[0]);
            System.out.println(manifest);
        }
    }

    public static void go() {
        final JFileChooser fc = new JFileChooser();
        //In response to a button click:
        int returnVal = fc.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        } else {
            System.err.println("Please Select File !");
            return;
        }
        if (file == null) {
            System.err.println("Please Select File !");
            return;
        }
        String filePath = file.getAbsolutePath();

        ////
        String manifest = showManifest(filePath);
        ////
        System.out.println(manifest);

        showDialog(manifest);
    }

    public static void showDialog(String s) {
        //JOptionPane.showMessageDialog(null, manifest);
        JFrame frame = new JFrame("Desktop Android Tools - Apk Manifest");
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(0, 1));
        JTextPane label = new JTextPane();
        label.setText(s);
        mainPanel.add(label);
        JScrollPane scroll = new JScrollPane(mainPanel);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static String showManifest(String apkPath) {

        if (apkPath.endsWith(".apk")) {

            try {
//            PackageManager pm = c.getPackageManager();
//            PackageInfo info = pm.getPackageInfo(pak, 0);
//            String apkPath = info.applicationInfo.publicSourceDir;
                File apkFile = new File(apkPath);
                ZipFile zipFile = new ZipFile(apkFile);
                ZipEntry entry = zipFile.getEntry("AndroidManifest.xml");
                InputStream is = zipFile.getInputStream(entry);
                String xml = AXMLPrinter.xmlToString(is);
                zipFile.close();
                return xml;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (apkPath.endsWith(".xml")) {
            //Already unzipped, its a binary xml file
            try {
                FileInputStream is = new FileInputStream(apkPath);
                String xml = AXMLPrinter.xmlToString(is);
                is.close();
                return xml;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
