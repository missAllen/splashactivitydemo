package com.haiquan.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;




import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by root on 16-10-18.
 */

public class SplashActivity extends AppCompatActivity {

    ImageView launcherImg;

    ViewPager splashViewPager;
    LinearLayout splashViewGroup;
    Button btnInto;
    RelativeLayout splashLayoutGuide;
    private int[] images = {R.mipmap.img_splash_1, R.mipmap.img_splash_2, R.mipmap.img_splash_3};
    private GuidePagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    /**
     * 初始化VIew
     */
    private void initView() {
        launcherImg = (ImageView) findViewById(R.id.launcher_img);
        splashViewPager = (ViewPager) findViewById(R.id.splash_view_pager);
        splashViewGroup = (LinearLayout) findViewById(R.id.splash_view_group);
        btnInto = (Button) findViewById(R.id.btnInto);
        splashLayoutGuide = (RelativeLayout) findViewById(R.id.splash_layout_guide);
        btnInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferencesUtil.setBoolean(SplashActivity.this, AppConfig.IS_FIRST_LAUNCHER, false);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void initData() {
        boolean isFirst = SharedPreferencesUtil.getBoolean(this, AppConfig.IS_FIRST_LAUNCHER, true);
        if (isFirst) {
            launcherGuide();
        } else {
            launcherSplash();
        }
    }


    /**
     * 引导页面
     */

    private void launcherGuide() {
        launcherImg.setVisibility(View.GONE);
        splashLayoutGuide.setVisibility(View.VISIBLE);
        initIndicator();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        btnInto.setAnimation(animation);
        adapter = new GuidePagerAdapter();
        splashViewPager.setAdapter(adapter);
        splashViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    btnInto.setVisibility(View.VISIBLE);
                } else {
                    btnInto.setVisibility(View.GONE);
                }
                setCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 初始化指示器
     */
    private void initIndicator() {

        for (int i = 0; i < images.length; i++) {
            ImageView img = new ImageView(SplashActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(10, 0, 10, 0);
            img.setLayoutParams(params);

            if (i == 0) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.indicator_sel));
            } else {
                img.setImageDrawable(getResources().getDrawable(R.drawable.indicator_nor));
            }
            splashViewGroup.addView(img);
        }
    }


    private void setCurrentDot(int position) {
        for (int i = 0; i < images.length; i++) {
            ImageView img = (ImageView) splashViewGroup.getChildAt(i);
            if (i == position) {

                img.setImageDrawable(getResources().getDrawable(R.drawable.indicator_sel));
            } else {
                img.setImageDrawable(getResources().getDrawable(R.drawable.indicator_nor));
            }
        }
    }

    public class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(SplashActivity.this);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            item.setImageResource(images[position]);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 启动页面
     */
    private void launcherSplash() {
        splashLayoutGuide.setVisibility(View.GONE);
        launcherImg.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        launcherImg.startAnimation(animation);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 1000);
    }


}
