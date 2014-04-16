package com.nozomi.fragmentsample2;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private String[] titleArray = new String[] { "fragment0", "fragment1",
			"fragment2" };
	private ActionBar actionBar = null;
	private boolean isDrawer = false;
	private static final String SP_IS_DRAWER = "is_drawer";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();

	}

	private void initView() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		isDrawer = sp.getBoolean(SP_IS_DRAWER, false);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		if (isDrawer) {
			setContentView(R.layout.main_activity_drawer);
			mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
					.findFragmentById(R.id.navigation_drawer);
			// Set up the drawer.
			mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
					(DrawerLayout) findViewById(R.id.drawer_layout));
		} else {
			setContentView(R.layout.main_activity_tab);

			MyAdapter pagerAdapter = new MyAdapter(getSupportFragmentManager());
			ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
			viewPager.setAdapter(pagerAdapter);
			viewPager.setCurrentItem(0);
			actionBar.setTitle(titleArray[0]);
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {

					actionBar.setTitle(titleArray[position]);
				}

				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {

				}

				@Override
				public void onPageScrollStateChanged(int state) {

				}
			});

			PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

			pagerTabStrip.setDrawFullUnderline(true);

		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(titleArray[position]))
				.commit();
		actionBar.setTitle(titleArray[position]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(this, "action_settings", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == R.id.action_change) {

			isDrawer = !isDrawer;
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(this);
			sp.edit().putBoolean(SP_IS_DRAWER, isDrawer).commit();
			finish();
			startActivity(getIntent());

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public String[] getTitleArray() {
		return titleArray;
	}

	public class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titleArray[position];
		}

		@Override
		public int getCount() {
			return titleArray.length;
		}

		@Override
		public Fragment getItem(int position) {

			return PlaceholderFragment.newInstance(titleArray[position]);
		}
	}
}
