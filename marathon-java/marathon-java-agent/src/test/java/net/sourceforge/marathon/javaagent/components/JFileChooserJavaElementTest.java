/*******************************************************************************
 * Copyright 2016 Jalian Systems Pvt. Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sourceforge.marathon.javaagent.components;

import java.awt.BorderLayout;
import java.awt.Window;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import components.FileChooserDemo;
import net.sourceforge.marathon.javaagent.DeviceTest;
import net.sourceforge.marathon.javaagent.IJavaAgent;
import net.sourceforge.marathon.javaagent.IJavaElement;
import net.sourceforge.marathon.javaagent.JavaAgent;
import net.sourceforge.marathon.javaagent.JavaElementFactory;
import net.sourceforge.marathon.testhelpers.ComponentUtils;

@Test public class JFileChooserJavaElementTest extends JavaElementTest {
    private IJavaAgent driver;
    protected JFrame frame;

    @BeforeMethod public void showDialog() throws Throwable {
        JavaElementFactory.add(JFileChooser.class, JFileChooserJavaElement.class);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override public void run() {
                frame = new JFrame(JFileChooserJavaElementTest.class.getSimpleName());
                frame.setName("frame-" + JFileChooserJavaElementTest.class.getSimpleName());
                frame.getContentPane().add(new FileChooserDemo(), BorderLayout.CENTER);
                frame.pack();
                frame.setVisible(true);
                frame.setAlwaysOnTop(true);
                File file = new File(System.getProperty("java.home"));
                System.setProperty("marathon.project.dir", file.getPath());
            }
        });
        driver = new JavaAgent();
    }

    @AfterMethod public void disposeDriver() throws Throwable {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override public void run() {
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    public void selectForSingleFileSelection() throws Throwable {
        IJavaElement button = driver.findElementByTagName("button");
        button.click();
        new DeviceTest.WaitWithoutException("Waiting for the window to open") {

            @Override public boolean until() {
                try {
                    driver.switchTo().window("Open");
                    return true;
                } catch (Throwable t) {
                }
                return false;
            }
        };
        driver.switchTo().window("Open");
        IJavaElement fc = driver.findElementByTagName("file-chooser");
        File file = new File(System.getProperty("user.dir"));
        marathon_select(fc, file.getAbsolutePath());
        String attribute = fc.getAttribute("selectedFile");
        AssertJUnit.assertEquals(file.getAbsoluteFile().toString(), attribute);
    }

    public void selectForNoFileSelection() throws Throwable {
        IJavaElement button = driver.findElementByTagName("button");
        button.click();
        new DeviceTest.WaitWithoutException("Waiting for the window to open") {

            @Override public boolean until() {
                try {
                    driver.switchTo().window("Open");
                    return true;
                } catch (Throwable t) {
                }
                return false;
            }
        };
        driver.switchTo().window("Open");
        IJavaElement fc = driver.findElementByTagName("file-chooser");
        marathon_select(fc, "");
        String attribute = fc.getAttribute("selectedFile");
        AssertJUnit.assertEquals(null, attribute);
    }

    public void selectForMultipleFileSelection() throws Throwable {
        IJavaElement button = driver.findElementByTagName("button");
        button.click();
        new DeviceTest.WaitWithoutException("Waiting for the window to open") {

            @Override public boolean until() {
                try {
                    driver.switchTo().window("Open");
                    return true;
                } catch (Throwable t) {
                }
                return false;
            }
        };
        driver.switchTo().window("Open");
        IJavaElement fc = driver.findElementByTagName("file-chooser");
        Window[] windows = Window.getWindows();
        JFileChooser fc1 = null;
        for (Window window1 : windows) {
            fc1 = (JFileChooser) ComponentUtils.findComponent(JFileChooser.class, window1);
            if (fc1 != null) {
                break;
            }
        }
        fc1.setMultiSelectionEnabled(true);
        File file = new File(System.getProperty("user.home"));
        File[] fileList = file.listFiles();
        marathon_select(fc, fileList[0].getAbsolutePath() + File.pathSeparator + fileList[0].getAbsolutePath());
        String attribute = fc.getAttribute("selectedFiles");
        AssertJUnit.assertEquals("[" + fileList[0].getAbsoluteFile() + ", " + fileList[0].getAbsoluteFile() + "]", attribute);
    }

    public void selectForHomeDirFileSelection() throws Throwable {
        IJavaElement button = driver.findElementByTagName("button");
        button.click();
        new DeviceTest.WaitWithoutException("Waiting for the window to open") {

            @Override public boolean until() {
                try {
                    driver.switchTo().window("Open");
                    return true;
                } catch (Throwable t) {
                }
                return false;
            }
        };
        driver.switchTo().window("Open");
        IJavaElement fc = driver.findElementByTagName("file-chooser");
        File file = new File(System.getProperty("user.home"));
        marathon_select(fc, file.getAbsolutePath());
        String attribute = fc.getAttribute("selectedFile");
        AssertJUnit.assertEquals(file.getAbsoluteFile().toString(), attribute);
    }

    public void selectForMarathonDirectoryFileSelection() throws Throwable {
        IJavaElement button = driver.findElementByTagName("button");
        button.click();
        new DeviceTest.WaitWithoutException("Waiting for the window to open") {

            @Override public boolean until() {
                try {
                    driver.switchTo().window("Open");
                    return true;
                } catch (Throwable t) {
                }
                return false;
            }
        };
        driver.switchTo().window("Open");
        IJavaElement fc = driver.findElementByTagName("file-chooser");
        File file = new File(System.getProperty("marathon.project.dir"));
        marathon_select(fc, file.getAbsolutePath());
        String attribute = fc.getAttribute("selectedFile");
        AssertJUnit.assertEquals(file.getAbsoluteFile().toString(), attribute);
    }

}
