package com.example.aahaarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserdataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<model> datalist;
    private FirebaseFirestore db;
    private myadapter adapter;
    private FirebaseAuth fAuth;
    private String userID;
    Button donors, recievers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdata);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        donors = findViewById(R.id.donors);
        recievers = findViewById(R.id.recievers);
        SQLiteDatabase mdb = openOrCreateDatabase("ahhar.db", MODE_PRIVATE, null);

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> dataList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        donors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = mdb.rawQuery("SELECT * FROM donate", null);
                dataList.clear();
                if (cursor.getCount() == 0) {
                    Toast.makeText(UserdataActivity.this, "No data exists", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuffer stb = new StringBuffer();
                    while (cursor.moveToNext()) {
                        stb.append("Name: " + cursor.getString(0) + "\n");
                        stb.append("Food item: " + cursor.getString(1) + "\n");
                        stb.append("Description: " + cursor.getString(2) + "\n");
                        stb.append("Phone: " + cursor.getString(3) + "\n");
                        stb.append("Type: " + cursor.getString(4) + "\n\n");
                    }

                    dataList.add(stb.toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        recievers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = mdb.rawQuery("SELECT * FROM recieve", null);
                dataList.clear();
                if (cursor.getCount() == 0) {
                    Toast.makeText(UserdataActivity.this, "No data exists", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuffer stb = new StringBuffer();
                    while (cursor.moveToNext()) {
                        stb.append("Name: " + cursor.getString(0) + "\n");
                        stb.append("Description: " + cursor.getString(1) + "\n");
                        stb.append("Type: " + cursor.getString(2) + "\n\n");
                    }

                    dataList.add(stb.toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}






       /* db.collection("user data")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        datalist.clear(); // Clear the list before adding new data
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            if (documentSnapshot.exists()) {
                                String userid = documentSnapshot.getString("userid");
                                if (userid.equals(userID)) {
                                    model obj = documentSnapshot.toObject(model.class);
                                    datalist.add(obj);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();

                        // Logging statements to check the data and list size
                        String TAG = "UserdataActivity";
                        Log.d(TAG, "Data list size: " + datalist.size());
                        for (model data : datalist) {
                            Log.d(TAG, "Data: " + data.toString());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure case
                        Toast.makeText(UserdataActivity.this, "Failed to retrieve data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        String TAG = "UserdataActivity";
                        Log.e(TAG, "Error retrieving data: " + e.getMessage());
                    }
                });*/





