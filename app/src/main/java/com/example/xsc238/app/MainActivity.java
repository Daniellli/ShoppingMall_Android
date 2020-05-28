package com.example.xsc238.app;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import com.example.xsc238.R;
import com.example.xsc238.base.fragment.BaseFragment;
import com.example.xsc238.community.fragment.CommunityFragment;
import com.example.xsc238.home.fragment.HomeFragment;
import com.example.xsc238.shoppingcart.fragment.ShoppingCartFragment;
import com.example.xsc238.type.fragment.TypeFragment;
import com.example.xsc238.user.Fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rg_main)
    RadioGroup rg_main;

    private ArrayList<BaseFragment> fragments;
    private Integer position = 0;
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tempFragment = null;
        initFragment();
        initListener();
        rg_main.check(R.id.rb_home);
    }


    /**
     * 添加radiogroup监听
     */
    private void initListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                BaseFragment baseFragment = getFragment(position);
                swictFragment(tempFragment, baseFragment);
            }
        });

    }

    /**
     * 切换fragment
     *
     * @param fromFragment
     * @param nextFragment
     */
    private void swictFragment(BaseFragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {

            tempFragment = nextFragment;
            if (nextFragment != null) {
                //切换
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //如果这不是第一次切换，令上次切换的fragment隐藏
                if (fromFragment != null) {
                    fragmentTransaction.hide(fromFragment);
                }
                //nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    fragmentTransaction.add(R.id.frameLayout, nextFragment).commit();
                } else {//显示出来
                    fragmentTransaction.show(nextFragment).commit();
                }
            }
        }
    }

    /**
     * 获取position位置的fragment
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        if (fragments != null && fragments.size() > 0) {
            baseFragment = fragments.get(position);
        }
        return baseFragment;
    }

    /**
     * 初始化fragments
     */
    private void initFragment() {
        fragments = new ArrayList<BaseFragment>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }
}
