package com.example.common.utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.common.R;


/**
 * Created by michaelyotive_hr on 12/26/16.
 */

public class FragmentUtility {

    public static void goToFragment(FragmentManager fragmentManager,
                                    Fragment fragment,
                                    int containerId,
                                    boolean addToBackstack,
                                    TransitionType transitionType){
        int enter = 0, exit = 0, popEnter = 0, popExit = 0;
        switch (transitionType){
            case SlideHorizontal:
                enter = R.anim.slide_in_right;
                exit = R.anim.slide_out_left;
                popEnter = R.anim.slide_in_left;
                popExit = R.anim.slide_out_right;
                break;
            case SlideVertical:
                enter = R.anim.slide_in_bottom;
                exit = R.anim.slide_out_bottom;
                popEnter = R.anim.slide_in_bottom;
                popExit = R.anim.slide_out_bottom;
                break;
        }

        FragmentTransaction ft = fragmentManager.beginTransaction();

        String tag = fragment.getClass().getSimpleName();

        if(addToBackstack) {
            ft.addToBackStack(tag);
        }

        ft.setCustomAnimations(enter, exit, popEnter, popExit)
                .replace(containerId, fragment, tag)
                .commit();
    }

}