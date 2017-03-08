package com.example.myotive.fragment_sample_mvp;

import android.content.Context;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.common.BaseApplication;
import com.example.common.di.ApplicationComponent;
import com.example.fragment_sample_mvp.MainActivity;
import com.example.fragment_sample_mvp.R;
import com.example.myotive.fragment_sample_mvp.assertions.RecyclerViewAssertion;
import com.example.myotive.fragment_sample_mvp.di.MockedNetworkModule;
import com.example.myotive.fragment_sample_mvp.rules.IdlingResourceRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTests {

    private static final String TAG = MainActivityTests.class.getSimpleName();

    // Retrofit Mock Web Server
    private MockWebServer mockWebServer = new MockWebServer();

    @Rule
    public DaggerMockRule<ApplicationComponent> daggerRule =
            new DaggerMockRule<>(ApplicationComponent.class, new MockedNetworkModule(mockWebServer)).set(
                    new DaggerMockRule.ComponentSetter<ApplicationComponent>() {
                        @Override public void setComponent(ApplicationComponent component) {
                            BaseApplication app =
                                    (BaseApplication) getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();
                            app.setComponent(component);
                        }
                    });

    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule =
            new IntentsTestRule<>(MainActivity.class, true, false);

    @Rule
    public IdlingResourceRule idlingResourceRule;

    /**
     * Startup mock webserver
     */
    @Before
    public void testSetup() {

        Context context = getInstrumentation()
                        .getTargetContext()
                        .getApplicationContext();

        idlingResourceRule = new IdlingResourceRule(BaseApplication.getApplication(context).getApplicationComponent().okhttp());
        try {
            mockWebServer.start();
        } catch (IOException e) {
            Log.e(TAG, "Could not start mock web server", e);
        }
    }

    /**
     * Shutdown mock webserver
     */
    @After
    public void testTearDown(){
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            Log.e(TAG, "Could not stop mock web server", e);
        }
    }


    @Test
    public void test_ListOfRepositories() throws Exception {
        // arrange
        setupResponses();

        // act
        startActivity();

        // assert
        onView(withId(R.id.rv_repository)).check(new RecyclerViewAssertion(1));
    }

    @Test
    public void test_ListOfRepositoryContents()throws Exception{
        // arrange
        setupResponses();

        // act
        startActivity();

        // assert
        onView(withId(R.id.rv_repository)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,
                        RecyclerViewAssertion.clickChildViewWithId(R.id.item_container)));
    }

    private void setupResponses(){
        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
                String path = recordedRequest.getPath();
                MockResponse response = new MockResponse();
                response.setResponseCode(200);

                if(path.contains("contents")){
                    try {
                        response.setBody(AssetStringHelper.readAssetString("mock_contents_response.json"));
                    } catch (IOException e) {
                        Log.e(TAG, "Could not read file.", e);
                    }
                }
                else if(path.contains("users")){

                    try {
                        response.setBody(AssetStringHelper.readAssetString("mock_repository_response.json"));
                    } catch (IOException e) {
                        Log.e(TAG, "Could not read file.", e);
                    }
                }

                return response;
            }
        });
    }

    /*private void givenIHaveAListOfRepositories() {
        MockResponse response = new MockResponse();
        response.setResponseCode(200);

        try {
            response.setBody(AssetStringHelper.readAssetString("mock_repository_response.json"));
        } catch (IOException e) {
            Log.e(TAG, "Could not read file.", e);
        }

        mockWebServer.enqueue(response);
    }

    private void givenIHaveAListOfRepositoryContents() {
        MockResponse response = new MockResponse();
        response.setResponseCode(200);

        try {
            response.setBody(AssetStringHelper.readAssetString("mock_contents_response.json"));
        } catch (IOException e) {
            Log.e(TAG, "Could not read file.", e);
        }

        mockWebServer.enqueue(response);
    }*/

    private MainActivity startActivity() {
        return mainActivityIntentsTestRule.launchActivity(null);
    }
}
