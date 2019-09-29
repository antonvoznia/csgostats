package com.cti.statscsgo.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ListView;

import com.cti.statscsgo.ListAdapter.CustomListGuns;
import com.cti.statscsgo.R;
import com.cti.statscsgo.variable.StatsConst;

import java.util.Comparator;
import java.util.HashMap;

public class GunStats1 extends ListFragment {

    //Compare dor fast sorting
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

    private CustomListGuns listViewAdapter;

    public GunStats1() {
    }

    HashMap<String, String> hm;

    private ListView lv;

    public static GunStats1 getFragment() {
        return new GunStats1();
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gun_stats, container, false);
        lv = (ListView) view.findViewById(R.id.f);
        lv.setAdapter(listViewAdapter);
        return view;
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hm = (HashMap<String, String>) getActivity().getIntent().getSerializableExtra("HASH_MAP");

        //POS - номер фрагмента для сортирвки.
        //Если POS = 1 - 1-ый фрагмент и сортировать по kills,
        //2 - сортировка по shots, 3 - сортировка по hits

        StatsConst cnst = new StatsConst();

        int POS_FR = getArguments().getInt("POS_FR");
        if (POS_FR == 1) {
            qSort(cnst.MS_GUNS_KILLS, 0, cnst.MS_GUNS_KILLS.length - 1, cnst);
        } else if (POS_FR == 2) {
            qSort(cnst.MS_GUNS_HITS, 0, cnst.MS_GUNS_HITS.length - 2, cnst);
        } else if (POS_FR == 3) {
            qSort(cnst.MS_GUNS_SHOTS, 0, cnst.MS_GUNS_SHOTS.length - 2, cnst);
        }
        listViewAdapter = new CustomListGuns(getActivity(), cnst, hm);
        setListAdapter(listViewAdapter);
    }

    //быстрая сортировка, ms - массив, по которому будем
    //сортировать (сортировка по kills, hits или shots)
    private void qSort(String ms[], int low, int high, StatsConst cnst) {
        int i = low;
        int j = high;
        int x = Integer.valueOf(hm.get(ms[(low+high)/2]));
        do {
            while(myComp.compare(Integer.valueOf(hm.get(ms[i])), x) == 1 ) ++i;
            while(myComp.compare(Integer.valueOf(hm.get(ms[j])), x) == 2) --j;
            if(i <= j){
                String temp = cnst.MS_GUNS_HITS[i];
                cnst.MS_GUNS_HITS[i] = cnst.MS_GUNS_HITS[j];
                cnst.MS_GUNS_HITS[j] = temp;
                temp = cnst.MS_GUNS_SHOTS[i];
                cnst.MS_GUNS_SHOTS[i] = cnst.MS_GUNS_SHOTS[j];
                cnst.MS_GUNS_SHOTS[j] = temp;
                temp = cnst.MS_GUNS_KILLS[i];
                cnst.MS_GUNS_KILLS[i] = cnst.MS_GUNS_KILLS[j];
                cnst.MS_GUNS_KILLS[j] = temp;
                int tmp = cnst.MS_ID_GUNS[i];
                cnst.MS_ID_GUNS[i] = cnst.MS_ID_GUNS[j];
                cnst.MS_ID_GUNS[j] = tmp;
                temp = cnst.MS_GUNS_NAMES[i];
                cnst.MS_GUNS_NAMES[i] = cnst.MS_GUNS_NAMES[j];
                cnst.MS_GUNS_NAMES[j] = temp;
                i ++ ; j --;
            }
        } while(i <= j);
        //рекурсивные вызовы функции qSort
        if(low < j) qSort(ms, low, j, cnst);
        if(i < high) qSort(ms, i, high, cnst);
    }
}
