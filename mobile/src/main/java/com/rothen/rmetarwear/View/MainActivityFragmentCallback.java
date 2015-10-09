package com.rothen.rmetarwear.View;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by stéphane on 08/10/2015.
 */
public interface MainActivityFragmentCallback {

    void onItemClickListener(AdapterView<?> parent, View view, int position, long id);

    boolean onItemLongClickListener(String OACICode);

}
