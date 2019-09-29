package com.cti.statscsgo.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ListView;

import com.cti.statscsgo.ListAdapter.CustomListMaps;
import com.cti.statscsgo.R;
import com.cti.statscsgo.variable.StatsConst;

import java.util.Comparator;
import java.util.HashMap;

public class MapStats extends ListFragment {

    private CustomListMaps listViewAdapter;

    public MapStats() {
    }

    HashMap<String, String> hm;

    private ListView lv;

    public static MapStats getFragment() {
        return new MapStats();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StatsConst cnst = new StatsConst();
        hm = (HashMap<String, String>) getActivity().getIntent().getSerializableExtra("HASH_MAP");
        int ij = 0;
        qSort(cnst.MS_MAPS_WINS, 0, cnst.MS_MAPS.length-1, cnst);
        //bubble_sort(cnst.MS_MAPS_WINS, cnst.MS_MAPS_WINS.length, cnst);
        listViewAdapter = new CustomListMaps(getActivity(), cnst.MS_MAPS_IMG, hm,
                cnst.MS_MAPS_WINS, cnst.MS_MAPS_ROUNDS, cnst.MS_MAPS);
        setListAdapter(listViewAdapter);
        getView().setBackgroundResource(R.drawable.de_vertigo_bg);
    }

    //Compare for fast sorting
    Comparator<Integer> myComp = new Comparator<Integer>() {
        @Override
        public int compare(Integer arg1, Integer arg2) {
            if (arg1 > arg2)
                return 1;
            else if (arg2 > arg1)
                return 2;
            return 0;
        }
    };

    //Fast storing by value - count of wins.
    private void qSort(String ms[], int low, int high, StatsConst cnst) {
        int i = low;
        int j = high;
        int x = Integer.valueOf(hm.get(ms[(low+high)/2]));
        do {
            while(myComp.compare(Integer.valueOf(hm.get(ms[i])), x) == 1 ) ++i;
            while(myComp.compare(Integer.valueOf(hm.get(ms[j])), x) == 2) --j;
            if(i <= j){
                String temp = cnst.MS_MAPS_WINS[i];
                cnst.MS_MAPS_WINS[i] = cnst.MS_MAPS_WINS[j];
                cnst.MS_MAPS_WINS[j] = temp;
                String temp2 = cnst.MS_MAPS_ROUNDS[i];
                cnst.MS_MAPS_ROUNDS[i] = cnst.MS_MAPS_ROUNDS[j];
                cnst.MS_MAPS_ROUNDS[j] = temp2;
                int tmp = cnst.MS_MAPS_IMG[i];
                cnst.MS_MAPS_IMG[i] = cnst.MS_MAPS_IMG[j];
                cnst.MS_MAPS_IMG[j] = tmp;
                String temp3 = cnst.MS_MAPS[i];
                cnst.MS_MAPS[i] = cnst.MS_MAPS[j];
                cnst.MS_MAPS[j] = temp3;
                i ++ ; j --;
            }
        } while(i <= j);

        if(low < j) qSort(ms, low, j, cnst);
        if(i < high) qSort(ms, i, high, cnst);
    }
}
