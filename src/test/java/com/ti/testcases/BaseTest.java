package com.ti.testcases;

import static com.ti.framework.config.Constants.VIDEO_FOLDER;

import com.ti.framework.base.BasePage;
import com.ti.framework.base.BrowserType;
import com.ti.framework.base.DriverFactory;
import com.ti.framework.config.PropertyManager;
import com.ti.framework.utils.logs.Log;
import com.ti.framework.utils.video.SpecializedScreenRecorder;
import com.ti.framework.utils.video.VideoRecorder;
import com.ti.library.TestLibrary;
import com.ti.pages.StudentsPage;
import java.awt.AWTException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest extends TestLibrary {
    protected static VideoRecorder videoRec;
    String baseURL = "https://demosite.titaniuminstitute.com.mx/wp-admin/admin.php?page=sch-dashboard";

    @BeforeTest
    @Parameters("browser")
    void setup(String browser) {
        //Arrange
        DriverFactory.getInstance().setDriver(BrowserType.valueOf(browser));
        if (Boolean.parseBoolean(PropertyManager.getInstance().getProperty("Video"))){
            videoRec = new VideoRecorder();
            videoRec.startRecording(VIDEO_FOLDER);
        }
        DriverFactory.getInstance().getDriver().navigate().to(baseURL);
        Log.info("Test is starting!");
        login();
    }

    @AfterTest
    void turnDown() throws Exception {
        for (int i=0;i<3;i++){
            actualPage.as(StudentsPage.class).deleteStudent();
        }
        DriverFactory.getInstance().removeDriver();
        Log.info("Test are ending!");
        if (videoRec!=null){
            videoRec.stopRecording();
        }
    }
}
