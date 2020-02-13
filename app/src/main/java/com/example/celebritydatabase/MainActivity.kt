package com.example.celebritydatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CelebrityCallback
{
    val databaseHelper by lazy{ CelebrityDatabaseHelper(this)}
    val adapter by lazy {CelebrityRVAdapter(databaseHelper.getAllPeopleFromDatabase(), this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.btnAddUpdateCelebrity ->
            {
                if(databaseHelper.getOnePersonFromDatabase(etLastName.text.toString()) == null)
                {
                    val firstName = etFirstName.text.toString()
                    val lastName = etLastName.text.toString()
                    val birthDate = etBirthDate.text.toString()
                    val netWorth = etNetWorth.text.toString()
                    val isFavorite = cbIsFavorite.isChecked.toString()
                    databaseHelper.insertCelebrityIntoDatabase(Celebrity(firstName,lastName, birthDate, netWorth, isFavorite))
                }
                else
                {
                    val firstName = etFirstName.text.toString()
                    val lastName = etLastName.text.toString()
                    val birthDate = etBirthDate.text.toString()
                    val netWorth = etNetWorth.text.toString()
                    val isFavorite = cbIsFavorite.isChecked.toString()
                    databaseHelper.updatePersonInDatabase(Celebrity(firstName, lastName, birthDate, netWorth, isFavorite))
                }
                //adapter.updateList(databaseHelper.getAllPeopleFromDatabase())
                onContentProviderAllCelebrities(view)
            }
            R.id.btnRemoveCelebrity ->
            {
                val lastName = etLastName.text.toString()
                databaseHelper.removePersonFromDatabase(lastName)
                adapter.updateList(databaseHelper.getAllPeopleFromDatabase())
            }
            R.id.btnSeeFavorites ->
            {
                databaseHelper.getAllFavoritesFromDatabase()
                adapter.updateList(databaseHelper.getAllFavoritesFromDatabase())
            }
        }
    }

    override fun passCelebrity(celebrity: Celebrity)
    {
        etFirstName.setText(celebrity.firstName)
        etLastName.setText(celebrity.lastName)
        etBirthDate.setText(celebrity.birthDate)
        etNetWorth.setText(celebrity.netWorth)
        if(celebrity.isFavorite == "true")
        {
            cbIsFavorite.isChecked = true
        }
        else
        {
            cbIsFavorite.isChecked = false
        }
    }

    private fun initRecyclerView() {
        rvCelebrityList.layoutManager = LinearLayoutManager(this)
        rvCelebrityList.adapter = adapter
    }

    fun onContentProviderAllCelebrities(view: View)
    {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        val celebrityList = ArrayList<Celebrity>()
        if(cursor!!.moveToFirst()) {
            do {
                val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
                val birthDate = cursor.getString(cursor.getColumnIndex(COL_BIRTH_DATE))
                val netWorth = cursor.getString(cursor.getColumnIndex(COL_NET_WORTH))
                val isFavorite = cursor.getString(cursor.getColumnIndex(COL_IS_FAVORITE))
                val celebrity = Celebrity(firstName, lastName, birthDate, netWorth, isFavorite)
                celebrityList.add(celebrity)
            } while(cursor.moveToNext())
        }

        cursor.close()
        adapter.updateList(celebrityList)
    }
    //Maybe how you do favorite selection through content provider
    /*fun onContentProviderFavoritesCelebrities(view: View)
    {
        val cursor = contentResolver.query(CONTENT_URI, null,  COL_IS_FAVORITE, null, null)
        val celebrityList = ArrayList<Celebrity>()
        if(cursor!!.moveToFirst()) {
            do {
                val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
                val birthDate = cursor.getString(cursor.getColumnIndex(COL_BIRTH_DATE))
                val netWorth = cursor.getString(cursor.getColumnIndex(COL_NET_WORTH))
                val isFavorite = cursor.getString(cursor.getColumnIndex(COL_IS_FAVORITE))
                val celebrity = Celebrity(firstName, lastName, birthDate, netWorth, isFavorite)
                celebrityList.add(celebrity)
            } while(cursor.moveToNext())
        }

        cursor.close()
        adapter.updateList(celebrityList)
    }*/
}
