package ui.utils.browser;

import com.google.common.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class ChromeDriverDownloader {
    private static final Logger logger = Logger.getLogger(ChromeDriverDownloader.class);
    private final boolean isWindows;
    private final boolean isMac;
    private final boolean isLinux64;

    public ChromeDriverDownloader() {
        String osName = System.getProperty("os.name");
        isWindows = osName.startsWith("Windows");
        isMac = osName.startsWith("Mac OS X") || osName.startsWith("Darwin");
        isLinux64 = System.getProperty("sun.arch.data.model").equals("64");
    }

    public File downloadAndExtract() throws MalformedURLException, IOException {
        File installDir = new File(new File(System.getProperty("user.home")), ".chromedriver");
        logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"The chromedriver directory is" + installDir);
        String url;
        File chromeDriver;
        if (isWindows) {
            url = "http://chromedriver.storage.googleapis.com/2.8/chromedriver_win32.zip";
            chromeDriver = new File(installDir, "chromedriver.exe");
        } else if (isMac) {
            url = "https://chromedriver.storage.googleapis.com/2.33/chromedriver_mac64.zip";
            chromeDriver = new File(installDir, "chromedriver");
        } else if (isLinux64) {
            url = "https://chromedriver.storage.googleapis.com/2.8/chromedriver_linux64.zip";
            chromeDriver = new File(installDir, "chromedriver");
        } else {
            url = "https://chromedriver.storage.googleapis.com/2.8/chromedriver_linux32.zip";
            chromeDriver = new File(installDir, "chromedriver");
        }

        extractExe(url, installDir, chromeDriver);

        return chromeDriver;
    }

    private void extractExe(String url, File installDir, File file) throws MalformedURLException, IOException {
        if (file.exists()) {
            return;
        }

        File targetZip = new File(installDir, "chromedriver.zip");
        downloadZip(url, targetZip);

        logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"Extracting chromedriver");
        try {
            if (isWindows) {
                unzip(targetZip, installDir);
            } else if (isMac) {
                new ProcessBuilder().command("/usr/bin/unzip", "-qo", "chromedriver.zip").directory(installDir).start().waitFor();
            } else {
                new ProcessBuilder().command("tar", "-xjvf", "chromedriver.zip").directory(installDir).start().waitFor();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to unzip chromedriver from " + targetZip.getAbsolutePath());
        }
    }

    private void downloadZip(String url, File targetZip) throws MalformedURLException, IOException {
        if (targetZip.exists()) {
            return;
        }

        logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"Downloading chromedriver from " + url + "...");

        File zipTemp = new File(targetZip.getAbsolutePath() + ".temp");
        FileUtils.copyURLToFile(new URL(url), targetZip);
        //fix this issue
     /*   try {
            zipTemp.getParentFile().mkdirs();

            InputSupplier<InputStream> input = Resources.newInputStreamSupplier(URI.create(url).toURL());
            OutputSupplier<FileOutputStream> ouput = Files.newOutputStreamSupplier(zipTemp);

            ByteStreams.copy(input, ouput);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to download chromedriver from " + url);
        }*/

        zipTemp.renameTo(targetZip);
    }

    private static void unzip(File zip, File toDir) throws IOException {
        final ZipFile zipFile = new ZipFile(zip);
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                File to = new File(toDir, entry.getName());
                to.getParentFile().mkdirs();
                //fix this later
              /* Files.copy(new InputSupplier<InputStream>() {
                    @Override
                    public InputStream getInput() throws IOException {
                        return zipFile.getInputStream(entry);
                    }
                }, to);*/
            }
        } finally {
            zipFile.close();
        }
    }
}