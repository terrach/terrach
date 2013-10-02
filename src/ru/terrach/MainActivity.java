package ru.terrach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.terrach.activity.component.DrawerExpandableListAdapter;
import ru.terrach.fragment.BoardsFragment;
import ru.terrach.fragment.MainFragment;
import ru.terrach.fragment.PostsFragment;
import ru.terrach.fragment.ThreadsFragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MainActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ExpandableListView elvLeftDrawer;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private List<String> posts = new ArrayList<String>();

	private enum MainFragments {
		MAIN, BOARDS, THREADS, POSTS;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main);
		prepareDrawer();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawable_open, R.string.drawable_close);
		mDrawerLayout.setDrawerListener(mDrawerToggle);		

		elvLeftDrawer = (ExpandableListView) findViewById(R.id.elv_left_drawer);
		elvLeftDrawer.setAdapter(new DrawerExpandableListAdapter(this, listDataHeader, listDataChild));

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		elvLeftDrawer.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				return false;
			}
		});
		elvLeftDrawer.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				switch (groupPosition) {
				case 0:
					changeFragment(MainFragments.MAIN);
					break;
				case 1:
					changeFragment(MainFragments.BOARDS);
					break;
				case 2:
					break;
				case 3:
					break;
				}
				return true;
			}
		});

		changeFragment(MainFragments.MAIN);
	}

	private void changeFragment(MainFragments fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		switch (fragment) {
		case BOARDS:
			transaction.replace(R.id.fMain, new BoardsFragment()).commit();
			break;
		case MAIN:
			transaction.replace(R.id.fMain, new MainFragment()).commit();
			break;
		case POSTS:
			transaction.replace(R.id.fMain, new PostsFragment()).commit();
			break;
		case THREADS:
			transaction.replace(R.id.fMain, new ThreadsFragment()).commit();
			break;
		}
	}

	private void prepareDrawer() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		listDataHeader.add(getString(R.string.fragment_main));
		listDataHeader.add(getString(R.string.fragment_boards));
		listDataHeader.add("Открытые посты");
		posts.add("post1");
		posts.add("post2");
		posts.add("post3");
		posts.add("post4");
		listDataHeader.add("Настроки");
		listDataChild.put(listDataHeader.get(0), new ArrayList<String>());
		listDataChild.put(listDataHeader.get(1), new ArrayList<String>());
		listDataChild.put(listDataHeader.get(2), posts);
		listDataChild.put(listDataHeader.get(3), new ArrayList<String>());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void setTitle(CharSequence title) {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(title);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
