package com.example.pizzahut;

import android.view.View;

public interface onRecyclerViewItemClickListener {
    // recycler view doesn't allow to click on item , so we created RecyclerViewItemClickListener to allow click on item .
    // created interface bc we cant implement anything related to main activity on adapter class , adapter should be independent .

    // create abstract method taker post Id as parameter to recall car info :
   void onItemClick(int Id);


}
