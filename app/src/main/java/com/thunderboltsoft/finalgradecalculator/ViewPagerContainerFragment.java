package com.thunderboltsoft.finalgradecalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

public class ViewPagerContainerFragment extends Fragment {

    private boolean assessmentView = false;

    public ViewPagerContainerFragment() {
    }

    public void setAssessmentView() {
        assessmentView = true;
    }

    public boolean getAssessmentView() {
        return assessmentView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.view_pager_container_fragment, container, false);

        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(getContext(), MainFragment.class.getName()));
        fragments.add(Fragment.instantiate(getContext(), AssessmentsFragment.class.getName()));

        PageAdapter mPageAdapter = new PageAdapter(super.getChildFragmentManager(), fragments);


        PagerTabStrip tabStrip = (PagerTabStrip) root.findViewById(R.id.tabStrip);
        if (tabStrip != null) {
            tabStrip.setTextColor(getResources().getColor(R.color.textColorPrimary));
            tabStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
            tabStrip.setDrawFullUnderline(true);
            tabStrip.setTabIndicatorColor(Color.WHITE);
        }

        ViewPager pager = (ViewPager) root.findViewById(R.id.viewPager);
        if (pager != null) {
            pager.setAdapter(mPageAdapter);

            if (assessmentView) {
                pager.setCurrentItem(1);
            }
        }

        return root;
    }
}