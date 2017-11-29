package com.iths.manisedighi.brewlikes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This activity contains the core for a listview page. It itemizes the list with setItems() method
 * that builds the lists and uses HashMap to create array list with category and item relation.
 * The second class ExpandableListAdapter creates the mechanics of the list and the xml files:
 * list_categories and list_category are used for the elements in the list.
 */

public class CategoriesActivity extends AppCompatActivity {
    private Context context = CategoriesActivity.this;
    private static ExpandableListView expandableListView;
    private static ExpandableListAdapter adapter;
    HashMap<String, List<String>> hashMap;
    ArrayList<String> header;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //setupBottomNavigation();



            expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
            // Setting group indicator null for custom indicator
            expandableListView.setGroupIndicator(null);

            setItems();

            adapter = new ExpandableListAdapter(CategoriesActivity.this, header, hashMap);
            // Setting adpater for expandablelistview
            expandableListView.setAdapter(adapter);

        /*
        You can add listeners for the item clicks
         */
            expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    return false;
                }
            });


        /**
         * Outcommentted toast log that informs list expand and collapse for each category
         */
           /* // Listview Group expanded listener
            expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            header.get(groupPosition) + " Expanded",
                            Toast.LENGTH_SHORT).show();
                }
            });*/

            /*// Listview Group collasped listener
            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            header.get(groupPosition) + " Collapsed",
                            Toast.LENGTH_SHORT).show();

                }
            });*/

        /**
         * Outcommentted toast log that informs item selection inside expanded listview
         */
            /*// Listview on child click listener
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {

                    Toast.makeText(
                            getApplicationContext(),
                            header.get(groupPosition)
                                    + " : "
                                    + hashMap.get(
                                    header.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            });*/
        }


        // Setting headers and childs to expandable listview
        void setItems() {

            // Array list for header
            header = new ArrayList<String>();

            // Array list for child items
            List<String> child1 = new ArrayList<String>();
            List<String> child2 = new ArrayList<String>();
            List<String> child3 = new ArrayList<String>();
            List<String> child4 = new ArrayList<String>();

            // Hash map for both header and child
            hashMap = new HashMap<String, List<String>>();

            // Adding headers to list
            for (int i = 1; i < 5; i++) {
                header.add("Group " + i);
            }
            // Adding child data
            for (int i = 1; i < 5; i++) {
                child1.add("Child" + i);
            }
            // Adding child data
            for (int i = 1; i < 5; i++) {
                child2.add("Child" + i);
            }
            // Adding child data
            for (int i = 1; i < 6; i++) {
                child3.add("Child" + i);
            }
            // Adding child data
            for (int i = 1; i < 7; i++) {
                child4.add("Child" + i);
            }

            // Adding header and childs to hash map
            hashMap.put(header.get(0), child1);
            hashMap.put(header.get(1), child2);
            hashMap.put(header.get(2), child3);
            hashMap.put(header.get(3), child4);

        }



    //}




    /**
     * A method that sets up the bottom navigation
     */
    /*
    private void setupBottomNavigation(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
        BottomNavigationHelper.manipulateBottomNavigation(bottomNavigationViewEx);
        BottomNavigationHelper.activateBottomNavigation(context, bottomNavigationViewEx);
    }
    */

}
