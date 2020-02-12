package cluckinbell.com

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FirebaseListener {

    override fun onFirebaseLoadSuccess(itemGroupList: List<ItemGroup>) {
        val adapter = MyGroupAdapter(this, itemGroupList)
        my_recycler_view.adapter = adapter
        dialog.dismiss()
    }

    override fun onFirebaseLoadFailed(message: String) {
        dialog.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    lateinit var dialog: AlertDialog
    lateinit var firebaseListener: FirebaseListener
    lateinit var myData: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = SpotsDialog.Builder().setContext(this).build()
        myData = FirebaseDatabase.getInstance().getReference("MyData")
        firebaseListener = this

        my_recycler_view.setHasFixedSize(true)
        my_recycler_view.layoutManager = LinearLayoutManager(this)

        getFirebaseDatabase()
    }

    private fun getFirebaseDatabase() {

        dialog.show()

        myData.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                firebaseListener.onFirebaseLoadFailed(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val itemGroups = ArrayList<ItemGroup>()
                for (myDataSnapshot in p0.children) {
                    val itemGroup = ItemGroup()
                    itemGroup.headerTitle = myDataSnapshot.child("headerTitle").getValue(true).toString()
                    val t = object: GenericTypeIndicator<ArrayList<ItemData>>() {}
                    itemGroup.listItem = myDataSnapshot.child("listItem").getValue(t)
                    itemGroups.add(itemGroup)
                }
                firebaseListener.onFirebaseLoadSuccess(itemGroups)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.icon_pay, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.order_pay) {
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}
