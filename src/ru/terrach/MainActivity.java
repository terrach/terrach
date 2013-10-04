package ru.terrach;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ru.terrach.activity.MainActivityInterface;
import ru.terrach.activity.component.PostHolder;
import ru.terrach.activity.component.adapter.DrawerExpandableListAdapter;
import ru.terrach.core.helper.RecentHelper;
import ru.terrach.fragment.BoardsFragment;
import ru.terrach.fragment.MainFragment;
import ru.terrach.fragment.PostsFragment;
import ru.terrach.fragment.ThreadsFragment;
import android.content.res.Configuration;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MainActivity extends ActionBarActivity implements MainActivityInterface {
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ExpandableListView elvLeftDrawer;
	private List<String> listDataHeader;
	private HashMap<String, List<PostHolder>> listDataChild;
	private List<PostHolder> posts = new ArrayList<PostHolder>();
	private Map<String, SoftReference<PostsFragment>> postFragments = new HashMap<String, SoftReference<PostsFragment>>();
	private Map<String, SoftReference<ThreadsFragment>> threadFragments = new HashMap<String, SoftReference<ThreadsFragment>>();
	private TextView tvCurrentFragment;
	private DrawerExpandableListAdapter expAdapter;
	private RecentHelper recentHelper;

	private enum MainFragments {
		MAIN, BOARDS, THREADS, POSTS;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main);

		prepareDrawer();
		recentHelper = new RecentHelper(this, getPreferences(0));
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawable_open, R.string.drawable_close);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		elvLeftDrawer = (ExpandableListView) findViewById(R.id.elv_left_drawer);
		expAdapter = new DrawerExpandableListAdapter(this, listDataHeader, listDataChild);
		elvLeftDrawer.setAdapter(expAdapter);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		elvLeftDrawer.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				changeFragment(MainFragments.POSTS, ((PostHolder) v.getTag()).board, ((PostHolder) v.getTag()).post);
				mDrawerLayout.closeDrawers();
				return true;
			}
		});
		elvLeftDrawer.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

				switch (groupPosition) {
				case 0:
					changeFragment(MainFragments.MAIN, null, null);
					mDrawerLayout.closeDrawers();
					return true;
				case 1:
					changeFragment(MainFragments.BOARDS, null, null);
					mDrawerLayout.closeDrawers();
					return true;
				case 2:

				case 3:

				}
				return false;
			}
		});
		tvCurrentFragment = (TextView) findViewById(R.id.tvCurrentFragment);
		changeFragment(MainFragments.MAIN, null, null);
	}

	private void changeFragment(MainFragments fragment, String board, String msg) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		switch (fragment) {
		case BOARDS:
			transaction.replace(R.id.fMain, new BoardsFragment());
			transaction.addToBackStack(null);
			transaction.commit();
			break;
		case MAIN:
			transaction.replace(R.id.fMain, new MainFragment());
			transaction.addToBackStack(null);
			transaction.commit();
			break;
		case POSTS:
			SoftReference<PostsFragment> postFragmentReference = postFragments.get(msg);
			if (postFragmentReference != null && postFragmentReference.get() != null) {
				transaction.replace(R.id.fMain, postFragmentReference.get()).commit();
				postFragmentReference.get().reload();
			} else {
				PostsFragment pf = new PostsFragment();
				postFragmentReference = new SoftReference<PostsFragment>(pf);
				postFragments.put(msg, postFragmentReference);

				transaction.replace(R.id.fMain, postFragmentReference.get());
				transaction.addToBackStack(null);
				transaction.commit();
				pf.loadPosts(board, msg);
			}
			tvCurrentFragment.setText(msg);
			break;
		case THREADS:
			SoftReference<ThreadsFragment> targetFragment = threadFragments.get(msg);
			if (targetFragment != null && targetFragment.get() != null) {
				transaction.replace(R.id.fMain, targetFragment.get()).commit();
			} else {
				ThreadsFragment tf = new ThreadsFragment();
				targetFragment = new SoftReference<ThreadsFragment>(tf);
				threadFragments.put(board, targetFragment);
				transaction.replace(R.id.fMain, targetFragment.get());
				transaction.addToBackStack(null);
				transaction.commit();
				tf.loadThreads(board);
			}
			tvCurrentFragment.setText(board);
			break;
		}
	}

	private void prepareDrawer() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<PostHolder>>();
		listDataHeader.add(getString(R.string.fragment_main));
		listDataHeader.add(getString(R.string.fragment_boards));
		listDataHeader.add("Открытые посты");
		listDataHeader.add("Настройки");
		listDataChild.put(listDataHeader.get(0), new ArrayList<PostHolder>());
		listDataChild.put(listDataHeader.get(1), new ArrayList<PostHolder>());
		listDataChild.put(listDataHeader.get(2), posts);
		listDataChild.put(listDataHeader.get(3), new ArrayList<PostHolder>());
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

	@Override
	public void loadBoard(String name) {
		changeFragment(MainFragments.THREADS, name, null);
		prepareDrawer();
		expAdapter = new DrawerExpandableListAdapter(this, listDataHeader, listDataChild);
		elvLeftDrawer.setAdapter(expAdapter);
	}

	@Override
	public void loadThread(String board, String thread) {
		changeFragment(MainFragments.POSTS, board, thread);
		posts.add(new PostHolder(board, thread));
		recentHelper.addRecentBoard(board);
		recentHelper.addRecentThread(thread);
		prepareDrawer();
		expAdapter = new DrawerExpandableListAdapter(this, listDataHeader, listDataChild);
		elvLeftDrawer.setAdapter(expAdapter);
	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		// check to see if stack is empty
		if (fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
			ft.commit();
		} else {

			super.onBackPressed();
		}
		return;
	}
}
