package com.kewal_krishna.android.stockhawk.touch_helper;

import android.view.View;

/**
 * Created by kewal_krishna on 10/6/15.
 * credit to Paul Burke (ipaulpro)
 * Interface to enable swipe to delete
 */
public interface ItemTouchHelperAdapter {

  void onItemDismiss(int position);
}
