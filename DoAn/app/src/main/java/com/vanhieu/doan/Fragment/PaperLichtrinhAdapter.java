package com.vanhieu.doan.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PaperLichtrinhAdapter extends FragmentPagerAdapter {
    private int tabNumber;

    public PaperLichtrinhAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super( fm, behavior );
        this.tabNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChitieuFragment();
            case 1:
                return new HanghoaFragment();
            case 2:
                return new KhachHangFragment();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}
