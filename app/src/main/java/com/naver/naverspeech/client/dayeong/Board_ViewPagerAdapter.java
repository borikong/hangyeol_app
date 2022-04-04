package com.naver.naverspeech.client.dayeong;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 뷰페이저어댑터(다영)
 */

public class Board_ViewPagerAdapter extends FragmentPagerAdapter {

    private static int PAGE_NUMBER=2;

    public Board_ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new Board_FragmentInterview();
           case 1:
               return new Board_FragmentMessageBoard();
               default:
                   return null;
       }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "면접 정보 게시판";
            case 1:
                return "자유게시판";
                default:
                    return null;
        }
    }
}
