package com.li.ramon.effectivenavigation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/*实现抽屉式导航面板，从屏幕左方滑动拖出。*/
public class DrawerDemoActivity extends AppCompatActivity{

    private String[] mPlanetTitles;//左侧布局 listview 数据源
    private ListView mDrawerList;// 左侧布局
    private DrawerLayout mDrawerLayout;// DrawerLayout 布局
    private Toolbar mToolbar;

    public  static int[] imgId = {R.drawable.img1,R.drawable.img2, R.drawable.img3,R.drawable.img4,R.drawable.img5,
                R.drawable.img6,R.drawable.img7,R.drawable.img8};

    /* ActionBarDrawerToggle 是 DrawerLayout.DrawerListener 的实现类,和 DrawerLayout 配合使用*/
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_drawer_demo);

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        mDrawerList =  findViewById(R.id.left_drawer);
        mToolbar = findViewById(R.id.mToolBar);

        // 为list view设置adapter
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mPlanetTitles));

        // 为list设置click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close) {

            //当drawer处于完全关闭的状态时调用
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // 创建对onPrepareOptionsMenu()的调用
            }

            //当drawer处于完全打开的状态时调用
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // 创建对onPrepareOptionsMenu()的调用
            }
        };

        // 设置drawer触发器为DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /* 当invalidateOptionsMenu()调用时调用 */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 如果nav drawer是打开的, 隐藏与内容视图相关联的action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /*该方法在 onStart 和 onRestoreInstanceState 后调用，同步触发器状态.*/
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /*ActionBarDrawerToggle与DrawerLayout的状态同步，并将
        ActionBarDrawerToggle中的drawer图标，设置为ActionBar的Home-Button的icon*/
        mDrawerToggle.syncState();
    }

    /*设备配置改变时*/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*将事件传递给ActionBarDrawerToggle, 如果返回true，表示app 图标点击事件已经被处理*/
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // 处理其他 action bar items
        return super.onOptionsItemSelected(item);
    }

    /*DrawLayout 的左侧菜单点击监听类*/
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** 在主内容视图中交换fragment */
    private void selectItem(int position) {
        // 创建一个新的fragment并且根据行星的位置来显示
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        // 通过替换已存在的fragment来插入新的fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // 高亮被选择的item, 更新标题, 并关闭drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public static class PlanetFragment extends Fragment{
        private  static final String ARG_PLANET_NUMBER = "arg_plnnet number";

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(
                    R.layout.fragment_drawer_view, container, false);
            Bundle args = getArguments();
            ((ImageView) rootView.findViewById(R.id.img)).setImageResource(imgId[args.getInt(PlanetFragment.ARG_PLANET_NUMBER)]);
            return rootView;
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}