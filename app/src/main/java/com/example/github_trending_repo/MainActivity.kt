package com.example.github_trending_repo

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.github_trending_repo.api.di.viewModel
import com.example.github_trending_repo.ui.home.HomeFragment
import com.example.github_trending_repo.ui.home.SharedViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val viewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        centerAppBarTitle()
        setupUI()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_main)
            }
        }
    }
    private fun setupUI() {
    }

    private fun centerAppBarTitle() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        val abar: ActionBar? = supportActionBar
        val viewActionBar: View = layoutInflater.inflate(R.layout.abs_layout, null)
        val params = ActionBar.LayoutParams( //Center the textview in the ActionBar !
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        abar?.setCustomView(viewActionBar, params);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_item_stars -> {
                viewModel.getList(1)
                true
            }
            R.id.menu_item_name -> {
                viewModel.getList(2)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}