package ru.vdv.myapp.myreadersdiary

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import ru.vdv.myapp.myreadersdiary.databinding.ActivityMainBinding
import ru.vdv.myapp.myreadersdiary.databinding.AppBarMainBinding
import ru.vdv.myapp.myreadersdiary.ui.CustomBackButtonListener

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: AppBarMainBinding
    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AppBarMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.appBarMain.toolbar)


        val bottomAppBar = binding.bottomAppBar
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_new_main_fragment,
//                R.id.nav_list_of_books,
//                R.id.nav_summary_statistics
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        bottomAppBar.setNavigationOnClickListener {
            Log.d("Моя проверка", "Нажата навигационная кнопка нижнего меню")
            //для простоты это действие просто возвращает пользователя на главный экран.
            //из фрагмента любго уровня вложенности (аналог того сайта с функцией "домой")
            navController.navigate(R.id.nav_new_main_fragment)
        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_summary_statistics -> {
                    // Отслеживание нажатия на кнопку суммарной статистики пользователя
                    navController.navigate(R.id.nav_summary_statistics)
                    true
                }
                R.id.nav_settings_fragment -> {
                    // ОТслеживание нажатия на кнопку перехода к странице настроек
                    navController.navigate(R.id.nav_settings_fragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.nav_settings_fragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val fragmentBackButton =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)?.childFragmentManager?.fragments?.first() as? CustomBackButtonListener
        fragmentBackButton?.backPressed() ?: super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val fragmentBackButton =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)?.childFragmentManager?.fragments?.first() as? CustomBackButtonListener
        return fragmentBackButton?.backPressed() ?: false || navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}